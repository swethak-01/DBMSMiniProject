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


@jakarta.servlet.annotation.WebServlet("/appointmentediturl")
public class AppointmentEditServlet extends HttpServlet {
    private static final String query = "update HOSPITALMANAGEMENT.APPOINTMENT set PatientID=?, DoctorID=?, Date=?, Time=?, Status=?  where AppointmentID=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
        // get the id of the record
        int AppointmentID = Integer.parseInt(req.getParameter("AppointmentID"));

        // get the edited data we want to edit
        String PatientID = req.getParameter("PatientID");
        String DoctorID= req.getParameter("DoctorID");
        String appointmentDate = req.getParameter("appointmentDate");
        Date appointmentDate1 = null;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            appointmentDate1 = dateFormat.parse(appointmentDate);
            // Now 'appointmentDate1' contains the parsed date
        } catch (ParseException e) {
            // Handle the parse exception, e.g., invalid date format
            pw.println("<h2>Invalid date format. Please use yyyy-MM-dd.</h2>");
            return;
        }

        String appointmentTime = req.getParameter("appointmentTime");
        Date appointmentTime1 = null;

        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:MM");
            appointmentTime1 = timeFormat.parse(appointmentTime);
            // Now 'appointmentTime1' contains the parsed time
        } catch (ParseException e) {
            // Handle the parse exception, e.g., invalid time format
            pw.println("<h2>Invalid time format. Please use HH:MM (24-hour clock).</h2>");
            return;
        }

        
        String appointmentStatus = req.getParameter("appointmentStatus");
        
       

        // LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///hospitalmanagement", "root", "root");
                PreparedStatement ps = con.prepareStatement(query);) {
            ps.setString(1, PatientID);
            ps.setString(2, DoctorID);
            ps.setDate(3, new java.sql.Date(appointmentDate1.getTime()));
            ps.setTime(4, new java.sql.Time(appointmentTime1.getTime()));  // Set the time value in the appropriate column
            ps.setString(5, appointmentStatus);
            ps.setInt(6, AppointmentID);
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
        pw.println("<a href='AddAppointment.html'>Add Appointment</a>");
        pw.println("<a href='appointmentList'>Appointment List</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
