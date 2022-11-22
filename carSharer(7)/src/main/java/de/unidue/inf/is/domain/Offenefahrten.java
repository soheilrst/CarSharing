package de.unidue.inf.is.domain;


/*
 * Klasse der Offenen Fahrten
 * */
public class Offenefahrten {
	private String startort; 
	private String  zielort;
	private int fahrtkosten; 
	private int freiplatz;
	private int fid;
	
	
	public Offenefahrten(String startort, String zielort, int fahrtkosten2, int freiplatz, int fid) {
		super();
		this.startort = startort;
		this.zielort = zielort;
		this.fahrtkosten = fahrtkosten2;
		this.freiplatz = freiplatz;
		this.fid = fid;
	}
	public String getStartort() {
		return startort;
	}
	public void setStartort(String startort) {
		this.startort = startort;
	}
	public String getZielort() {
		return zielort;
	}
	public void setZielort(String zielort) {
		this.zielort = zielort;
	}
	public int getFahrtkosten() {
		return fahrtkosten;
	}
	public void setFahrtkosten(int fahrtkosten) {
		this.fahrtkosten = fahrtkosten;
	}
	public int getFreiplatz() {
		return freiplatz;
	}
	public void setFreiplatz(int freiplatz) {
		this.freiplatz = freiplatz;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	

}
