package de.unidue.inf.is;

import java.io.IOException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import de.unidue.inf.is.application.CarApplication;
import de.unidue.inf.is.stores.Fahrerstellen;

public class fahrtersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.getRequestDispatcher("/newDrive.ftl").forward(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String von = (String) req.getParameter("von");
		String bis = (String) req.getParameter("bis");
		String datum = req.getParameter("datum");
		String time = req.getParameter("time");
		String maxKap = (String) req.getParameter("maxkap");
		String fahrtkosten = (String) req.getParameter("kosten");
		String transportmittel = (String) req.getParameter("Transportmittel");
		String beschreibung = (String) req.getParameter("beschreibung");
		String fahrdatumzeit = datum + " " + time + ":00";
		int anbieter = 3;// hart Benutzer ID
		int maxKap1 = 0;
		int fahrtKosten1 = 0;
		int transportmittel1 = 0;
		Timestamp fahrdatumzeit1 = Timestamp.valueOf(fahrdatumzeit);

		Date date = new Date();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		final SimpleDateFormat ti = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp actuell = Timestamp.valueOf(ti.format(timestamp));

		int a = actuell.compareTo(fahrdatumzeit1);
		if (a > 0) {
			req.setAttribute("message", "Das Datum ist ungültig und abgelaufen");
			req.getRequestDispatcher("/failNewFahrt.ftl").forward(req, res);
		}

		else {

			try {
				transportmittel1 = Integer.parseInt(transportmittel);
				maxKap1 = Integer.parseInt(maxKap);
				fahrtKosten1 = Integer.parseInt(fahrtkosten);

				if (beschreibung.length() > 100) {
					req.setAttribute("message",
							"Die Länge der Beschreibung soll nicht über 100 Characters überschreiten");
					req.getRequestDispatcher("/failNewFahrt.ftl").forward(req, res);
				}

				if (fahrtKosten1 < 0) {
					req.setAttribute("message", "Die Fahrtkosten muss Positiv sein");
					req.getRequestDispatcher("/failNewFahrt.ftl").forward(req, res);
				}

				if (maxKap1 > 10) {
					req.setAttribute("message",
							"Fahrt Erstellung Fehlgeschlagen! Die Maximale Plätze dürfen nicht mehr als 10 sein.  ");
					req.getRequestDispatcher("/failNewFahrt.ftl").forward(req, res);
				}

				if (CarApplication.isNumeric(maxKap) && CarApplication.isNumeric(fahrtkosten)) {

					try {
						try (Fahrerstellen fahrerstellen = new Fahrerstellen()) {

							fahrerstellen.fahrterstellen(von, bis, fahrdatumzeit1, maxKap1, fahrtKosten1, anbieter,
									transportmittel1, beschreibung);
						}
						req.setAttribute("message", "Die Fahrt wurde erfolgreich erstellt !! ");
						req.getRequestDispatcher("/erfolgfahrt.ftl").forward(req, res);

					} catch (Exception e) {
						e.printStackTrace();
						req.setAttribute("message",
								"Fahrt Erstellung Fehlgeschlagen! Fehler in Database oder die Werte sind ungültig");
						req.getRequestDispatcher("/failNewFahrt.ftl").forward(req, res);
					}
				} else {
					req.setAttribute("message", "Die Werte sind nicht numerisch oder nicht positiv ");
					req.getRequestDispatcher("/failNewFahrt.ftl").forward(req, res);
				}

			} catch (Exception e) {
				req.setAttribute("message", "Fehler Ihre Eingaben sind ungültig");
				req.getRequestDispatcher("/failNewFahrt.ftl").forward(req, res);
				e.printStackTrace();
			}
		}

	}

}
