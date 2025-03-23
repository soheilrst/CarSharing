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
	public List<reservierteFahrten> resList = new ArrayList<>();
	public List<Offenefahrten> ofFah = new ArrayList<>();

	public viewMaindb() throws SQLException {
		this.connection = DBUtil.getExternalConnection();

		if (this.connection == null || this.connection.isClosed() || !this.connection.isValid(5)) {
			throw new SQLException("Database connection is not available or has been closed.");
		}

		this.connection.setAutoCommit(false);
	}

	public void rfdb(int BenutzerID) throws SQLException {
		if (connection == null || connection.isClosed()) {
			throw new SQLException("Connection is closed before executing query.");
		}

		String Queryresfa = "SELECT fid, startort, zielort, status FROM reservieren r, Fahrt f, benutzer b "
				+ "WHERE r.kunde = ? AND r.kunde = b.bid AND f.fid = r.fahrt";
		String QueryOffe = "SELECT fid, startort, zielort, COALESCE(reser, 0) AS reser, fahrtkosten, MAXPLAETZE "
				+ "FROM fahrt f LEFT JOIN (SELECT fahrt, SUM(r.ANZPLAETZE) AS reser FROM reservieren r GROUP BY r.fahrt) a "
				+ "ON a.fahrt = f.fid";

		try (PreparedStatement pre1 = connection.prepareStatement(Queryresfa)) {
			pre1.setInt(1, BenutzerID);
			try (ResultSet of = pre1.executeQuery()) {
				while (of.next()) {
					resList.add(new reservierteFahrten(of.getString("startort"), of.getString("zielort"),
							of.getString("status"), of.getInt("fid")));
				}
			}
		}

		try (PreparedStatement pre2 = connection.prepareStatement(QueryOffe); ResultSet rs = pre2.executeQuery()) {
			while (rs.next()) {
				int maxPlaetze = rs.getInt("MAXPLAETZE");
				int reserviert = rs.getInt("reser");
				int frei = maxPlaetze - reserviert;

				if (frei > 0) {
					ofFah.add(new Offenefahrten(rs.getString("startort"), rs.getString("zielort"),
							rs.getInt("fahrtkosten"), frei, rs.getInt("fid")));
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
				if (!connection.isClosed()) {
					if (!connection.getAutoCommit()) {
						if (complete) {
							connection.commit();
						} else {
							connection.rollback();
						}
					}
					connection.close();
				}
			} catch (SQLException e) {
				throw new StoreException(e);
			}
		}
	}
}
