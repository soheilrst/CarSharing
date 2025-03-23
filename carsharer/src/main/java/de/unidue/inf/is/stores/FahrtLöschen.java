package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import de.unidue.inf.is.domain.fahrtexception;
import de.unidue.inf.is.utils.DBUtil;

public class FahrtLöschen implements Closeable {
	private Connection connection;
	private boolean complete;

	// Konstruktor
	public FahrtLöschen() throws fahrtexception {
		try {
			connection = DBUtil.getExternalConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	public boolean benutzerCheck(int benutzer, int fid) {
		boolean ersteller = false;

		String QueryCheck = "select *  FROM fahrt f where f.fid = ? AND f.anbieter = ? ";
		try {

			connection = DBUtil.getExternalConnection();
			connection.setAutoCommit(false);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (connection != null) {
			try (PreparedStatement preBefore = connection.prepareStatement(QueryCheck)) {
				preBefore.setInt(1, fid);
				preBefore.setInt(2, benutzer);
				ResultSet se = preBefore.executeQuery();
				if (se.next() == true) {
					ersteller = true;
				} else {
					ersteller = false;
				}

				preBefore.close();
				connection.commit();

			} catch (SQLException e2) {

				e2.printStackTrace();
			}
		}

		return ersteller;
	}

	public void fahrtlöschen(int fahrtToBeDeleted) {
		// wir mussen die Fahrt, ihre Reservierungen und ihre Bewertungen löschen
		String QueryLöschen = "delete from reservieren where fahrt = ?  ";
		String bewertungloschen = " delete from bewertung  where beid in(select Bewertung from schreiben s where s.Fahrt=?)";
		String fahrtloschen = "delete from fahrt where fid=?";
		try (PreparedStatement pre1 = connection.prepareStatement(QueryLöschen)) {

			pre1.setInt(1, fahrtToBeDeleted);

			pre1.executeUpdate();
			pre1.close();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (PreparedStatement pre2 = connection.prepareStatement(bewertungloschen)) {

			pre2.setInt(1, fahrtToBeDeleted);

			pre2.executeUpdate();
			pre2.close();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (PreparedStatement pre3 = connection.prepareStatement(fahrtloschen)) {

			pre3.setInt(1, fahrtToBeDeleted);

			pre3.executeUpdate();
			pre3.close();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (connection != null) {
				try {
					connection.close();
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
