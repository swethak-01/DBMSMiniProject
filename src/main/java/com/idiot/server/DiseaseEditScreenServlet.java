package com.idiot.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/DiseaseEditScreen")
public class DiseaseEditScreenServlet extends HttpServlet {
	private static final String query = "SELECT DiseaseName, Vaccination, Symptoms, Treatment FROM HOSPITALMANAGEMENT.DISEASE where DiseaseID=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
     //get the id of record
        int DiseaseID=Integer.parseInt(req.getParameter("DiseaseID"));
     // LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
     // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///hospitalmanagement", "root", "root");
        	PreparedStatement ps = con.prepareStatement(query);){
        	ps.setInt(1, DiseaseID);
        	ResultSet rs=ps.executeQuery();
        	rs.next();
        	pw.println("<form action='diseaseediturl?DiseaseID=" + DiseaseID + "' method='post'>");
        	pw.println("<table>");

        	// First Column
        	pw.println("<tr>");
        	pw.println("<td>Disease Name:</td>");
        	pw.println("<td><input type='text' name='diseaseName' value='" + rs.getString(1) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Vaccination:</td>");
        	pw.println("<td><input type='text' name='vaccination' value='" + rs.getString(2) + "'></td>");
        	pw.println("</tr>");

        	// Second Column
        	pw.println("<tr>");
        	pw.println("<td>Symptoms:</td>");
        	pw.println("<td><input type='text' name='symptoms' value='" + rs.getString(3) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Treatment:</td>");
        	pw.println("<td><input type='text' name='treatment' value='" + rs.getString(4) + "'></td>");
        	pw.println("</tr>");


        	// Buttons
        	pw.println("<tr>");
        	pw.println("<td><input type='submit' value='Edit'></td>");
        	pw.println("<td><input type='reset' value='Reset'></td>");
        	pw.println("</tr>");

        	pw.println("</table>");
        	pw.println("</form>");

        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>"+se.getMessage()+"</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>"+e.getMessage()+"</h2>");
        }
        pw.println("<a href='AddDisease.html'>Add Disease</a>");
        pw.println("<a href='diseaseList'>Disease List</a>");
    }
        		@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
