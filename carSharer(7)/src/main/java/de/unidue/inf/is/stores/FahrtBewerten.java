package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import de.unidue.inf.is.utils.DBUtil;

public class FahrtBewerten implements Closeable{
	private Connection connection;
	private boolean complete; 
	
	
	//Der Check ob der User die Fahrt schon bewertet hat 
	
	public boolean RatingCheck (int benutzer, int fid) {
		boolean rated = false;
		
		String QueryCheck = "select * FROM dbp058.schreiben s where s.benutzer = ? AND s.Fahrt = ? ";
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
			try(PreparedStatement preBefore = connection.prepareStatement(QueryCheck)){
				preBefore.setInt(1, benutzer);
				preBefore.setInt(2, fid);
				ResultSet se = preBefore.executeQuery();
				//checking if ResultSet is Voll 
				if (se.next() == true) {
			    	rated = true;
				}
				else {
					rated = false;
				}

			    preBefore.close();
			    connection.commit();
				
			} catch (SQLException e2) {
				
				e2.printStackTrace();
			}		
		}
		
		return rated;
	}
	
	//Die Fahrt bewerten nur wenn der User diese nicht bewertet hat
	public void fahrtBewerten(String BewertungsText,Timestamp erstellungsdatumnew, int Bewertungsrate, int benutzer, int FahrtTogetBewertet1) {
		//Die Bewertung in Bewertung Tabelle schreiben
		String QueryBewertung = "INSERT INTO dbp058.bewertung (textnachricht, erstellungsdatum, rating) VALUES	(?, ?, ?)";
		//Lesen der Bewertung ID von der hinzugefügte Bewertung
		String QueryRead = "select beid FROM dbp058.Bewertung ORDER BY ERSTELLUNGSDATUM DESC FETCH FIRST 1 ROW ONLY ";
		//Der Benutzer der diese Bewertung schreibt speichern mit Hilfe von BEID die gelesen wurde
		String QuerySchreiben = "INSERT INTO dbp058.schreiben (benutzer, fahrt, bewertung) VALUES (?, ?, ?)";	
		
		
		//Damit wir WW-Conflict vermeiden haben wir es alle
		try(PreparedStatement pre = connection.prepareStatement(QueryBewertung)){
			
			pre.setString(1, BewertungsText);
			pre.setTimestamp(2, erstellungsdatumnew);
			pre.setInt(3, Bewertungsrate);
			
			pre.executeUpdate();
			pre.close();
			connection.commit();
			
			} catch (SQLException e) {
					e.printStackTrace();
				
			}
			
			
			
			try(PreparedStatement pre1 = connection.prepareStatement(QueryRead)){
				ResultSet se = pre1.executeQuery();
				int beid = 0;
				if (se.next()) {
					beid= se.getInt("beid");
				}
				se.close();
				pre1.close();
				try(PreparedStatement pre2 = connection.prepareStatement(QuerySchreiben)){
					pre2.setInt(1, benutzer);
					pre2.setInt(2, FahrtTogetBewertet1);
					pre2.setInt(3, beid);
					
					pre2.executeUpdate();
					pre2.close();
					connection.commit();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
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
		    } catch (SQLException e) {
		    	throw new StoreException(e);
		    } finally {
		    	try {
		           	connection.close();
		        } catch (SQLException e) {
		            throw new StoreException(e);
		        }
		    }
	    }
	}
}
