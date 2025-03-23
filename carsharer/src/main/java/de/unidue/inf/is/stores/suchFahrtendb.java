package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import de.unidue.inf.is.domain.Such;
import de.unidue.inf.is.utils.DBUtil;

public class suchFahrtendb implements Closeable {

	private Connection connection;
	private boolean complete;

	public final List<Such> suchergebniss = new ArrayList<>();

	public suchFahrtendb() {
		try {
			connection = DBUtil.getExternalConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException("Failed to open DB connection", e);
		}
	}

	public List<Such> getSuchergebnisse() {
		return suchergebniss;
	}

	public void suchen(String startOrt, String zielOrt, Timestamp fahrtdatumzeit1) {
		String sql = """
				    SELECT f.fid, t.icon, f.startort, f.zielort, f.fahrtdatumzeit, f.fahrtkosten
				    FROM Fahrt f
				    JOIN transportmittel t ON f.transportmittel = t.tid
				    WHERE f.startort = ? AND f.zielort = ? AND f.fahrtdatumzeit > ? AND f.status = ?
				""";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, startOrt);
			stmt.setString(2, zielOrt);
			stmt.setTimestamp(3, fahrtdatumzeit1);
			stmt.setString(4, "offen");

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int fid = rs.getInt("fid");
					String icon = formatIconPath(rs.getString("icon"));
					String start = rs.getString("startort");
					String ziel = rs.getString("zielort");
					String fahrdatumunduhrzeit = rs.getString("fahrtdatumzeit");
					BigDecimal kosten = rs.getBigDecimal("fahrtkosten");

					Such fahrt = new Such(start, ziel, fahrdatumunduhrzeit, icon, kosten, fid);
					suchergebniss.add(fahrt);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Fehler beim Suchen der Fahrten", e);
		}
	}

	private String formatIconPath(String rawIcon) {
		if (rawIcon == null || rawIcon.length() < 5)
			return "";
		return ".." + rawIcon.substring(4);
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
				e.printStackTrace();
				throw new IOException("Fehler beim Commit oder Rollback", e);
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new IOException("Fehler beim Schließen der Verbindung", e);
				}
			}
		}
	}
}
