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
            pw.println("<h3 class='h3'>Invalid date format. Please use yyyy-MM-dd.</h3>");
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
            pw.println("<h3 class='h3'>Invalid time format. Please use HH:MM (24-hour clock).</h3>");
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
                pw.println("<h3 class='h3'>Record is Registered Successfully...</h3>");
            } else {
                pw.println("<h3 class='h3'>Record is not Registered Successfully...</h3>");
            }
            ps.setString(4, appointmentStatus);
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h3 class='h3'>"+se.getMessage()+"</h3>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h3 class='h3'>"+e.getMessage()+"</h3>");
        }
        pw.println("</body>");
        pw.println("</html>");
    }
        		@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}