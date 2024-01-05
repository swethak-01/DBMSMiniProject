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
            pw.println("<h2>Error parsing date: " + e.getMessage() + "</h2>");
            e.printStackTrace(); // Log the exception details for debugging
            return;
        }

        String patientGender = req.getParameter("patientGender");

     // Check if the gender is not selected
     if (patientGender == null || patientGender.isEmpty()) {
         pw.println("<h2>Please select a gender.</h2>");
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
        pw.println("<a href='AddPatient.html'>Add Patient</a>");
        pw.println("<a href='patientList'>Patient List</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
