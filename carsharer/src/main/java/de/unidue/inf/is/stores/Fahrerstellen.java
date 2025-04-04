package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import de.unidue.inf.is.domain.fahrtexception;
import de.unidue.inf.is.utils.DBUtil;

public final class Fahrerstellen implements Closeable {
	private Connection connection;
	private boolean complete;

	// Konstruktor zum Aufbau einer neuen Verbindung bei jeder Instanziierung.
	public Fahrerstellen() throws fahrtexception {
		try {
			connection = DBUtil.getExternalConnection();
			connection.setAutoCommit(false);

		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}

	public void fahrterstellen(String von, String bis, Timestamp fahrdatumzeit, int maxKap, int fahrtkosten,
			int anbieter, int transportmittel1, String beschreibung) throws SQLException {

		String QueryFahrt = "INSERT INTO fahrt(startort, zielort, fahrtdatumzeit, maxPlaetze, fahrtkosten, anbieter, transportmittel, beschreibung) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pre = connection.prepareStatement(QueryFahrt)) {
			pre.setString(1, von);
			pre.setString(2, bis);
			pre.setTimestamp(3, fahrdatumzeit);
			pre.setInt(4, maxKap);
			pre.setInt(5, fahrtkosten);
			pre.setInt(6, anbieter); // hart kodierte User-ID
			pre.setInt(7, transportmittel1);
			pre.setString(8, beschreibung);

			pre.executeUpdate();
			pre.close();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
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
