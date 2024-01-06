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
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/patientdeleteurl")
public class PatientDeleteServlet extends HttpServlet {
	private static final String query = "delete from HOSPITALMANAGEMENT.PATIENT  where PatientID=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
        // get the id of the record
        int PatientID = Integer.parseInt(req.getParameter("PatientID"));

        
        // LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///hospitalmanagement", "root", "root");
                PreparedStatement ps = con.prepareStatement(query);) {
         ps.setInt(1, PatientID);
            
            int count = ps.executeUpdate();
            if (count == 1) {
                pw.println("<h2>Record is deleted successfully...</h2>");
            } else {
                pw.println("<h2>Record is not deleted successfully...</h2>");
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
