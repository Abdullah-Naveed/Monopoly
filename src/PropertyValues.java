package src;
/* This class is in charge of the values of the property, in terms of storing, returning and naming them */
public class PropertyValues extends property 
{
	private static int value;
	private String name;
	
	PropertyValues(int value, String name) // This function stores the values
	{
		PropertyValues.value = value;
		this.name = name;
	}

	public static  int GetValue() // This function returns the value of the property
	{
		return value;
	}
	
	public String GetName() // This function returns the name of the property
	{
		return name;
	}
	
	

}