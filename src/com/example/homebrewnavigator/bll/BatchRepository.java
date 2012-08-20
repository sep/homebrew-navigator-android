package com.example.homebrewnavigator.bll;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import com.example.homebrewnavigator.MyContext;

public class BatchRepository {
	
	public ArrayList<Batch> GetAll(){	
		Context ctx = MyContext.getContext();
				
		String[] files = ctx.fileList();
		ArrayList<Batch> batches = new ArrayList<Batch>();
		try{
		for(String s : files){
			FileInputStream fos = ctx.openFileInput(s);
			Serializer serializer = new Persister();
			batches.add(serializer.read(Batch.class, fos));
			fos.close();
		}
		}catch(Exception e){
			e.printStackTrace();			
		}
			
		return batches;
	}
	
	public void SaveBatch(Batch b)	{
		Context ctx = MyContext.getContext();
		try{
		FileOutputStream fos = ctx.openFileOutput(b.getName(), Context.MODE_PRIVATE);
		
			Serializer serializer = new Persister();
			serializer.write(b, fos);
			fos.close();
		}catch(Exception e){
			e.printStackTrace();		
		}finally
		{
			
		}
	}
			
}
