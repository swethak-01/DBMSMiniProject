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
            pw.println("<h2>Error parsing date: " + e.getMessage() + "</h2>");
            e.printStackTrace(); // Log the exception details for debugging
            return;
        }

        String doctorGender = req.getParameter("doctorGender");

     // Check if the gender is not selected
     if (doctorGender == null || doctorGender.isEmpty()) {
         pw.println("<h2>Please select a gender.</h2>");
         return;
     }

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
                pw.println("<h2>Record is edited successfully...</h2>");
            } else {
                pw.println("<h2>Record is not edited successfully...</h2>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h2>");
        }
        pw.println("<a href='AddDoctor.html'>Add Doctor</a>");
        pw.println("<a href='doctorList'>Doctor List</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
