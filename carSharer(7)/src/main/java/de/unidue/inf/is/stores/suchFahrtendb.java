package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import de.unidue.inf.is.domain.Such;
import de.unidue.inf.is.utils.DBUtil;

public class suchFahrtendb implements Closeable {
	private Connection connection; 
	private boolean complete; 
	
	public List<Such> suchergebniss = new ArrayList<Such>();
	
	
	
	public void suchen(String startOrt, String zielOrt, Timestamp fahrtdatumzeit1) throws RuntimeException {
		
		String Queryresfa = "select fid,icon,startort,zielort,fahrtkosten from dbp058.Fahrt f, dbp058.transportmittel t where f.transportmittel=t.tid AND f.startort = ? AND f.zielort = ? AND f.fahrtdatumzeit > ? AND status= ? ";
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
				try(PreparedStatement pre = connection.prepareStatement(Queryresfa)){
					//wir sollen die fahrts bekommen die noch offen sind
					String status = "offen";
					pre.setString(1, startOrt);
					pre.setString(2, zielOrt);
					pre.setTimestamp(3, fahrtdatumzeit1);
					pre.setString(4, status);			
					ResultSet FD = pre.executeQuery();
					//wir bekommen die Fahrt für den Benutzer 6
					while (FD.next()) {
						String StartOrt1 = FD.getString("startort");
						String ZielOrt1 = FD.getString("zielort");
						BigDecimal fahrtkosten1 = FD.getBigDecimal("fahrtkosten");
						int fid = FD.getInt("fid");
						String icon=FD.getString("icon");
						String icon0 = icon.substring(4);
						String icon1 = ".."+icon0;
						
						Such rv = new Such(StartOrt1, ZielOrt1,icon1,fahrtkosten1,fid);
						
						suchergebniss.add(rv);
					}
					pre.close();
					connection.commit();
				} catch (SQLException e) {
					e.printStackTrace();	
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