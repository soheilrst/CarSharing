package de.unidue.inf.is;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.stores.viewMaindb;

/*
 * Ein Servlet ist ein kleines Java-Programm, das auf einem Webserver ausgeführt wird. 
 * Servlets empfangen und beantworten Anfragen von Webclients, normalerweise über HTTP (das Hypertext Transfer Protocol).
 * HttpServlet : Stellt eine abstrakte Klasse bereit, die abgeleitet werden soll, um ein für eine Website geeignetes HTTP-Servlet zu erstellen.
 * */
public class viewMainServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public viewMainServlet() {
		super();
	}
	
	/*
	 * Um GET request zu behandeln wird Doget Methode gebraucht
	 *um die reservierten und offene fahrten darzustellen
	 */
	
	/* HttpServletRequestum: um Anforderungsinformationen für HTTP-Servlets bereitzustellen.
	 * HttpServletResponse: um ein Servlet beim Senden einer Antwort an den Client zu unterstützen
	 * */
	
	@Override
	//
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			try (viewMaindb reservierteFahrtendb = new viewMaindb()) {
				int BenutzerID = 6;  //fixierter Benutzer 
				reservierteFahrtendb.rfdb(BenutzerID);
				try {
					//Die Liste der reservierten Fahrten wird in View_Main.ftl festgesetzt
					req.setAttribute("reservierteFahrten", reservierteFahrtendb.resList);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					//Die Liste der offenen Fahrten wird in View_Main.ftl festgesetzt
					req.setAttribute("offeneFahrten", reservierteFahrtendb.ofFah);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		req.getRequestDispatcher("/view_Main.ftl").forward(req, res);
	
	}
	
	//private int counter = reservierteFahrtendb.resList.size()-1;
	
	/*
	 * To intercept on HTTP POST requests 
	 * um die fehlermeldung bei dopost zu vermeiden ,definieren wir einfach dopost selber
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("/view_Main.ftl").forward(req, res);
		doGet(req,res);
	}

}
