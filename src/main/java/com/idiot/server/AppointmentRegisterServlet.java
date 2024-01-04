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

@jakarta.servlet.annotation.WebServlet("/AppointmentRegister")
public class AppointmentRegisterServlet extends HttpServlet {
    private static final String query = "INSERT INTO APPOINTMENT(PatientId, DoctorId, Date, Time, Status) VALUES(?,?,?,?,?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
     // GET THE patient info
        Integer patientID = Integer.parseInt(req.getParameter("patientID"));
        Integer doctorID = Integer.parseInt(req.getParameter("doctorID"));
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
            ps.setInt(1, patientID);
            ps.setInt(2, doctorID);
            ps.setDate(3, new java.sql.Date(appointmentDate1.getTime()));
            ps.setTime(4, new java.sql.Time(appointmentTime1.getTime()));  // Set the time value in the appropriate column
            ps.setString(5, appointmentStatus);

            int count = ps.executeUpdate();

            if (count == 1) {
                pw.println("<h2>Record is Registered Successfully...</h2>");
            } else {
                pw.println("<h2>Record is not Registered Successfully...</h2>");
            }
            ps.setString(4, appointmentStatus);
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>"+se.getMessage()+"</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>"+e.getMessage()+"</h2>");
        }
        pw.println("<a href='AddAppointment.html'>Add Appointment</a>");
        pw.println("<a href='appointmentList'>Appointment List</a>");
    }
        		@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}