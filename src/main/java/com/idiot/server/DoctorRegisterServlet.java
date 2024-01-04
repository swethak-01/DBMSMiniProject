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

@jakarta.servlet.annotation.WebServlet("/DoctorRegister")
public class DoctorRegisterServlet extends HttpServlet {
    private static final String query = "INSERT INTO DOCTOR(FirstName, LastName, DateOfBirth, Gender, Address, PhoneNumber, Email, Position, Specialization) VALUES(?,?,?,?,?,?,?,?,?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
     // GET THE patient info
        String doctorFName = req.getParameter("doctorFName");
        String doctorLName = req.getParameter("doctorLName");
        String doctorDate = req.getParameter("doctorDOB");
        Date doctorDOB = null;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            doctorDOB = dateFormat.parse(doctorDate);
            // Now 'patientDOB' contains the parsed date
        } catch (ParseException e) {
            // Handle the parse exception, e.g., invalid date format
            pw.println("<h2>Invalid date format. Please use yyyy-MM-dd.</h2>");
            return;
        }

        String maleGender = req.getParameter("maleGender");
        String femaleGender = req.getParameter("femaleGender");

        // Check which radio button is selected
        String doctorGender;
        if ("on".equals(maleGender)) {
        	doctorGender = "Male";  // Male is selected
        } else if ("on".equals(femaleGender)) {
        	doctorGender = "Female";  // Female is selected
        } else {
            pw.println("<h2>Please select a gender.</h2>");
            return;
        }
        // Rest of your code remains unchanged

        // Print the selected gender in the response
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
        	PreparedStatement ps = con.prepareStatement(query);){
        	ps.setString(1, doctorFName);
            ps.setString(2, doctorLName);
            ps.setDate(3, new java.sql.Date(doctorDOB.getTime())); // Convert to java.sql.Date
            ps.setString(4, doctorGender);
            ps.setString(5, doctorAddress);
            ps.setLong(6, doctorNumber);
            ps.setString(7, doctorEmail);
            ps.setString(8,doctorPosition );
            ps.setString(9,doctorSpecialization );
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
        pw.println("<a href='AddDoctor.html'>Add Doctor</a>");
        pw.println("<a href='doctorList'>Doctor List</a>");
    }
        		@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}