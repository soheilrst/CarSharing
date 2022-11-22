package de.unidue.inf.is.domain;
/*
 * Eine Klasse von Rating 
 * */


public class Rating {
	private String email;
	private String rating;
	private String textnachricht;
	private int fahrt;

	

	public Rating(String email, String rating, String textnachricht, int fahrt) {
		super();
		this.email = email;
		this.rating = rating;
		this.textnachricht = textnachricht;
		this.fahrt= fahrt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getTextnachricht() {
		return textnachricht;
	}

	public void setTextnachricht(String textnachricht) {
		this.textnachricht = textnachricht;
	}
	public int getFahrt() {
		return fahrt;
	}

	public void setFahrt(int fahrt) {
		this.fahrt = fahrt;
	}

	

}
