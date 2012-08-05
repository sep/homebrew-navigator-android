package com.example.homebrewnavigator;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

import android.app.Notification;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

//Define an IntentService that lives (effectively) forever



//Notification used for updates of the temperature.
public class TemperatureService extends NonStopIntentService {

	private BluetoothSocket socket = null;
	
	private BluetoothDevice device = null;
	private OutputStream out = null;
	private InputStream in = null;
	
	private boolean keep_running = false;
	
	private float temperature = 0;
	private boolean connected = false;
	
	private NotificationManager  notiManager;
	
	private static final int TEMP_NOTIF_ID = 1;
	
	private IBinder temperatureBinder = new TemperatureBinder();

	public class TemperatureBinder extends Binder {
		
		public TemperatureService getService()
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
		Context context = getApplicationContext();
		String contentTitle = "Temperature notification";
		String contentText = "Your current temperature is " + temp;
		Notification.Builder builder = new Notification.Builder(context)
        .setContentTitle(contentTitle)
        .setContentText(contentText)
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
        
        BluetoothDevice[] bluetooth_devices;
        
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
            	
            	device = adapter.getRemoteDevice(bluetooth_devices[0].getAddress());
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
		
		keep_running = false;
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
		super.onBind(intent);
		
		return temperatureBinder;
		
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		startBluetoothService();
		startTempThread(in);
	}
	
	public float GetLastTemperature()
	{
		return temperature;
	}
	
	public boolean isConnected()
	{
		return connected;
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
		        	
		        	if(socket == null)
		        	{
		        		AttemptBtConnection();
		        		
		        		//Wait for 10 seconds, breaking if something happens.  Wouldn't want to drain the battery too much.
		        		for(int i=0; i<100; i++)
		        		{
		        			Thread.sleep(100);
		        			if(!keep_running) break;
		        		}
		        	}
		        	
		        	String temp = ""; 
		        	//allow break-out of loop while reading
		        	while(keep_running && socket != null && (ch = in.read() ) != 13 )
		            {
		        		temp = temp + (char)ch;
		            }
		          
		        	
		        	try
		        	{
		        		temperature = Float.parseFloat(temp);
		        		connected = true;
		        		fireNotification(temp);
		        		Intent i = new Intent("updatedTemperature");
		        		i.putExtra("temp", temperature);
		        		MyContext.getContext().sendBroadcast(i);
		        	}
		        	catch(Exception e)
		        	{
		        		temperature = 0.0f;
		        		connected = false;
		        	}
		          }
		        catch (Exception e) {
		        	System.out.println("BlueTemp problem: "+e.toString());
		        	try { socket.close(); } catch(Exception ex1) { System.out.println("Shutdown problem"); } finally 
		        	{
		        		connected = false;
		        		socket = null;
		        		temperature = 0;
		        	}
		        }
	      }
	    }
	  };

	  private void AttemptBtConnection()
	  {
		  if(device == null) return;
		  try
		  {
			 Method m;
			 m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
			 socket = (BluetoothSocket)m.invoke(device, Integer.valueOf(1));
			 //Attempt to connect.
			 socket.connect();
			 out = socket.getOutputStream();
			 in = socket.getInputStream();
		  }
		  catch(Exception e)
		  {
			  BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
			  adapter.cancelDiscovery();
		  }
	  }

	  @Override
	  protected void onHandleIntent(Intent intent) {
		  
	  }

}
