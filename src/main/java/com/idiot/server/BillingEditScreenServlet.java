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
@WebServlet("/BillingEditScreen")
public class BillingEditScreenServlet extends HttpServlet {
	private static final String query = "SELECT PatientID,DoctorID,PrescriptionID,TotalAmount, PaymentStatus, BillingDate FROM HOSPITALMANAGEMENT.BILLING where BillID=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
     //get the id of record
        int BillID=Integer.parseInt(req.getParameter("BillID"));
     // LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
     // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///hospitalmanagement", "root", "root");
        	PreparedStatement ps = con.prepareStatement(query);){
        	ps.setInt(1, BillID);
        	ResultSet rs=ps.executeQuery();
        	rs.next();
        	pw.println("<form action='billingediturl?BillID=" + BillID + "' method='post'>");
        	pw.println("<table>");

        	// First Column
        	pw.println("<tr>");
        	pw.println("<td>Patient ID:</td>");
        	pw.println("<td><input type='text' name='PatientID' value='" + rs.getInt(1) + "'></td>");
        	pw.println("</tr>");
        	
        	pw.println("<tr>");
        	pw.println("<td>Doctor ID:</td>");
        	pw.println("<td><input type='text' name='DoctorID' value='" + rs.getInt(2) + "'></td>");
        	pw.println("</tr>");
        	
        	pw.println("<tr>");
        	pw.println("<td>Prescription ID:</td>");
        	pw.println("<td><input type='text' name='PrescriptionID' value='" + rs.getInt(3) + "'></td>");
        	pw.println("</tr>");

        	
        	pw.println("<tr>");
        	pw.println("<td>Total Amount:</td>");
        	pw.println("<td><input type='float' name='TotalAmount' value='" + rs.getFloat(4) + "'></td>");
        	pw.println("</tr>");

        	

        	// Second Column
        	pw.println("<tr>");
        	pw.println("<td>Payment Status:</td>");
        	pw.println("<td><input type='text' name='PaymentStatus' value='" + rs.getString(5) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Billing Date:</td>");
        	pw.println("<td><input type='date' name='BillingDate' value='" + rs.getDate(6) + "'></td>");
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
        pw.println("<a href='AddBilling.html'>Add Billing</a>");
        pw.println("<a href='billingList'>Billing List</a>");
    }
        		@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
