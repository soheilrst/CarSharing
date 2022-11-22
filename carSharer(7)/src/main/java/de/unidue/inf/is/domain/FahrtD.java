package de.unidue.inf.is.domain;

/*
 * Eine Klasse zu Fahrtdetails
 * */

public class FahrtD {
	  private String anbieter;
	  private String fahrdatumunduhrzeit;
	  private String von;
	  private String nach;
	  private int anzahlfreieplaetze;
	  private int fahrtkosten;
	  private String status;
	  private String beschreibung;
	
	  
	public FahrtD(String anbieter, String fahrdatumunduhrzeit, String von, String nach, int anzahlfreieplaetze,int fahrtkosten, String status, String beschreibung) {
		super();
		this.anbieter = anbieter;
		this.fahrdatumunduhrzeit = fahrdatumunduhrzeit;
		this.von = von;
		this.nach = nach;
		this.anzahlfreieplaetze = anzahlfreieplaetze;
		this.fahrtkosten = fahrtkosten;
		this.status = status;
		this.beschreibung = beschreibung;
	}
	public String getAnbieter() {
		return anbieter;
	}
	public void setAnbieter(String anbieter) {
		this.anbieter = anbieter;
	}
	public String getFahrdatumunduhrzeit() {
		return fahrdatumunduhrzeit;
	}
	public void setFahrdatumunduhrzeit(String fahrdatumunduhrzeit) {
		this.fahrdatumunduhrzeit = fahrdatumunduhrzeit;
	}
	public String getVon() {
		return von;
	}
	public void setVon(String von) {
		this.von = von;
	}
	public String getNach() {
		return nach;
	}
	public void setNach(String nach) {
		this.nach = nach;
	}
	public int getAnzahlfreieplaetze() {
		return anzahlfreieplaetze;
	}
	public void setAnzahlfreieplaetze(int anzahlfreieplaetze) {
		this.anzahlfreieplaetze = anzahlfreieplaetze;
	}
	public int getFahrtkosten() {
		return fahrtkosten;
	}
	public void setFahrtkosten(int fahrtkosten) {
		this.fahrtkosten = fahrtkosten;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBeschreibung() {
		return beschreibung;
	}
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	  
	
	

	
	  
}
