package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.unidue.inf.is.domain.FahrtD;
import de.unidue.inf.is.domain.Rating;
import de.unidue.inf.is.utils.DBUtil;

public class FahrtDetail implements Closeable {
	private static Connection connection ;
	private boolean complete;
	public static List<FahrtD> fdselected = new ArrayList<FahrtD>();
	public static List<Rating> ratings = new ArrayList<Rating>();
	public static int durchschnitt;
	

	/*
	 * alle Fahrt Detauls werden hier aus der Database aufgerufen 
	 * */
	public void detail(int fid) throws RuntimeException{
	
		String QuerySelected = "select email,startort,status,zielort,reser,fahrtkosten, Beschreibung,FAHRTDATUMZEIT,MAXPLAETZE from dbp058.benutzer b,dbp058.fahrt f LEFT OUTER JOIN (select fahrt,sum(r.ANZPLAETZE)as reser FROM dbp058.reservieren r Group by r.Fahrt)a on a.fahrt = f.fid where b.bid = f.anbieter and fid = ? ";
		String QueryDurchschnitt = "select avg(rating) as durchschnitt FROM dbp058.FAHRT f INNER JOIN(select * from dbp058.bewertung b INNER JOIN dbp058.schreiben r ON  b.beid = r.bewertung)a on a.fahrt = f.fid where fid = ? ";
		String QueryRatings = "select fahrt,email,rating, textnachricht, benutzer from dbp058.benutzer be INNER JOIN(select * from dbp058.schreiben s, dbp058.bewertung b where b.beid= s.bewertung)o ON be.bid = o.benutzer AND o.fahrt = ? order by erstellungsdatum desc ";
		try {connection = DBUtil.getExternalConnection();
		connection.setAutoCommit(false);
		
		}catch(SQLException e) {
				e.printStackTrace();
		 }
		 if(connection != null) {

			try(PreparedStatement predurch = connection.prepareStatement(QueryDurchschnitt)){
					predurch.setInt(1,fid);
					ResultSet ra=predurch.executeQuery();
					while (ra.next()) {
						int durch = ra.getInt("durchschnitt");	
							durchschnitt= durch;
							
					}
					
					connection.commit();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			 
			 
			 
			 
			 try(PreparedStatement pre = connection.prepareStatement(QuerySelected)){
					//to clear the list and avoid dobulicated information in our website
					fdselected.clear();
					ratings.clear();
					
					pre.setInt(1,fid);
					ResultSet FD=pre.executeQuery();
					
					while (FD.next()) {
						String anbieter=FD.getString("email");
						String Fahrdatumunduhrzeit=FD.getString("FAHRTDATUMZEIT");
						String von=FD.getString("startort");
						String nach=FD.getString("zielort");
						int reser=FD.getInt("reser");
						int fahrtkosten=FD.getInt("fahrtkosten");
						String status=FD.getString("status");
						int maxPlaetze = FD.getInt("MAXPLAETZE");
						String beschreibung=FD.getString("Beschreibung");
						
						int frei = maxPlaetze - reser;
						
						//Im Fall dass die Beschreibung null war 
						if (beschreibung == null || beschreibung.length() == 0) { beschreibung = "";}
						
						//Wenn die Fahrt Positiv ist 
						if (frei >= 0) {
							
							FahrtD Fobject =new FahrtD(anbieter,Fahrdatumunduhrzeit,von,nach,frei,fahrtkosten,status,beschreibung);
							fdselected.add(Fobject);
							
						}
						
				}
				connection.commit();
					
				}catch (SQLException e) {
					e.printStackTrace();	
				}
				
				
				
				try(PreparedStatement pre1 = connection.prepareStatement(QueryRatings)){
					pre1.setInt(1,fid);
					ResultSet ra=pre1.executeQuery();
					System.out.println("executeQuery wurde ausgeführt ");
					
					while (ra.next()) {
						int fahrt = ra.getInt("fahrt");
						String email=ra.getString("EMAIL");
						String rating=ra.getString("rating");
						String textnachricht=ra.getString("textnachricht");
						Rating Fobject =new Rating(email,rating,textnachricht,fahrt);
						ratings.add(Fobject);
						
						
					}
					
				connection.commit();
				
				
				} catch (SQLException e) {
					e.printStackTrace();
				}
				 finally {
						//Resourcen schließen
					if(connection != null ) {
						try {
							connection.close() ;
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
		 }
}
	
	public void complete() {
	     complete = true;
	}
	
	
	 @Override
		public void close() throws IOException {
		 if (connection != null) {
			 try {
		         if (complete) {
		             	connection.commit();
		         }
		         else {
		                connection.rollback();
		        }
		     }
		     catch (SQLException e) {
		         throw new StoreException(e);
		     }
		     finally {
		         try {
		        	 connection.close();
		         }
		         catch (SQLException e) {
		             throw new StoreException(e);
		         }

		     }
		 }
		}
}
