package de.unidue.inf.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.utils.DBUtil;

/*
 * A servlet is a small Java program that runs within a Web server. Servlets receive and respond to requests from Web clients,
 	usually across HTTP, the HyperText Transfer Protocol. 
 */

/**
 * Das könnte die Eintrittsseite sein.
 */
public final class CarSharerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
  
        boolean databaseExists = DBUtil.checkDatabaseExistsExternal();

        if (databaseExists) {
            request.setAttribute("db2exists", "vorhanden! Supi!");
        }
        else {
            request.setAttribute("db2exists", "nicht vorhanden :-(");
        }

        request.getRequestDispatcher("carSharer_start.ftl").forward(request, response);
    }

}