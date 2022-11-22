package de.unidue.inf.is.application;


public class CarApplication {
	private static CarApplication instance;
	/**
	 *  Singleton pattern hätten wir für den gesamten Projekt nutzen sollen
	 */

	public static CarApplication getInstance() {
		if (instance == null) {
			instance = new CarApplication();
		}
		return instance;
	}
		
	/*
	 * Prüfen ob die Zahl(aus String geparst) numerisch ist
	 * */
	public static boolean isNumeric(String strn) {
		int strn1 = 0;
		strn1 = Integer.parseInt(strn);
		
		if (strn == null || strn1 < 0 )
		{
			System.out.println("strn == null || wert == true || strn1 < 0");
			return false;
		}
	    
	    try {
	        double d = Double.parseDouble(strn);
	        System.out.println(d);
	    } catch (NumberFormatException nfe) {
	        return false;
	        
	    }
	    return true;
	}
	
	



}
