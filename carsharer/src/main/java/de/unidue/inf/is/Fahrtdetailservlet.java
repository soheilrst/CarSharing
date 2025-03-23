package de.unidue.inf.is;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.fahrtexception;
import de.unidue.inf.is.stores.FahrtDetail;
import de.unidue.inf.is.stores.FahrtLöschen;
import de.unidue.inf.is.stores.reservierendb;

public class Fahrtdetailservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int fid1;
	private int anzahlfreieplaetze;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) {
		try {
			int fid = Integer.parseInt(req.getParameter("fid"));
			setFid1(fid);

			FahrtDetail fahrtDetail = new FahrtDetail();
			fahrtDetail.detail(fid);

			req.setAttribute("FaherObj", FahrtDetail.fdselected);
			req.setAttribute("Rating", FahrtDetail.ratings);
			req.setAttribute("durchschnitt", new FahrtDetail().durchschnitt);

			req.getRequestDispatcher("/FahrtDetails.ftl").forward(req, res);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("fahrtTry");
		int fahrtId = getFid1();

		switch (action) {
		case "ratings":
			handleRatingRedirect(req, res, fahrtId);
			break;

		case "Fahrtreservieren":
			handleReservation(req, res, fahrtId);
			break;

		case "FahrtLöschen":
			handleFahrtLöschen(req, res, fahrtId);
			break;

		default:
			req.setAttribute("message", "Unbekannte Aktion.");
			req.getRequestDispatcher("/failReservierung.ftl").forward(req, res);
			break;
		}
	}

	private void handleRatingRedirect(HttpServletRequest req, HttpServletResponse res, int fid)
			throws ServletException, IOException {
		try {
			req.getSession().setAttribute("fahrtTry", fid);
			req.getRequestDispatcher("/new_Rating.ftl").forward(req, res);
		} catch (Exception e) {
			req.setAttribute("errormessage", "Fehler beim Weiterleiten zur Bewertung.");
			req.getRequestDispatcher("/failRating.ftl").forward(req, res);
		}
	}

	private void handleReservation(HttpServletRequest req, HttpServletResponse res, int fahrtId)
			throws ServletException, IOException {
		int benutzer = 5; // hartkodierter user
		int anzahl;

		try {
			anzahl = Integer.parseInt(req.getParameter("anzahl"));
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Ungültige Anzahl.");
			req.getRequestDispatcher("/failReservierung.ftl").forward(req, res);
			return;
		}

		if (anzahl > 3 || anzahl < 1) {
			req.setAttribute("message",
					"Die Reservierung von mehr als 2 Plätzen oder weniger als 1 Platz ist nicht erlaubt.");
			req.getRequestDispatcher("/failReservierung.ftl").forward(req, res);
			return;
		}

		try (reservierendb reservierendb = new reservierendb()) {
			if (reservierendb.reservierungCheck(benutzer, fahrtId)) {
				req.setAttribute("message", "Sie haben diese Fahrt bereits reserviert.");
				req.getRequestDispatcher("/failReservierung.ftl").forward(req, res);
				return;
			}

			reservierendb.reservieren(benutzer, fahrtId, anzahl);
			reservierendb.complete();
			req.setAttribute("message", "Die Reservierung war erfolgreich!");
			req.getRequestDispatcher("/reservierenConfirmation.ftl").forward(req, res);

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("message", "Es gab ein Problem bei der Reservierung.");
			req.getRequestDispatcher("/failReservierung.ftl").forward(req, res);
		}
	}

	private void handleFahrtLöschen(HttpServletRequest req, HttpServletResponse res, int fahrtId)
			throws ServletException, IOException {
		int benutzer = 5; // hartkodierter user

		FahrtLöschen fl = new FahrtLöschen();

		try {
			if (!fl.benutzerCheck(benutzer, fahrtId)) {
				req.setAttribute("message", "Sie dürfen diese Fahrt nicht löschen.");
				req.getRequestDispatcher("/failReservierung.ftl").forward(req, res);
				return;
			}

			fl.fahrtlöschen(fahrtId);
			req.setAttribute("message", "Die Fahrt wurde erfolgreich gelöscht.");
			req.getRequestDispatcher("/reservierenConfirmation.ftl").forward(req, res);

		} catch (fahrtexception e) {
			e.printStackTrace();
			req.setAttribute("message", "Fehler beim Löschen der Fahrt.");
			req.getRequestDispatcher("/failReservierung.ftl").forward(req, res);
		}
	}

	public int getAnzahlfreieplaetze() {
		return anzahlfreieplaetze;
	}

	public void setAnzahlfreieplaetze(int anzahlfreieplaetze) {
		this.anzahlfreieplaetze = anzahlfreieplaetze;
	}

	public int getFid1() {
		return fid1;
	}

	public void setFid1(int fid1) {
		this.fid1 = fid1;
	}
}
