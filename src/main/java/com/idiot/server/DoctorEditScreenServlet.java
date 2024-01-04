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
@jakarta.servlet.annotation.WebServlet("/DoctorEditScreen")
public class DoctorEditScreenServlet extends HttpServlet {
	private static final String query = "SELECT FirstName, LastName, DateOfBirth, Gender, Address, PhoneNumber, Email, Position, Specialization FROM HOSPITALMANAGEMENT.DOCTOR WHERE DoctorID=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
        //get the id of the record
        int DoctorID=Integer.parseInt(req.getParameter("DoctorID"));

     
     // LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
     // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///hospitalmanagement", "root", "root");
        	PreparedStatement ps = con.prepareStatement(query);){
        	ps.setInt(1, DoctorID);
        	ResultSet rs=ps.executeQuery();
        	rs.next();
        	pw.println("<form action='doctorediturl?'id="+DoctorID+"' method='post'>");
        	pw.println("<table>");
        	pw.println("<tr>");
        	pw.println("<td>Doctor First Name:<td>");
        	pw.println("<td><input type='text' name='doctorFName' value='"+rs.getString(1)+"'></td>");
        	pw.println("</tr>");
        	pw.println("<tr>");
        	pw.println("<td>Doctor Last Name:<td>");
        	pw.println("<td><input type='text' name='doctorLName' value='"+rs.getString(2)+"'></td>");
        	pw.println("</tr>");
        	pw.println("<tr>");
        	pw.println("<td>Doctor Date Of Birth:<td>");
        	pw.println("<td><input type='date' name='doctorDOB' value='"+rs.getDate(3)+"'></td>");
        	pw.println("</tr>");
        	pw.println("<tr>");
        	pw.println("<td>Doctor Gender:</td>");
        	pw.println("<td>");
        	pw.println("<input type='radio' name='maleGender' value='on'" + (rs.getString(4).equals("M") ? " checked" : "") + "> Male");
        	pw.println("<input type='radio' name='femaleGender' value='on'" + (rs.getString(4).equals("F") ? " checked" : "") + "> Female");
        	pw.println("</td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Doctor Address:<td>");
        	pw.println("<td><input type='text' name='doctorAddress' value='"+rs.getString(5)+"'></td>");
        	pw.println("</tr>");
        	pw.println("<tr>");
        	pw.println("<td>Doctor Phone Number:<td>");
        	pw.println("<td><input type='text' name='doctorNumber' value='"+rs.getString(6)+"'></td>");
        	pw.println("</tr>");
        	pw.println("<tr>");
        	pw.println("<td>Doctor Email:<td>");
        	pw.println("<td><input type='email' name='doctorEmail' value='"+rs.getString(7)+"'></td>");
        	pw.println("</tr>");
        	pw.println("<tr>");
        	pw.println("<td>Doctor Position:<td>");
        	pw.println("<td><input type='text' name='doctorPosition' value='"+rs.getString(8)+"'></td>");
        	pw.println("</tr>");
        	pw.println("<tr>");
        	pw.println("<td>Doctor Specialization:<td>");
        	pw.println("<td><input type='text' name='doctorSpecialization' value='"+rs.getString(9)+"'></td>");
        	pw.println("</tr>");
        	pw.println("<tr>");

        	pw.println("<td><input type='submit' value='Edit'</td>");
        	pw.println("<td><input type='reset' value='Reset'</td>");
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
        pw.println("<a href='AddDoctor.html'>Add Doctor</a>");
        
    }
        		@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}