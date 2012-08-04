package com.example.homebrewnavigator;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.TextView;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import com.example.homebrewnavigator.TemperatureService.TemperatureBinder;


public class ThermometerActivity extends Activity {
	
	private boolean keep_running;
	
	private boolean bound;
	private TemperatureService service;
	
	private final class UpdateInfo
	{
		public String temperature;
		public boolean connected;
	}
	
	final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			updateTempLabel((UpdateInfo)msg.obj);
		}
		
	};
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermometer);
        
        Intent serviceIntent = new Intent(ThermometerActivity.this, TemperatureService.class);
        
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
        
        startTempThread();
    }
	
	/** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection connection = new ServiceConnection() {

    	@Override
        public void onServiceConnected(ComponentName className,
                IBinder serviceBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            TemperatureBinder binder = (TemperatureBinder) serviceBinder;
            service = binder.getService();
            bound = true;
        }

    	@Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };
	
	private void updateTempLabel(UpdateInfo info)
	{
		TextView themoData = (TextView) findViewById(R.id.textView4);
        themoData.setText("Temp: " + info.temperature);
        themoData.postInvalidate();
        
        TextView connectedInfo = (TextView)findViewById(R.id.textView3);
        if(info.connected)
        {
        	connectedInfo.setText( "Connected!" );
        }
        else
        {
        	connectedInfo.setText( "Not connected :(");
        }
        
        connectedInfo.postInvalidate();
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		
		kill();
		
		if(bound)
		{
			unbindService(connection);
			bound = false;
		}
	}
	
	public void startTempThread()
	{
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
	      while(keep_running)
	      {
	        try
	        {
	        	
	            String temp = "00.0";
	            boolean connStatus = false;
	            
	            if(service != null && bound)
	            {
	            	float tempFlt = service.GetLastTemperature();
	            	temp = Float.toString(tempFlt);
	            	connStatus = service.isConnected();
	            }
	            
	            UpdateInfo info = new UpdateInfo();
	            info.temperature = temp;
	            info.connected = connStatus;
	            
	        	Message msg = handler.obtainMessage();
	        	msg.obj = info;
	        	handler.sendMessage(msg);
	              
	        	Thread.sleep(1000);
	              //postInvalidate();
	            //}
	          }
	        catch (Exception e) { System.out.println("BlueTemp problem: "+e.toString()); }
	      }
	    }
	  };

}
