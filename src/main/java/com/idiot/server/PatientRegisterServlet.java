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

@jakarta.servlet.annotation.WebServlet("/PatientRegister")
public class PatientRegisterServlet extends HttpServlet {
    private static final String query = "INSERT INTO PATIENT(FirstName, LastName, DateOfBirth, Gender, Address, PhoneNumber, Email, EmergencyContact) VALUES(?,?,?,?,?,?,?,?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
     // GET THE patient info
        String patientFName = req.getParameter("patientFName");
        String patientLName = req.getParameter("patientLName");
        String patientDate = req.getParameter("patientDOB");
        Date patientDOB = null;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            patientDOB = dateFormat.parse(patientDate);
            // Now 'patientDOB' contains the parsed date
        } catch (ParseException e) {
            // Handle the parse exception, e.g., invalid date format
            pw.println("<h2>Invalid date format. Please use yyyy-MM-dd.</h2>");
            return;
        }

        String maleGender = req.getParameter("maleGender");
        String femaleGender = req.getParameter("femaleGender");

        // Check which radio button is selected
        String patientGender;
        if ("on".equals(maleGender)) {
            patientGender = "Male";  // Male is selected
        } else if ("on".equals(femaleGender)) {
            patientGender = "Female";  // Female is selected
        } else {
            pw.println("<h2>Please select a gender.</h2>");
            return;
        }
        // Rest of your code remains unchanged

        // Print the selected gender in the response
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
        	PreparedStatement ps = con.prepareStatement(query);){
        	ps.setString(1, patientFName);
            ps.setString(2, patientLName);
            ps.setDate(3, new java.sql.Date(patientDOB.getTime())); // Convert to java.sql.Date
            ps.setString(4, patientGender);
            ps.setString(5, patientAddress);
            ps.setLong(6, patientNumber);
            ps.setString(7, patientEmail);
            ps.setLong(8, patientEContact);
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
        pw.println("<a href='AddPatient.html'>Add Patient</a>");
        pw.println("<a href='patientList'>Patient List</a>");
    }
        		@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}