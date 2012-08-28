package datamodel;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class Batch {
	public Batch(){}
	public Batch(String pname, String recipeBrewed){		
		name = pname;
		recipeBrewedName = recipeBrewed;
	}
	
	@Element(required=true)
	private String name;
	
	@Element(required=false)
	private double originalGravity;
	
	@Element(required=false)
	private double finalGravity;
	
	@Element(required=true)
	private String recipeBrewedName;
	
	public String getName(){
		return name;
	}
	
	public void setName(String theName){
		name = theName;		
	}
}
