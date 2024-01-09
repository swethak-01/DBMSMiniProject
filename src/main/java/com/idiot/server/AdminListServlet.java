package com.idiot.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminList")
public class AdminListServlet extends HttpServlet {
    private static final String query1 = "SELECT COUNT(*) FROM HOSPITALMANAGEMENT.PATIENT";
    private static final String query2 = "SELECT COUNT(*) FROM HOSPITALMANAGEMENT.DOCTOR";
    private static final String query3 = "SELECT COUNT(*) FROM HOSPITALMANAGEMENT.APPOINTMENT";
    private static final String query4 = "SELECT COUNT(*) FROM HOSPITALMANAGEMENT.PRESCRIPTION";
    private static final String query5 = "SELECT COUNT(*) FROM HOSPITALMANAGEMENT.BILLING";

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
        pw.println("<header>\r\n" + "    <span class=\"h1\">GLOBAL HOSPITALS...</span>\r\n"
                + "  <a class='tag' href='Admin.html'>ADMIN</a></header><br>");
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

        // LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///hospitalmanagement", "root", "root");
                PreparedStatement ps1 = con.prepareStatement(query1);
                PreparedStatement ps2 = con.prepareStatement(query2);
                PreparedStatement ps3 = con.prepareStatement(query3);
                PreparedStatement ps4 = con.prepareStatement(query4);
                PreparedStatement ps5 = con.prepareStatement(query5);) {

            ResultSet rs1 = ps1.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            ResultSet rs3 = ps3.executeQuery();
            ResultSet rs4 = ps4.executeQuery();
            ResultSet rs5 = ps5.executeQuery();

            pw.println("<table>");

            // Display counts
            displayCount(pw, "Patients", rs1);
            displayCount(pw, "Doctors", rs2);
            displayCount(pw, "Appointments", rs3);
            displayCount(pw, "Prescriptions", rs4);
            displayCount(pw, "Billing Records", rs5);

            pw.println("</table>");
            pw.println("</body>");
            pw.println("</html>");

        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h2>");
        }
    }

    private void displayCount(PrintWriter pw, String label, ResultSet resultSet) throws SQLException {
        pw.println("<tr>");
        pw.println("<td>" + label + ":</td>");
        while (resultSet.next()) {
            pw.println("<td>" + resultSet.getInt(1) + "</td>");
        }
        pw.println("</tr>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
