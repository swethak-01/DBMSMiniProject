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


@jakarta.servlet.annotation.WebServlet("/patientediturl")
public class PatientEditServlet extends HttpServlet {
    private static final String query = "update HOSPITALMANAGEMENT.PATIENT set FirstName=?, LastName=?, DateOfBirth=?, Gender=?, Address=?, PhoneNumber=?, Email=?, EmergencyContact=?  where PatientID=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
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
        // get the id of the record
        int PatientID = Integer.parseInt(req.getParameter("PatientID"));

        // get the edited data we want to edit
        String patientFName = req.getParameter("patientFName");
        String patientLName = req.getParameter("patientLName");
        String patientDate = req.getParameter("patientDOB");
        Date patientDOB;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            patientDOB = dateFormat.parse(patientDate);
            // Now 'patientDOB' contains the parsed date
        } catch (ParseException e) {
            // Handle the parse exception, e.g., invalid date format
            pw.println("<h3 class='h3'>Error parsing date: " + e.getMessage() + "</h3>");
            e.printStackTrace(); // Log the exception details for debugging
            return;
        }

        String patientGender = req.getParameter("patientGender");

     // Check if the gender is not selected
     if (patientGender == null || patientGender.isEmpty()) {
         pw.println("<h3 class='h3'>Please select a gender.</h3>");
         return;
     }

        String patientAddress = req.getParameter("patientAddress");
        Long patientNumber = Long.parseLong(req.getParameter("patientNumber"));
        String patientEmail = req.getParameter("patientEmail");
        Long patientEContact = Long.parseLong(req.getParameter("patientEContact"));

        // LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///hospitalmanagement", "root", "root");
                PreparedStatement ps = con.prepareStatement(query);) {
            ps.setString(1, patientFName);
            ps.setString(2, patientLName);
            ps.setDate(3, new java.sql.Date(patientDOB.getTime()));
            ps.setString(4, patientGender);
            ps.setString(5, patientAddress);
            ps.setLong(6, patientNumber);
            ps.setString(7, patientEmail);
            ps.setLong(8, patientEContact);
            ps.setInt(9, PatientID);
            int count = ps.executeUpdate();
            if (count == 1) {
                pw.println("<h3 class='h3'>Record is edited successfully...</h3>");
            } else {
                pw.println("<h3 class='h3'>Record is not edited successfully...</h3>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h3>" + se.getMessage() + "</h3>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h3>" + e.getMessage() + "</h3>");
        }
        pw.println("</body>");
        pw.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
