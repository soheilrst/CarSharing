package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.unidue.inf.is.domain.Offenefahrten;
import de.unidue.inf.is.domain.reservierteFahrten;
import de.unidue.inf.is.utils.DBUtil;

public class viewMaindb implements Closeable {
	
	private Connection connection; 
	private boolean complete; 
	public List<reservierteFahrten> resList = new ArrayList<reservierteFahrten>();
	public List<Offenefahrten> ofFah = new ArrayList<Offenefahrten>();
	
	public void rfdb(int BenutzerID) throws RuntimeException {
		//sql anfrage fur reservierten fahrten
		String Queryresfa = "SELECT fid,startort, zielort, status FROM dbp058.reservieren r, dbp058.Fahrt f, dbp058.benutzer b where r.kunde = ? AND r.kunde = b.bid AND f.fid = r.fahrt";
		///sql anfrage fur offene fahrten
		String QueryOffe = "select fid,startort,zielort,reser,fahrtkosten, MAXPLAETZE  from dbp058.fahrt f LEFT OUTER JOIN (select fahrt,sum(r.ANZPLAETZE) as reser FROM  dbp058.reservieren r Group by r.Fahrt)a on a.fahrt = f.fid";
		try {
			connection = DBUtil.getExternalConnection();
			/*connection.setAutoCommit(false) will allow you to group multiple 
			 * subsequent Statements under the same transaction.
			*/
			connection.setAutoCommit(false);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		if(connection != null) {
			/*
			 * Zur Vermeidugn von SQL_injection Bösarrtige Statments werden vermieden
			 */
			try(PreparedStatement pre1 = connection.prepareStatement(Queryresfa)){
				// Setzen wir in der Query welcher Benutzer wird betroffen
				pre1.setInt(1, BenutzerID);
				/*
				 * Rückgabe Daten von der Datenbank
				 */
				ResultSet of = pre1.executeQuery();
				//wir bekommen die Fahrt für den Benutzer 6
				while (of.next()) {
					String StartOrt = of.getString("startort");
					String ZielOrt = of.getString("zielort");
					String status = of.getString("status");
					int fid = of.getInt("fid");
					
					reservierteFahrten rv = new reservierteFahrten(StartOrt, ZielOrt,status,fid);
					resList.add(rv);
				}
				
				pre1.close();
				of.close();
			} catch (SQLException e) {
				e.printStackTrace();	
			} 
			try(PreparedStatement pre2 = connection.prepareStatement(QueryOffe)){
				ResultSet rs = pre2.executeQuery();
				while (rs.next()) {
				String startort = rs.getString("startort");
				String zielort = rs.getString("zielort");
				int fahrtkosten = rs.getInt("fahrtkosten");
				int reserviert = rs.getInt("reser");
				int maxPlaetze = rs.getInt("MAXPLAETZE");
				int fid = rs.getInt("fid");
				
				int frei = maxPlaetze - reserviert;
				
				
				if (frei >0) {
					Offenefahrten of = new Offenefahrten(startort,zielort,fahrtkosten,frei,fid);
					ofFah.add(of);
				}
			
				}
				
				pre2.close();
				rs.close();
				connection.commit();
				
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
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
	     }catch (SQLException e) {
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