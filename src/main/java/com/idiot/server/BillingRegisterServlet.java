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
    private static final String query = "INSERT INTO BILLING(PatientID, DoctorID, PrescriptionID,  TotalAmount, PaymentStatus, BillingDate) VALUES(?,?,?,?,?,?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
     // GET THE patient info
        pw.println("<html>");
    	pw.println("<head>");
    	pw.println("<link rel='stylesheet' type='text/css' href='styles.css'>");
    	pw.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
    	pw.println("<link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">");
    	pw.println("<meta charset=\"ISO-8859-1\">");
    	pw.println("<title>Result</title>");
    	pw.println("</head>");
    	pw.println("<body>");
    	pw.println("<header>\r\n"
    			+ "    <span class=\"h1\">GLOBAL HOSPITALS...</span>\r\n"
    			+ "  <a class='tag' href='Admin.html'>ADMIN</a></header><br>");
    	pw.println("<center>\r\n"
    	        + "    <h3 style=\"font-family: 'Courier New', monospace; font-weight: bold; font-size: 25px;\">RESULT</h3>\r\n"
    	        + "  </center>");
    	pw.println("<table class=\"w3-table w3-bordered w3-border w3-centered\" style=\"width:16.5%\">");
        pw.println("<tr class=\"w3-hover\">");
        pw.println("<td><a class='tag1' href='patientList'>Patients</a></td>");
        pw.println("</tr>");
        pw.println("<tr class=\"w3-hover\">");
        pw.println("<td><a class='tag1' href='doctorList'>Doctors</a></td>");
        pw.println("</tr>");
        pw.println("<tr class=\"w3-hover\">");
        pw.println("<td><a class='tag1' href='prescriptionList'>Prescriptions</a></td>");
        pw.println("</tr>");
        pw.println("<tr class=\"w3-hover\">");
        pw.println("<td><a class='tag1' href='appointmentList'>Appointments</a></td>");
        pw.println("</tr>");
        pw.println("<tr class=\"w3-hover\">");
        pw.println("<td><a class='tag1' href='billingList'>Bills</a></td>");
        pw.println("</tr>");
        pw.println("</table>");

        // get the edited data we want to edit
        int PatientID = Integer.parseInt(req.getParameter("PatientID"));
        int DoctorID = Integer.parseInt(req.getParameter("DoctorID"));
        int PrescriptionID = Integer.parseInt(req.getParameter("PrescriptionID"));
        

       float TotalAmount=Float.parseFloat(req.getParameter("TotalAmount"));
       

       String PaymentStatus = req.getParameter("PaymentStatus");


     // Check if the gender is not selected
     

       String BillingD = req.getParameter("BillingDate");
       Date BillingDate = null;

       try {
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           BillingDate = dateFormat.parse(BillingD);
           // Now 'patientDOB' contains the parsed date
       } catch (ParseException e) {
           // Handle the parse exception, e.g., invalid date format
           pw.println("<h2>Invalid date format. Please use yyyy-MM-dd.</h2>");
           return;
       }

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
        	 ps.setInt(2, DoctorID);
        	 ps.setInt(3, PrescriptionID);
             ps.setFloat(4, TotalAmount);
             ps.setString(5, PaymentStatus);
             ps.setDate(6, new java.sql.Date(BillingDate.getTime()));
            int count = ps.executeUpdate();
            if (count == 1) {
            	 pw.println("<h3 class='h3'>Record is Registered Successfully...</h3>");
            } else {
                pw.println("<h3 class='h3'>Record is not Registered Successfully...</h3>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h3>"+se.getMessage()+"</h3>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h3>"+e.getMessage()+"</h3>");
        }
        pw.println("</body>");
        pw.println("</html>");
    }
        		@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}