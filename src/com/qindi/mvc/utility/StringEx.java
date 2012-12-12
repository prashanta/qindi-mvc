package com.qindi.mvc.utility;

/**
 * A utility class to do String manipulations
 * @author Prashanta Shrestha
 *
 */
public class StringEx {
			
	
	/**
	 * Replace white spaces with "&nbsp;"
	 * 
	 * @param data	text to work with 
	 * @return		modified text
	 */
	public static String replaceWhiteSpace(String data){
		boolean d = true;
		while(d){
			int i = data.indexOf(' ');
			if(i<0)	d = false;
			else{
				CharSequence temp1 = data.subSequence(0 , i);
				CharSequence temp2 = data.subSequence(i+1, data.length());
				data = temp1 + "&nbsp;" + temp2;
			}
		}
		return data;
	}
	
	/**
	 * Replace _char with another char
	 * 
	 * @param _char		the character to replace
	 * @param data		text to work with
	 * @param replace	the character to replace with
	 * @return			modified text
	 */
	public static String replaceCharWithString(String _char , String data , String replace){
		boolean d = true;
		while(d){
			int i = data.indexOf(_char);
			if(i<0)	d = false;
			else{
				CharSequence temp1 = data.subSequence(0 , i);
				CharSequence temp2 = data.subSequence(i+1, data.length());
				data = temp1 + replace + temp2;
			}
		}
		return data;
	}
	
	/**
	 * Convert "_" separated strings to camel case
	 * 
	 * @param data	text to work with
	 * @return		modified text
	 */
	public static String toCamelCase(String data){
		data = data.toLowerCase();
		data = replaceCharWithString("_", data, " ");
		boolean d = true;
		while(d){
			int i = data.indexOf(" ");
			if(i<0)	d = false;
			else{				
				CharSequence temp1 = data.subSequence(0 , i);
				CharSequence temp2 = data.subSequence(i+2, data.length());
				data = temp1 + data.substring(i+1,i+2).toUpperCase() + temp2;
			}	
		}
		return data;
	}
	
	/**
	 * Get HEX representation of given String
	 * 
	 * @param data	text to work with
	 * @return		modified text
	 */
	public static String getHexString(String data){
	    int code;
	    String hex = new String();
	    for(int i = 0; i < data.length(); i++) {
	    	code = (int)data.charAt(i);
	    	if(i>0) hex += "+";
	    	hex += "0x"+Integer.toHexString(code);	    	
	    }
	    return hex;
	}
	
	/**
	 * Get name of day from index 
	 * 
	 * @param day	Non-zero based integer representation of day of week
	 * @return		Name of day
	 */
	public static String getNameofDay(int day){
		switch(day){
			case 1: return "Sunday";
			case 2: return "Monday";
			case 3: return "Tuesday";
			case 4: return "Wednesday";
			case 5: return "Thursday";
			case 6: return "Friday";
			case 7: return "Saturday";
			default: return "Unknown";
		}	
	}

	/**
	 * Get index of day from String
	 * 
	 * @param day	Name of day
	 * @return		Non-zero based integer representation of day of week	
	 */
	public static int getIndexofDay(String day){
		day = day.toLowerCase();
		if((day.equals("sunday")) || (day.equals("sun")))	return 1;
		if((day.equals("monday")) || (day.equals("mon")))	return 2;
		if((day.equals("tuesday")) || (day.equals("tue")))	return 3;
		if((day.equals("wednesday")) || (day.equals("wed")))	return 4;
		if((day.equals("thursday")) || (day.equals("thu")))	return 5;
		if((day.equals("friday")) || (day.equals("fri")))	return 6;
		if((day.equals("saturday")) || (day.equals("sat")))	return 7;
		return 0;				
	}
	
	/**
	 * Get Name of month from index
	 * 
	 * @param month	Non-zero based integer representation of month
	 * @return		Name of month	
	 */
	public static String getMonthFromIndex(int month){
		if(month==1)	return "Jan";
		if(month==2)	return "Feb";
		if(month==3)	return "Mar";
		if(month==4)	return "Apr";
		if(month==5)	return "May";
		if(month==6)	return "Jun";
		if(month==7)	return "Jul";
		if(month==8)	return "Aug";
		if(month==9)	return "Sep";
		if(month==10)	return "Oct";
		if(month==11)	return "Nov";
		if(month==12)	return "Dec";
		return "Jan";				
	}
		
	/**
	 * Get index of month from String
	 * 
	 * @param month	Name of month
	 * @return		Non-zero based integer representation of day of week	
	 */
	public static int getIndexofMonth(String month){
		month = month.toLowerCase();
		if(month.equals("jan"))	return 1;
		if(month.equals("feb"))	return 2;
		if(month.equals("mar"))	return 3;
		if(month.equals("apr"))	return 4;
		if(month.equals("may"))	return 5;
		if(month.equals("jun"))	return 6;
		if(month.equals("jul"))	return 7;
		if(month.equals("aug"))	return 8;
		if(month.equals("sep"))	return 9;
		if(month.equals("oct"))	return 10;
		if(month.equals("nov"))	return 11;
		if(month.equals("dec"))	return 12;
		return 0;				
	}
	
	public static void main(String[] args) {
		String temp = "string1,string2,string3";		
		//System.out.println("data = [" + toCamelCase(temp) +"]");
		String[] temp2 = temp.split(",");
		for(int i=0 ; i<temp2.length;i++)
		System.out.println("data = [" + temp2[i] +"]");
	}    
}