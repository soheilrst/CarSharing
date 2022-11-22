package de.unidue.inf.is.domain;


/*
 * Eine Klasse der reservierten Fahrten 
 * */
public class reservierteFahrten {
	private String von; 
	private String nach; 
	private String status;
	private int fid;
	
	public reservierteFahrten(String von, String nach, String status, int fid) {
		this.von = von;
		this.nach = nach;
		this.status = status;
		this.fid = fid;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	} 
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	

}
