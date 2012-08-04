package com.example.homebrewnavigator;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

//Define an IntentService that lives (effectively) forever



//Notification used for updates of the temperature.
public class TemperatureService extends NonStopIntentService {

	private BluetoothSocket socket = null;
	private BluetoothDevice[] bluetooth_devices = null;
	private OutputStream out = null;
	private InputStream in = null;
	
	private boolean notificationFired = false;
	
	private boolean keep_running = false;
	
	private float temperature = 0;
	
	private NotificationManager  notiManager;
	
	private static final int TEMP_NOTIF_ID = 1;
	
	private IBinder temperatureBinder = new TemperatureBinder();

	public class TemperatureBinder extends Binder {
		
		TemperatureService getService()
		{
			return TemperatureService.this;
		}
	}
	
	public TemperatureService() {
		super("TemperatureService");
		// TODO Auto-generated constructor stub
  	
	}
	
	public void startTempThread(InputStream in)
	{
		this.in = in;
		keep_running = true;
		Thread thread = new Thread(runnable);
		thread.start();
	}
	
	private void fireNotification(String temp)
	{
		int icon = R.drawable.tempnot;
		String text = temp;
		long when = System.currentTimeMillis();
		
		Context context = getApplicationContext();
		String contentTitle = "Temperature notification";
		String contentText = "Your current temperature is " + temp;
		Intent notificationIntent = new Intent(this, TemperatureService.class);
		int flag = Notification.FLAG_ONGOING_EVENT;
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, flag);

		
		
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
		{
			Notification noti = new Notification(icon, temp, when);
			noti.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
			notiManager.notify(TEMP_NOTIF_ID, noti);
		}
		else
		{
			fireNotiApi11(context, contentTitle, contentText, icon);
		}
		
		
	}
	
	@TargetApi(11)
	private void fireNotiApi11(Context context, String title, String text, int icon)
	{
		Notification.Builder builder = new Notification.Builder(context)
        .setContentTitle(title)
        .setContentText(text)
        .setSmallIcon(icon);
		
		Notification noti = builder.getNotification();
		
		notiManager.notify(TEMP_NOTIF_ID, noti);
	}
	
	public void startBluetoothService()
	{
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        
        String ns = Context.NOTIFICATION_SERVICE;
        notiManager = (NotificationManager) getSystemService(ns);
        
        //We should never actually use the below value
        String[] devices_strings = { "No Adapter Found" };
        
        if (adapter != null)
        {
        	Set<BluetoothDevice> devices = adapter.getBondedDevices();
        	
        	devices_strings = new String[devices.size()];
            bluetooth_devices = new BluetoothDevice[devices.size()];
            Iterator<BluetoothDevice> iter = devices.iterator();
            
            int i = 0;
            while(iter.hasNext())
            {
              bluetooth_devices[i] = (BluetoothDevice)iter.next();
              devices_strings[i] = bluetooth_devices[i].getName()+"  "+bluetooth_devices[i].getAddress();
              i++;
            }
            
            //TODO: If there's a bonded device, pick the first one and use it.
            // Eventually we might want to use it based on the name it provides.
            
            try
            {
            	
            	BluetoothDevice device = adapter.getRemoteDevice(bluetooth_devices[0].getAddress());
            	//Connect to SPP service - build-in method is apparently broken.  this reflection is a workaround.
            	Method m;
            	m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
            	socket = (BluetoothSocket)m.invoke(device, Integer.valueOf(1)); 
            	
            	
            	//socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            	socket.connect();
                out = socket.getOutputStream();
                in = socket.getInputStream();
            }
            catch( Exception e) {
            	System.out.println(e.toString());
            	adapter.cancelDiscovery();
            	if(socket != null)
            	{
            		try { socket.close(); } catch(Exception e1) {}
            		socket = null;
            	}
            	if(out != null)
            	{
            		try { out.close(); } catch(Exception e1) {}
            		out = null;
            	}
            	if(in != null)
            	{
            		try { in.close(); } catch(Exception e1) {}
            		in = null;
            	}
            	}
            finally {

            }
        }
        
        fireNotification("0.00");
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		try
	    {
	      if (in != null) in.close();
	      if (out != null) out.close();
	      if (socket != null) socket.close();
	    }
	    catch (Exception e) { System.out.println(e.toString()); }
	
	    socket = null;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		onHandleIntent(intent);
		return temperatureBinder;
	}
	
	public float GetLastTemperature()
	{
		return temperature;
	}
	
	public boolean isConnected()
	{
		return socket != null;
	}
	
	final Runnable runnable = new Runnable()
	  {
	    public void run()
	    {
	    	int ch;

		      while(keep_running)
		      {
		        try
		        {
		        	String temp = ""; 
		        	//allow breakout of loop while reading
		        	while(keep_running && (ch = in.read() ) != 13 )
		            {
		        		temp = temp + (char)ch;
		            }
		          
		        	
		        	try
		        	{
		        		temperature = Float.parseFloat(temp);
		        		fireNotification(temp);
		        	}
		        	catch(Exception e)
		        	{
		        		temperature = 0.0f;
		        	}
		          }
		        catch (Exception e) {
		        	System.out.println("BlueTemp problem: "+e.toString());
		        	try { socket.close(); } catch(Exception ex1) { System.out.println("Shutdown problem"); } finally 
		        	{
		        		socket = null;
		        		temperature = 0;
		        		keep_running = false;
		        	}
		        }
	      }
	    }
	  };


	  @Override
	  protected void onHandleIntent(Intent intent) {
		  startBluetoothService();
		  // TODO Auto-generated method stub
		  startTempThread(in);
	  }

}
