package com.idiot.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@jakarta.servlet.annotation.WebServlet("/appointmentList")
public class AppointmentListServlet extends HttpServlet {
	private static final String query = "SELECT AppointmentID,PatientID,DoctorID, Date, Time, Status FROM HOSPITALMANAGEMENT.APPOINTMENT";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
     
     // LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
     // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///hospitalmanagement", "root", "root");
        	PreparedStatement ps = con.prepareStatement(query);){
        	ResultSet rs=ps.executeQuery();
        	pw.println("<table border='1' align='center'>");
            pw.println("<tr>");
            pw.println("<th>Appointment ID</th>");
            pw.println("<th>Patient ID</th>");
            pw.println("<th>DoctorID</th>");
            pw.println("<th>Date</th>");
            pw.println("<th>Time</th>");
            pw.println("<th>Status</th>");
            pw.println("<th>Edit</th>");
            pw.println("<th>Delete</th>");
            pw.println("</tr>");

        	while(rs.next()) {
        		pw.println("<tr>");
                pw.println("<td>" + rs.getInt(1) + "</td>");
                pw.println("<td>" + rs.getString(2) + "</td>");
                pw.println("<td>" + rs.getString(3) + "</td>");
                pw.println("<td>" + rs.getDate(4) +"</td>");
                pw.println("<td>" + rs.getTime(5) + "</td>");
                pw.println("<td>" + rs.getString(6) + "</td>");
             
                pw.println("<td><a href='AppointmentEditScreen?AppointmentID="+rs.getInt("AppointmentID")+"'>Edit</a></td>");
                pw.println("<td><a href='appointmentdeleteurl?AppointmentID="+rs.getInt(1)+"'>Delete</a></td>");
                pw.println("</tr>");

        	}
        	pw.println("</table>");
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>"+se.getMessage()+"</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>"+e.getMessage()+"</h2>");
        }
        pw.println("<a href='AddAppointment.html'>Add Appointment</a>");
        
    }
        		@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
