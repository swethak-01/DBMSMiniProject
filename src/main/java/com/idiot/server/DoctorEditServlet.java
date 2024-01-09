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


@jakarta.servlet.annotation.WebServlet("/doctorediturl")
public class DoctorEditServlet extends HttpServlet {
    private static final String query = "update HOSPITALMANAGEMENT.DOCTOR set FirstName=?, LastName=?, DateOfBirth=?, Gender=?, Address=?, PhoneNumber=?, Email=?, Position=?, Specialization=?  where DoctorID=?";

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
    			+ "  <a class='tag' href='AdminList'>ADMIN</a></header><br>");
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
     

        int DoctorID = Integer.parseInt(req.getParameter("DoctorID"));
     
        // get the edited data we want to edit
        String doctorFName = req.getParameter("doctorFName");
        String doctorLName = req.getParameter("doctorLName");
        String doctorDate = req.getParameter("doctorDOB");
        Date doctorDOB;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            doctorDOB = dateFormat.parse(doctorDate);
            // Now 'patientDOB' contains the parsed date
        } catch (ParseException e) {
            // Handle the parse exception, e.g., invalid date format
            pw.println("<h3 class='h3'>Error parsing date: " + e.getMessage() + "</h3>");
            e.printStackTrace(); // Log the exception details for debugging
            return;
        }

        String doctorGender = req.getParameter("doctorGender");

        
        String doctorAddress = req.getParameter("doctorAddress");
        Long doctorNumber = Long.parseLong(req.getParameter("doctorNumber"));
        String doctorEmail = req.getParameter("doctorEmail");
        String doctorPosition = req.getParameter("doctorPosition");
        String doctorSpecialization = req.getParameter("doctorSpecialization");

        // LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///hospitalmanagement", "root", "root");
                PreparedStatement ps = con.prepareStatement(query);) {
            ps.setString(1, doctorFName);
            ps.setString(2, doctorLName);
            ps.setDate(3, new java.sql.Date(doctorDOB.getTime()));
            ps.setString(4, doctorGender);
            ps.setString(5,doctorAddress);
            ps.setLong(6, doctorNumber);
            ps.setString(7, doctorEmail);
            ps.setString(8, doctorPosition);
            ps.setString(9, doctorSpecialization);
            ps.setInt(10, DoctorID);
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
