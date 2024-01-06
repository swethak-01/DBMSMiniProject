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
@WebServlet("/AppointmentEditScreen")
public class AppointmentEditScreenServlet extends HttpServlet {
	private static final String query = "SELECT PatientID, DoctorID, Date, Time, Status FROM HOSPITALMANAGEMENT.APPOINTMENT where AppointmentID=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
     //get the id of record
        int AppointmentID=Integer.parseInt(req.getParameter("AppointmentID"));
     // LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
     // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///hospitalmanagement", "root", "root");
        	PreparedStatement ps = con.prepareStatement(query);){
        	ps.setInt(1, AppointmentID);
        	ResultSet rs=ps.executeQuery();
        	rs.next();
        	pw.println("<form action='appointmentediturl?AppointmentID=" + AppointmentID + "' method='post'>");
        	pw.println("<table>");

        	// First Column
        	pw.println("<tr>");
        	pw.println("<td>Patient ID:</td>");
        	pw.println("<td><input type='text' name='PatientID' value='" + rs.getString(1) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Doctor ID:</td>");
        	pw.println("<td><input type='text' name='DoctorID' value='" + rs.getString(2) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Date Of Appointment:</td>");
        	pw.println("<td><input type='date' name='appointmentDate' value='" + rs.getDate(3) + "'></td>");
        	pw.println("</tr>");

        	
        	// Second Column
        	pw.println("<tr>");
        	pw.println("<td>Time Of Appointment:</td>");
        	pw.println("<td><input type='time' name='appointmentTime' value='" + rs.getTime(4) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Status Of Appointment:</td>");
        	pw.println("<td><input type='text' name='appointmentStatus' value='" + rs.getString(5) + "'></td>");
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
        pw.println("<a href='AddAppointment.html'>Add Appointment</a>");
        pw.println("<a href='appointmentList'>Appointment List</a>");
    }
        		@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
