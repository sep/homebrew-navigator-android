package com.example.homebrewnavigator.bll;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.homebrewnavigator.MyContext;
import com.example.homebrewnavigator.TemperatureService;
import com.example.homebrewnavigator.ThermometerActivity;
import com.example.homebrewnavigator.TemperatureService.TemperatureBinder;

public class TemperatureStep extends RecipeStep<Integer> {

	private IIntentDispatcher dispatcher; 
	private boolean bound;
	private TemperatureService service;

	public TemperatureStep(Integer target, String units, Integer actualValue,
			String instruction) {
		super(target, units, actualValue, instruction);
		Intent serviceIntent = new Intent(MyContext.getContext(), TemperatureService.class);	        
		MyContext.getContext().bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
		dispatcher = new IntentDispatcher();
	}

	public TemperatureStep(Integer target, String instruction) {
		super(target, "\u00B0F", null, instruction);
		Intent serviceIntent = new Intent(MyContext.getContext(), TemperatureService.class);	        
		MyContext.getContext().bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
		
		dispatcher = new IntentDispatcher();
	}
	
	public TemperatureStep(int i, IIntentDispatcher d) {
		super(i, "\u00B0F", null, "wha");
		dispatcher = d;
		Intent serviceIntent = new Intent(MyContext.getContext(), TemperatureService.class);	        
		MyContext.getContext().bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
		
		dispatcher = new IntentDispatcher();		
	}
	
	@Override
	public void execute() {		

		 
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
    
  private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver(){
	  @Override 
	  public void onReceive(Context context, Intent intent) {
		  execute();
	  };
  };
}
