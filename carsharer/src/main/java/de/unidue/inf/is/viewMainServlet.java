package de.unidue.inf.is;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.stores.viewMaindb;

@WebServlet("/viewMainServlet")
public class viewMainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public viewMainServlet() {
		super();
	}

	@Override
	//
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			try (viewMaindb reservierteFahrtendb = new viewMaindb()) {
				int BenutzerID = 5; // hartkodierter Benutzer
				reservierteFahrtendb.rfdb(BenutzerID);
				try {

					req.setAttribute("reservierteFahrten", reservierteFahrtendb.resList);
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {

					req.setAttribute("offeneFahrten", reservierteFahrtendb.ofFah);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		req.getRequestDispatcher("/view_Main.ftl").forward(req, res);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("/view_Main.ftl").forward(req, res);
		doGet(req, res);
	}

}
