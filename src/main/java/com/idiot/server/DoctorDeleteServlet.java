package com.idiot.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/doctordeleteurl")
public class DoctorDeleteServlet extends HttpServlet {
	private static final String query = "delete from HOSPITALMANAGEMENT.DOCTOR  where DoctorID=?";

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
            
            int count = ps.executeUpdate();
            if (count == 1) {
                pw.println("<h3 class='h3'>Record is deleted successfully...</h3>");
            } else {
                pw.println("<h3 class='h3'>Record is not deleted successfully...</h3>");
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
