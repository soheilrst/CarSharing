package de.unidue.inf.is.domain;

import java.math.BigDecimal;

public class Such {
	private String StartOrt;
	private String zielOrt;
	private String fahrdatumunduhrzeit;
	private String icon1;
	private BigDecimal fahrtkosten1;
	private int fid;

	public Such(String startOrt, String zielOrt, String fahrdatumunduhrzeit, String icon1, BigDecimal fahrtkosten1,
			int fid) {
		super();
		this.StartOrt = startOrt;
		this.zielOrt = zielOrt;
		this.fahrdatumunduhrzeit = fahrdatumunduhrzeit;
		this.icon1 = icon1;
		this.fahrtkosten1 = fahrtkosten1;
		this.fid = fid;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public void setStartOrt(String startOrt) {
		this.StartOrt = startOrt;
	}

	public String getZielOrt() {
		return zielOrt;
	}

	public void setZielOrt(String zielOrt) {
		this.zielOrt = zielOrt;
	}

	public String getFahrdatumunduhrzeit() {
		return fahrdatumunduhrzeit;
	}

	public void setFahrdatumunduhrzeit(String fahrdatumunduhrzeit) {
		this.fahrdatumunduhrzeit = fahrdatumunduhrzeit;
	}

	public String getIcon1() {
		return icon1;
	}

	public void setIcon1(String icon1) {
		this.icon1 = icon1;
	}

	public String getStartOrt() {
		return StartOrt;
	}

	public BigDecimal getFahrtkosten1() {
		return fahrtkosten1;
	}

	public void setFahrtkosten1(BigDecimal fahrtkosten1) {
		this.fahrtkosten1 = fahrtkosten1;
	}
}
