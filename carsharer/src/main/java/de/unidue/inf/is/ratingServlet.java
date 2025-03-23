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
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		int fid = (int) req.getSession().getAttribute("fahrtTry"); 
		String Bewertungtext = (String) req.getParameter("Bewertungtext");
		String Bewertungrate = req.getParameter("Bewertungrating");
		
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss");
		LocalDateTime now = LocalDateTime.now();
		String erstellungsdatum = (dtf.format(now)).toString();
		
		int Bewertungsrate1 = 0;	
		int benutzer = 6;	
		 try {
			 	Bewertungsrate1 = Integer.parseInt(Bewertungrate);
			 	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh.mm.ss");
	            Date parsedDate = dateFormat.parse(erstellungsdatum);
	            Timestamp erstellungsdatumnew = new java.sql.Timestamp(parsedDate.getTime());	        
	    		
	            
	            try (FahrtBewerten fb = new FahrtBewerten()) {
	            	//checktbob der User die Fahrt bewertet hat
	    			boolean rated =fb.RatingCheck(benutzer, fid);
	    			
	    			if (rated) {
	    				req.setAttribute("message", "Sie haben dieser Fahrt schon bewertet!! ");
						req.getRequestDispatcher("/failRating.ftl").forward(req, res);
	    			}
	    			//User hat die Fahrt nicht bewertet
	    			else {
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
