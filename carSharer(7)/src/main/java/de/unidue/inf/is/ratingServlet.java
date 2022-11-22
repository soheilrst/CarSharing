package de.unidue.inf.is;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.stores.FahrtBewerten;

public class ratingServlet extends HttpServlet{
	private static final long serialVersionUID = 1L; 
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 req.getRequestDispatcher("/new_Rating.ftl").forward(req, res);
	}
	
	/*
	 * Post Request annehmen und Ein Rating mit aktuellem Datum 
	 * zur Klasse FahrtBewerten weiterleiten
	 */
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//Gibt das mit dem angegebenen Namen gebundene Objekt in dieser Sitzung zurück 
		int fid = (int) req.getSession().getAttribute("fahrtTry"); 
		String Bewertungtext = (String) req.getParameter("Bewertungtext");
		String Bewertungrate = req.getParameter("Bewertungrating");
		
		//Ein Actuelles Datum des Ratings erstellen
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss");
		LocalDateTime now = LocalDateTime.now();
		String erstellungsdatum = (dtf.format(now)).toString();
		
		int Bewertungsrate1 = 0;	// wir setzten das zuerst zu 0 und später parsen wir es
		int benutzer = 6;	//hart kodierte Benutzer-id 
		 try {
			 	Bewertungsrate1 = Integer.parseInt(Bewertungrate);
			 	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh.mm.ss");
	            Date parsedDate = dateFormat.parse(erstellungsdatum);
	            Timestamp erstellungsdatumnew = new java.sql.Timestamp(parsedDate.getTime());	        
	    		
	            
	            try (FahrtBewerten fb = new FahrtBewerten()) {
	            	//check ob der User die Fahrt bewertet hat
	    			boolean rated =fb.RatingCheck(benutzer, fid);
	    			//User hat die Fahrt schon bewertet
	    			if (rated) {
	    				req.setAttribute("message", "leider sie haben dieser Fahrt schon bewertet!! ");
						req.getRequestDispatcher("/failRating.ftl").forward(req, res);
	    			}
	    			//User hat die Fahrt nicht bewertet
	    			else {
	    				//Die Fahrt Bewerten
	    				fb.fahrtBewerten(Bewertungtext, erstellungsdatumnew, Bewertungsrate1,benutzer, fid);
	    				req.setAttribute("message", "Sie haben die Fahrt Erfolgreich bewertet!!");
						req.getRequestDispatcher("/ratingErfolg.ftl").forward(req, res);
	    			}
	    			
	    		
				 } catch(Exception e) {
					 e.printStackTrace();
				 }

		 } catch(Exception e) {
			 e.printStackTrace();
		 }
		 res.sendRedirect(req.getServletContext().getContextPath()+"/view_Main.ftl");
	}		 
}		
