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


@jakarta.servlet.annotation.WebServlet("/prescriptionediturl")
public class PrescriptionEditServlet extends HttpServlet {
    private static final String query = "update HOSPITALMANAGEMENT.PRESCRIPTION set DoctorID=?, PatientID=?, Diagnosis=?, Medication=?, PrescriptionDate=?  where PrescriptionID=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
        // get the id of the record
        int PrescriptionID = Integer.parseInt(req.getParameter("PrescriptionID"));

        // get the edited data we want to edit
        int DoctorID = Integer.parseInt(req.getParameter("DoctorID"));
        int PatientID = Integer.parseInt(req.getParameter("PatientID"));
        String Diagnosis = req.getParameter("Diagnosis");
        String Medication = req.getParameter("Medication");
        String PrescriptionD = req.getParameter("PrescriptionDate");
        Date PrescriptionDate;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            PrescriptionDate = dateFormat.parse(PrescriptionD);
            // Now 'patientDOB' contains the parsed date
        } catch (ParseException e) {
            // Handle the parse exception, e.g., invalid date format
            pw.println("<h2>Error parsing date: " + e.getMessage() + "</h2>");
            e.printStackTrace(); // Log the exception details for debugging
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
                PreparedStatement ps = con.prepareStatement(query);) {
            ps.setInt(1, DoctorID);
            ps.setInt(2, PatientID);
            ps.setString(3, Diagnosis);
            ps.setString(4, Medication);
            ps.setDate(5, new java.sql.Date(PrescriptionDate.getTime()));
            ps.setInt(6, PrescriptionID);
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
        pw.println("<a href='AddPrescription.html'>Add Prescription</a>");
        pw.println("<a href='prescriptionList'>Prescription List</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
