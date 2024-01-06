package com.idiot.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@jakarta.servlet.annotation.WebServlet("/BillingRegister")
public class BillingRegisterServlet extends HttpServlet {
    private static final String query = "INSERT INTO BILLING(PatientID, BillDate, TotalAmount, PaymentStatus, DiseaseID) VALUES(?,?,?,?,?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
     // GET THE patient info
        int patientID = Integer.parseInt(req.getParameter("patientID"));
        String BillD = req.getParameter("BillDate");
        Date BillDate = null;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            BillDate = dateFormat.parse(BillD);
            // Now 'patientDOB' contains the parsed date
        } catch (ParseException e) {
            // Handle the parse exception, e.g., invalid date format
            pw.println("<h2>Invalid date format. Please use yyyy-MM-dd.</h2>");
            return;
        }

       float TotalAmount=Float.parseFloat(req.getParameter("TotalAmount"));
       

        String PaymentStatus = req.getParameter("PaymentStatus");

     // Check if the gender is not selected
     

        int DiseaseID = Integer.parseInt(req.getParameter("DiseaseID"));
     // LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
     // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///hospitalmanagement", "root", "root");
        	PreparedStatement ps = con.prepareStatement(query);){
        	  ps.setInt(1, patientID);
              ps.setDate(2, new java.sql.Date(BillDate.getTime()));
              ps.setFloat(3, TotalAmount);
              ps.setString(4, PaymentStatus);
              ps.setInt(5, DiseaseID);
            int count = ps.executeUpdate();
            if (count == 1) {
                pw.println("<h2>Record is Registered Successfully...</h2>");
            } else {
                pw.println("<h2>Record is not Registered Successfully...</h2>");
            }
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