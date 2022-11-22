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

/**
 * Fahrtdetails:
 * hier wird die informationen von eine fahrt gezeigt und dazu hat die benutzer die möglichkeit
 * eine fahrt zu reservieren
 * eine fahrt zu bewerten 
 * eine fahrt zu löschen(benutzer==ersteller)
 **/
public class Fahrtdetailservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int fid1;
	private int anzahlfreieplaetze;

	/**
	* doGet enthält die Daten der selectiertem Fahrt und zwar 
	* Informationen und Rating dieses Farhrts.
	*/
	protected void doGet(HttpServletRequest req, HttpServletResponse res) {
			String fid0 = req.getParameter("fid");
			int fid = Integer.parseInt(fid0);
			setFid1(fid);
			FahrtDetail fahrtDetail = new FahrtDetail();
			fahrtDetail.detail(fid);
			req.setAttribute("FaherObj", FahrtDetail.fdselected);
			req.setAttribute("Rating", FahrtDetail.ratings);
			FahrtDetail fd = new FahrtDetail();
			req.setAttribute("durchschnitt", fd.durchschnitt);
			// Dispatch request to template engine
			try {
				req.getRequestDispatcher("/FahrtDetails.ftl").forward(req, res);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// Generate and show results of a search
		System.out.println("the first do post rating wurde aufgerufen");
		if (req.getParameter("fahrtTry").equals("ratings")) {
			try {
				int fid = getFid1();		
				// Dispatch results to template engine
				req.getSession().setAttribute("fahrtTry", getFid1() );
				req.getRequestDispatcher("/new_Rating.ftl").forward(req, res);
			} catch (Exception e1) {
				try {
					req.setAttribute("errormessage", "Database error: bitte kontktieren Sie die Administrator");
					req.getRequestDispatcher("/failRating.ftl").forward(req, res);
				} catch (Exception e) {
					req.setAttribute("errormessage", "System error: bitte kontktieren Sie die Administrator");
					e.printStackTrace();
				}
					e1.printStackTrace();
			}
		}	
		
		if (req.getParameter("fahrtTry").equals("Fahrtreservieren")) {	
			int fahrtTobereserved = getFid1();
			
			int benutzer = 6;		//hart kodierter Benutzer
			
			String anzahl0 = req.getParameter("anzahl");
			int anzahl = Integer.parseInt(anzahl0);
			
			
			//Sicherstellen, dass User nicht HTML verändert und die Maximale Anzahl an Plätze überschreitet
			if (anzahl > 3 || anzahl <1) {
				req.setAttribute("message", "Sie dürfen leider nicht mehr als 2 Plätze auf einmal reservieren oder garkeine Plätze");
				req.getRequestDispatcher("/failReservierung.ftl").forward(req, res);
			}
			
			// Check ob die Numer der Reservierung ist großer als der Anzahl freie Plätze
			try {
				reservierendb reservierendb = new reservierendb();
				if(reservierendb.reservierungCheck(benutzer,fahrtTobereserved)) {
					req.setAttribute("message", "Sie haben diese Fahrt schon reserviert !!!! ");
					req.getRequestDispatcher("/failReservierung.ftl").forward(req, res);
					
					
				}else {
					try {
					reservierendb.reservieren(benutzer, fahrtTobereserved, anzahl);
					req.setAttribute("message", "Die Reservierung ist Erfolgreich abgelaufen!");
					req.getRequestDispatcher("/reservierenConfirmation.ftl").forward(req, res);
					} catch (Exception e) {
						req.setAttribute("message", "Probleme bei der Reservierung !!!! ");
						req.getRequestDispatcher("/failReservierung.ftl").forward(req, res);
						e.printStackTrace();
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	}
		
		if (req.getParameter("fahrtTry").equals("FahrtLöschen")) {	
			int fahrtToBeDeleted = getFid1();
			int benutzer = 6;
			FahrtLöschen fl=new FahrtLöschen();
			if(fl.benutzerCheck( benutzer,fahrtToBeDeleted)==false) {
				req.setAttribute("message", "Sie dürfen nicht die Fahrt löschen. !");
				req.getRequestDispatcher("/failReservierung.ftl").forward(req, res);
				
				
			}else {
				try{fl.fahrtlöschen(fahrtToBeDeleted);
				req.setAttribute("message", "Die Fahrt wurde Erfolgreich gelöscht !");
				req.getRequestDispatcher("/reservierenConfirmation.ftl").forward(req, res);
				
			} catch (fahrtexception e) {
				e.printStackTrace();
			}
			
			}		
				
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





