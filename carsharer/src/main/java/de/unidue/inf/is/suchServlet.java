package de.unidue.inf.is;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.stores.suchFahrtendb;

public class suchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		String erstellungsdatum = (dtf.format(now)).toString();
		req.setAttribute("aktuell", erstellungsdatum);
		req.getRequestDispatcher("/view_Search.ftl").forward(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		try {
			String StartOrt = (String) req.getParameter("von");
			String zielOrt = (String) req.getParameter("bis");
			String fahrtdatumzeit = (String) req.getParameter("datum");
			String fahrtdatumzeit0 = fahrtdatumzeit + " 00:00:00";
			Timestamp fahrtdatumzeit1 = Timestamp.valueOf(fahrtdatumzeit0);
			if (StartOrt == "") {
				req.setAttribute("message", "Sie sollen den Startort eingeben!");
				req.getRequestDispatcher("/searchFail.ftl").forward(req, res);
			}
			if (zielOrt == "") {
				req.setAttribute("message", "Sie sollen den Zielort eingeben!");
				req.getRequestDispatcher("/searchFail.ftl").forward(req, res);
			}
			try (suchFahrtendb suchFahrtendb = new suchFahrtendb()) {
				suchFahrtendb.suchen(StartOrt, zielOrt, fahrtdatumzeit1);
				if (!suchFahrtendb.suchergebniss.isEmpty()) {
					try {
						req.setAttribute("suchergebniss", suchFahrtendb.suchergebniss);
						req.getRequestDispatcher("/searchErgebnis.ftl").forward(req, res);

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					if (suchFahrtendb.suchergebniss.isEmpty()) {
						req.setAttribute("message", "Leider es gibt keine Offene Fahrten für dieses Datum :( ");
						req.getRequestDispatcher("/nosearchfounded.ftl").forward(req, res);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}