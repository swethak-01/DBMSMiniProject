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
@WebServlet("/PatientEditScreen")
public class PatientEditScreenServlet extends HttpServlet {
	private static final String query = "SELECT FirstName, LastName, DateOfBirth, Gender, Address, PhoneNumber, Email, EmergencyContact FROM HOSPITALMANAGEMENT.PATIENT where PatientID=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
     //get the id of record
        int PatientID=Integer.parseInt(req.getParameter("PatientID"));
     // LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
     // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///hospitalmanagement", "root", "root");
        	PreparedStatement ps = con.prepareStatement(query);){
        	ps.setInt(1, PatientID);
        	ResultSet rs=ps.executeQuery();
        	rs.next();
        	pw.println("<form action='patientediturl?PatientID=" + PatientID + "' method='post'>");
        	pw.println("<table>");

        	// First Column
        	pw.println("<tr>");
        	pw.println("<td>Patient First Name:</td>");
        	pw.println("<td><input type='text' name='patientFName' value='" + rs.getString(1) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Patient Last Name:</td>");
        	pw.println("<td><input type='text' name='patientLName' value='" + rs.getString(2) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Patient Date Of Birth:</td>");
        	pw.println("<td><input type='date' name='patientDOB' value='" + rs.getDate(3) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Patient Gender:</td>");
        	pw.println("<td>");
        	pw.println("<input type='radio' name='patientGender' value='Male' " + (rs.getString(4).equals("Male") ? "checked" : "") + "> Male");
        	pw.println("<input type='radio' name='patientGender' value='Female' " + (rs.getString(4).equals("Female") ? "checked" : "") + "> Female");
        	pw.println("</td>");
        	pw.println("</tr>");

        	// Second Column
        	pw.println("<tr>");
        	pw.println("<td>Patient Address:</td>");
        	pw.println("<td><input type='text' name='patientAddress' value='" + rs.getString(5) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Patient Phone Number:</td>");
        	pw.println("<td><input type='text' name='patientNumber' value='" + rs.getLong(6) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Patient Email:</td>");
        	pw.println("<td><input type='email' name='patientEmail' value='" + rs.getString(7) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Patient Emergency Contact:</td>");
        	pw.println("<td><input type='text' name='patientEContact' value='" + rs.getLong(8) + "'></td>");
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
        pw.println("<a href='AddPatient.html'>Add Patient</a>");
        pw.println("<a href='patientList'>Patient List</a>");
    }
        		@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
