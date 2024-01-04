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
@jakarta.servlet.annotation.WebServlet("/doctorList")
public class DoctorListServlet extends HttpServlet {
	private static final String query = "SELECT DoctorID,FirstName, LastName, DateOfBirth, Gender, Address, PhoneNumber, Email, Position, Specialization FROM HOSPITALMANAGEMENT.DOCTOR";

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
            pw.println("<th>Doctor ID</th>");
            pw.println("<th>First Name</th>");
            pw.println("<th>Last Name</th>");
            pw.println("<th>Date Of Birth</th>");
            pw.println("<th>Gender</th>");
            pw.println("<th>Address</th>");
            pw.println("<th>Phone Number</th>");
            pw.println("<th>Email</th>");
            pw.println("<th>Position</th>");
            pw.println("<th>Specialization</th>");
            pw.println("<th>Edit</th>");
            pw.println("<th>Delete</th>");
            pw.println("</tr>");

        	while(rs.next()) {
        		pw.println("<tr>");
                pw.println("<td>" + rs.getInt(1) + "</td>");
                pw.println("<td>" + rs.getString(2) + "</td>");
                pw.println("<td>" + rs.getString(3) + "</td>");
                pw.println("<td>" + rs.getDate(4) +"</td>");
                pw.println("<td>" + rs.getString(5) + "</td>");
                pw.println("<td>" + rs.getString(6) + "</td>");
                pw.println("<td>" + rs.getLong(7) + "</td>");
                pw.println("<td>" + rs.getString(8) + "</td>");
                pw.println("<td>" + rs.getString(9) + "</td>");
                pw.println("<td>" + rs.getString(10) + "</td>");
                pw.println("<td><a href='PatientEditScreen?id="+rs.getInt(1)+"'>Edit</a></td>");
                pw.println("<td><a href='deleteurl?id="+rs.getInt(1)+"'>Delete</a></td>");
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
        pw.println("<a href='AddDoctor.html'>Add Doctor</a>");
        
    }
        		@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}