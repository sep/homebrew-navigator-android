package com.example.homebrewnavigator;

import android.R.string;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import com.example.homebrewnavigator.R.id;


public class ThermometerActivity extends Activity {
	
	private BluetoothSocket socket = null;
	private BluetoothDevice[] bluetooth_devices = null;
	private OutputStream out = null;
	private InputStream in = null;
	
	private float temperature = 0;
	private Thread thread;
	private boolean keep_running;
	final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			updateTempLabel((String)msg.obj);
		}
		
	};
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermometer);
        
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        
        //We should never actually use the below value
        String[] devices_strings = { "No Adapter Found" };
        
        if (adapter != null)
        {
        	Set<BluetoothDevice> devices = adapter.getBondedDevices();
        	
        	devices_strings = new String[devices.size()];
            bluetooth_devices = new BluetoothDevice[devices.size()];
            Iterator iter = devices.iterator();
            
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
                
                TextView status = (TextView) findViewById(R.id.textView3);
                status.setText("Connected to " + device.getName());
                
                startTempThread(in);
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
    }
	
	private void updateTempLabel(String text)
	{
		TextView themoData = (TextView) findViewById(R.id.textView4);
        themoData.setText("Temp: " + text);
        
        themoData.postInvalidate();
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		
		kill();
	
	    try
	    {
	      if (in != null) in.close();
	      if (out != null) out.close();
	      if (socket != null) socket.close();
	    }
	    catch (Exception e) { System.out.println(e.toString()); }
	
	    socket = null;
	}
	
	public void startTempThread(InputStream in)
	{
		this.in = in;
		keep_running = true;
		Thread thread = new Thread(runnable);
		thread.start();
	}
	
	public void kill()
	{
	    keep_running = false;
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
	          // System.out.println("Read in "+ch);

	          //TODO: What does this actually do for us?  Is "Nothing" 0xff?
	          //if (index < 0)
	          //{
	          //  if (ch == 0xff) { index++; }
	          //  else { index = -2; }
	          //}
	          //  else
	          //{
	            // System.out.println("index="+index);
	              
	        	Message msg = handler.obtainMessage();
	        	msg.obj = temp;
	        	handler.sendMessage(msg);
	              
	              //postInvalidate();
	            //}
	          }
	        catch (Exception e) { System.out.println("BlueTemp problem: "+e.toString()); }
	      }
	    }
	  };

}
