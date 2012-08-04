package com.example.homebrewnavigator.bll;

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
	public TemperatureStep(int i, IIntentDispatcher d) {
		dispatcher = d;
		Intent serviceIntent = new Intent(MyContext.getContext(), TemperatureService.class);	        
		MyContext.getContext().bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
	}

	@Override
	public void execute() {		
		
		  if (bound && service.isConnected()){
			  float t =  service.GetLastTemperature();
			  if (t >= this.target)				  
				  dispatcher.fireStepCompleteIntent();
		  }		  
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
}
