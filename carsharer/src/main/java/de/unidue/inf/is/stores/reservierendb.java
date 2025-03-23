package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.unidue.inf.is.domain.fahrtexception;
import de.unidue.inf.is.utils.DBUtil;

public class reservierendb implements Closeable {

	private Connection connection;
	private boolean complete;

	public reservierendb() throws fahrtexception {
		try {
			connection = DBUtil.getExternalConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	// checkt ob der Nutzer schon reserviert hat
	public boolean reservierungCheck(int benutzer, int fahrtTobereserved) {
		boolean reserved = false;
		String query = "SELECT 1 FROM reservieren r WHERE r.kunde = ? AND r.Fahrt = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, benutzer);
			stmt.setInt(2, fahrtTobereserved);
			try (ResultSet rs = stmt.executeQuery()) {
				reserved = rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reserved;
	}

	public void reservieren(int benutzer, int fahrtTobereserved, int anzahl) throws SQLException {
		String query = "INSERT INTO reservieren (kunde, fahrt, anzplaetze) VALUES (?, ?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, benutzer);
			stmt.setInt(2, fahrtTobereserved);
			stmt.setInt(3, anzahl);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
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
				} else {
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
