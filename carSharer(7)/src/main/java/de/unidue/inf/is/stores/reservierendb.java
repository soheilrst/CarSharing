package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.unidue.inf.is.domain.fahrtexception;
import de.unidue.inf.is.utils.DBUtil;


public class reservierendb implements Closeable{
	 private Connection connection;
	 private boolean complete;
	 
	//Constructor
	public reservierendb() throws fahrtexception {
		try {
			connection = DBUtil.getExternalConnection();
	        connection.setAutoCommit(false);    
	    }
	        catch (SQLException e) {
	        throw new StoreException(e);
	        }
		}
	 
	public boolean reservierungCheck (int benutzer, int fahrtTobereserved) {
		boolean reserved = false;
		//Damit checken wir ob wir die Fahrt schon reserviert haben
		String QueryCheckReservierung = "select * FROM dbp058.reservieren r where r.kunde = ? AND r.Fahrt = ? ";
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
			try(PreparedStatement preBefore = connection.prepareStatement(QueryCheckReservierung)){
				preBefore.setInt(1, benutzer);
				preBefore.setInt(2, fahrtTobereserved);
				ResultSet se = preBefore.executeQuery();
				//checking if ResultSet is Voll 
				if (se.next() == true) {
			    	reserved = true;
				}
				else {
					reserved = false;
				}

			    preBefore.close();
			    connection.commit();
				
			} catch (SQLException e2) {
				
				e2.printStackTrace();
			}		
		}
		
		return reserved;
	}
	
	
	
	
	
	
	public void reservieren(int benutzer, int fahrtTobereserved, int anzahl) throws SQLException {
		 String QueryReservieren = "INSERT INTO dbp058.reservieren (kunde, fahrt, anzPlaetze) VALUES ( ? , ? , ? ) ";
		 try(PreparedStatement pre = connection.prepareStatement(QueryReservieren)){
			 	pre.setInt(1, benutzer); //hart kodierte User-ID 
		    	pre.setInt(2, fahrtTobereserved);
		    	pre.setInt(3, anzahl);
		    	
		    	pre.executeUpdate();
		    	pre.close();
		    	connection.commit();
		 } catch (SQLException e) {
				e.printStackTrace();
		 }finally {
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
