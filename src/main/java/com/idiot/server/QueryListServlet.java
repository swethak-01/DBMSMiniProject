package com.idiot.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@jakarta.servlet.annotation.WebServlet("/QueryList")
public class QueryListServlet extends HttpServlet {
    private static final String query1 = "SELECT d.DoctorID, d.FirstName, d.LastName, SUM(b.TotalAmount) AS TotalAmountEarned "
            + "FROM hospitalmanagement.doctor d "
            + "INNER JOIN hospitalmanagement.billing b ON d.DoctorID = b.DoctorID "
            + "GROUP BY d.DoctorID;";
    private static final String query2 = "SELECT d.DoctorID, d.FirstName, d.LastName, AVG(b.TotalAmount) AS AverageBillingAmount "
            + "FROM hospitalmanagement.doctor d "
            + "INNER JOIN hospitalmanagement.billing b ON d.DoctorID = b.DoctorID "
            + "GROUP BY d.DoctorID "
            + "HAVING AverageBillingAmount = (SELECT MAX(AvgBilling) FROM (SELECT AVG(TotalAmount) AS AvgBilling FROM hospitalmanagement.billing GROUP BY DoctorID) AS MaxAvgBilling) "
            + "    OR AverageBillingAmount = (SELECT MIN(AvgBilling) FROM (SELECT AVG(TotalAmount) AS AvgBilling FROM hospitalmanagement.billing GROUP BY DoctorID) AS MinAvgBilling);";
    private static final String query3 = "SELECT p.PatientID, p.FirstName, p.LastName, SUM(b.TotalAmount) AS TotalBillingAmount\r\n"
    		+ "FROM hospitalmanagement.patient p\r\n"
    		+ "INNER JOIN hospitalmanagement.billing b ON p.PatientID = b.PatientID\r\n"
    		+ "GROUP BY p.PatientID\r\n"
    		+ "HAVING TotalBillingAmount = (SELECT MAX(TotalAmount) FROM (SELECT SUM(TotalAmount) AS TotalAmount FROM hospitalmanagement.billing GROUP BY PatientID) AS MaxBilling)\r\n"
    		+ "    OR TotalBillingAmount = (SELECT MIN(TotalAmount) FROM (SELECT SUM(TotalAmount) AS TotalAmount FROM hospitalmanagement.billing GROUP BY PatientID) AS MinBilling);\r\n"
    		+ "";
    private static final String query4 = "SELECT a1.PatientID, p.FirstName AS PatientFirstName, p.LastName AS PatientLastName, "
            + "a1.Date AS AppointmentDate1, a2.Date AS AppointmentDate2 "
            + "FROM hospitalmanagement.appointment a1 "
            + "INNER JOIN hospitalmanagement.appointment a2 ON a1.PatientID = a2.PatientID "
            + "AND a1.DoctorID = a2.DoctorID "
            + "AND DATEDIFF(a2.Date, a1.Date) = 1 "
            + "INNER JOIN hospitalmanagement.patient p ON a1.PatientID = p.PatientID";

    private static final String query5 = "SELECT d.DoctorID, d.FirstName, d.LastName\r\n"
    		+ "FROM hospitalmanagement.doctor d\r\n"
    		+ "INNER JOIN hospitalmanagement.appointment a ON d.DoctorID = a.DoctorID\r\n"
    		+ "INNER JOIN hospitalmanagement.patient p ON a.PatientID = p.PatientID\r\n"
    		+ "GROUP BY d.DoctorID\r\n"
    		+ "HAVING COUNT(DISTINCT CASE WHEN p.Gender = 'M' THEN p.PatientID END) > 0\r\n"
    		+ "   AND COUNT(DISTINCT CASE WHEN p.Gender = 'F' THEN p.PatientID END) > 0;\r\n"
    		+ "";
    private static final String query6 = "SELECT d.DoctorID, d.FirstName, d.LastName, COUNT(p.PrescriptionID) AS PrescriptionCount\r\n"
    		+ "FROM hospitalmanagement.doctor d\r\n"
    		+ "INNER JOIN hospitalmanagement.prescription p ON d.DoctorID = p.DoctorID\r\n"
    		+ "WHERE p.Diagnosis = 'Hypertension'\r\n"
    		+ "GROUP BY d.DoctorID;\r\n"
    		+ "";
    private static final String query7 = "SELECT p.PatientID, p.FirstName, p.LastName, AVG(b.TotalAmount) AS AverageTotalAmount\r\n"
    		+ "FROM hospitalmanagement.patient p\r\n"
    		+ "INNER JOIN hospitalmanagement.billing b ON p.PatientID = b.PatientID\r\n"
    		+ "GROUP BY p.PatientID;\r\n"
    		+ "";
    private static final String query8 = "SELECT d.DoctorID, d.FirstName, d.LastName\r\n"
    		+ "FROM hospitalmanagement.doctor d\r\n"
    		+ "INNER JOIN hospitalmanagement.prescription pr ON d.DoctorID = pr.DoctorID\r\n"
    		+ "WHERE pr.Diagnosis = 'Hypertension';\r\n"
    		+ "";
    private static final String query9 = "SELECT p.PatientID, p.FirstName, p.LastName, SUM(b.TotalAmount) AS TotalAmount, COUNT(pr.PrescriptionID) AS PrescriptionCount\r\n"
    		+ "FROM hospitalmanagement.patient p\r\n"
    		+ "INNER JOIN hospitalmanagement.billing b ON p.PatientID = b.PatientID\r\n"
    		+ "LEFT JOIN hospitalmanagement.prescription pr ON p.PatientID = pr.PatientID\r\n"
    		+ "GROUP BY p.PatientID;\r\n"
    		+ "";
    private static final String query10 = "SELECT d.DoctorID, d.FirstName, d.LastName, COUNT(a.AppointmentID) AS AppointmentCount\r\n"
    		+ "FROM hospitalmanagement.doctor d\r\n"
    		+ "INNER JOIN hospitalmanagement.appointment a ON d.DoctorID = a.DoctorID\r\n"
    		+ "WHERE a.Date = '2024-01-01'  -- Replace with the desired date\r\n"
    		+ "GROUP BY d.DoctorID;\r\n"
    		+ "";
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
        pw.println("<title>Queries</title>");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<header>\r\n" + "    <span class=\"h1\">GLOBAL HOSPITALS...</span>\r\n"
                + "  <a class='tag' href='AdminList'>ADMIN</a></header><br>");
        pw.println("<center>\r\n"
                + "    <h3 style=\"font-family: 'Courier New', monospace; font-weight: bold; font-size: 25px;\">QUERIES</h3>\r\n"
                + "  </center>");
        // LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///hospitalmanagement", "root", "root");
             PreparedStatement ps1 = con.prepareStatement(query1);
             ResultSet rs1 = ps1.executeQuery()) {

            
            pw.println("1)Display doctors and the total amount earned through billing:");
            pw.println("<table class=\"w3-table-all w3-centered\">");
            pw.println("<tr class=\"w3-hover\" style=\"background-color: #290066; color: white;\">");
            pw.println("<th>Doctor ID</th>");
            pw.println("<th>First Name</th>");
            pw.println("<th>Last Name</th>");
            pw.println("<th>Total Amount Earned</th>");
            pw.println("</tr>");

            while (rs1.next()) {
                pw.println("<tr>");
                pw.println("<td>" + rs1.getInt(1) + "</td>");
                pw.println("<td>" + rs1.getString(2) + "</td>");
                pw.println("<td>" + rs1.getString(3) + "</td>");
                pw.println("<td>" + rs1.getFloat(4) + "</td>");
                pw.println("</tr>");
            }

            pw.println("</table>");

            // Execute the second query
            try (PreparedStatement ps2 = con.prepareStatement(query2);
                 ResultSet rs2 = ps2.executeQuery()) {

                pw.println("<br>");
                pw.println("<hr>");
                pw.println("<br>");

                pw.println("2)Identify doctors with the highest and lowest average billing amounts:");
                pw.println("<table class=\"w3-table-all w3-centered\">");
                pw.println("<tr class=\"w3-hover\" style=\"background-color: #290066; color: white;\">");
                pw.println("<th>Doctor ID</th>");
                pw.println("<th>First Name</th>");
                pw.println("<th>Last Name</th>");
                pw.println("<th>Average Billing Amount</th>");
                pw.println("</tr>");

                while (rs2.next()) {
                    pw.println("<tr>");
                    pw.println("<td>" + rs2.getInt(1) + "</td>");
                    pw.println("<td>" + rs2.getString(2) + "</td>");
                    pw.println("<td>" + rs2.getString(3) + "</td>");
                    pw.println("<td>" + rs2.getFloat(4) + "</td>");
                    pw.println("</tr>");
                }

                pw.println("</table>");
            }
            try (PreparedStatement ps3 = con.prepareStatement(query3);
                    ResultSet rs3 = ps3.executeQuery()) {

                   pw.println("<br>");
                   pw.println("<hr>");
                   pw.println("<br>");

                   pw.println("3)List patients who have the highest and lowest total billing amounts:");
                   pw.println("<table class=\"w3-table-all w3-centered\">");
                   pw.println("<tr class=\"w3-hover\" style=\"background-color: #290066; color: white;\">");
                   pw.println("<th>Patient ID</th>");
                   pw.println("<th>First Name</th>");
                   pw.println("<th>Last Name</th>");
                   pw.println("<th>Total Billing Amount</th>");
                   pw.println("</tr>");

                   while (rs3.next()) {
                       pw.println("<tr>");
                       pw.println("<td>" + rs3.getInt(1) + "</td>");
                       pw.println("<td>" + rs3.getString(2) + "</td>");
                       pw.println("<td>" + rs3.getString(3) + "</td>");
                       pw.println("<td>" + rs3.getFloat(4) + "</td>");
                       pw.println("</tr>");
                   }

                   pw.println("</table>");
               }

            try (PreparedStatement ps4 = con.prepareStatement(query4);
                    ResultSet rs4 = ps4.executeQuery()) {

                   pw.println("<br>");
                   pw.println("<hr>");
                   pw.println("<br>");

                   pw.println("4)Find patients who have appointments with the same doctor on consecutive days:");
                   pw.println("<table class=\"w3-table-all w3-centered\">");
                   pw.println("<tr class=\"w3-hover\" style=\"background-color: #290066; color: white;\">");
                   pw.println("<th>Patint ID</th>");
                   pw.println("<th>First Name</th>");
                   pw.println("<th>Last Name</th>");
                   pw.println("<th>Appointment Date 1</th>");
                   pw.println("<th>Appointment Date 2</th>");
                   pw.println("<th>Average Billing Amount</th>");
                   pw.println("</tr>");

                   while (rs4.next()) {
                       pw.println("<tr>");
                       pw.println("<td>" + rs4.getInt(1) + "</td>");
                       pw.println("<td>" + rs4.getString(2) + "</td>");
                       pw.println("<td>" + rs4.getString(3) + "</td>");
                       pw.println("<td>" + rs4.getDate(4) + "</td>");
                       pw.println("<td>" + rs4.getDate(5) + "</td>");
                       pw.println("</tr>");
                   }

                   pw.println("</table>");
               }

            try (PreparedStatement ps5 = con.prepareStatement(query5);
                    ResultSet rs5 = ps5.executeQuery()) {

                   pw.println("<br>");
                   pw.println("<hr>");
                   pw.println("<br>");

                   pw.println("5)List doctors who have both male and female patients:");
                   pw.println("<table class=\"w3-table-all w3-centered\">");
                   pw.println("<tr class=\"w3-hover\" style=\"background-color: #290066; color: white;\">");
                   pw.println("<th>Doctor ID</th>");
                   pw.println("<th>First Name</th>");
                   pw.println("<th>Last Name</th>");;
                   pw.println("</tr>");

                   while (rs5.next()) {
                       pw.println("<tr>");
                       pw.println("<td>" + rs5.getInt(1) + "</td>");
                       pw.println("<td>" + rs5.getString(2) + "</td>");
                       pw.println("<td>" + rs5.getString(3) + "</td>");;
                       pw.println("</tr>");
                   }

                   pw.println("</table>");
               }

            try (PreparedStatement ps6 = con.prepareStatement(query6);
                    ResultSet rs6 = ps6.executeQuery()) {

                   pw.println("<br>");
                   pw.println("<hr>");
                   pw.println("<br>");

                   pw.println("6)Find doctors who have prescribed medications for a specific diagnosis and list the total number of prescriptions for each doctor:");
                   pw.println("<table class=\"w3-table-all w3-centered\">");
                   pw.println("<tr class=\"w3-hover\" style=\"background-color: #290066; color: white;\">");
                   pw.println("<th>Doctor ID</th>");
                   pw.println("<th>First Name</th>");
                   pw.println("<th>Last Name</th>");
                   pw.println("<th>Prescription Count</th>");
                   pw.println("</tr>");

                   while (rs6.next()) {
                       pw.println("<tr>");
                       pw.println("<td>" + rs6.getInt(1) + "</td>");
                       pw.println("<td>" + rs6.getString(2) + "</td>");
                       pw.println("<td>" + rs6.getString(3) + "</td>");
                       pw.println("<td>" + rs6.getInt(4) + "</td>");
                       pw.println("</tr>");
                   }

                   pw.println("</table>");
               }

            try (PreparedStatement ps7 = con.prepareStatement(query7);
                    ResultSet rs7 = ps7.executeQuery()) {

                   pw.println("<br>");
                   pw.println("<hr>");
                   pw.println("<br>");

                   pw.println("7)Calculate the average total amount of bills for each patient:");
                   pw.println("<table class=\"w3-table-all w3-centered\">");
                   pw.println("<tr class=\"w3-hover\" style=\"background-color: #290066; color: white;\">");
                   pw.println("<th>Patient ID</th>");
                   pw.println("<th>First Name</th>");
                   pw.println("<th>Last Name</th>");
                   pw.println("<th>Average Total Amount</th>");
                   pw.println("</tr>");

                   while (rs7.next()) {
                       pw.println("<tr>");
                       pw.println("<td>" + rs7.getInt(1) + "</td>");
                       pw.println("<td>" + rs7.getString(2) + "</td>");
                       pw.println("<td>" + rs7.getString(3) + "</td>");
                       pw.println("<td>" + rs7.getFloat(4) + "</td>");
                       pw.println("</tr>");
                   }

                   pw.println("</table>");
               }

            try (PreparedStatement ps8 = con.prepareStatement(query8);
                    ResultSet rs8 = ps8.executeQuery()) {

                   pw.println("<br>");
                   pw.println("<hr>");
                   pw.println("<br>");

                   pw.println("8)Identify doctors who have prescribed medications for hypertension:");
                   pw.println("<table class=\"w3-table-all w3-centered\">");
                   pw.println("<tr class=\"w3-hover\" style=\"background-color: #290066; color: white;\">");
                   pw.println("<th>Doctor ID</th>");
                   pw.println("<th>First Name</th>");
                   pw.println("<th>Last Name</th>");
                   
                   pw.println("</tr>");

                   while (rs8.next()) {
                       pw.println("<tr>");
                       pw.println("<td>" + rs8.getInt(1) + "</td>");
                       pw.println("<td>" + rs8.getString(2) + "</td>");
                       pw.println("<td>" + rs8.getString(3) + "</td>");
                       
                       pw.println("</tr>");
                   }

                   pw.println("</table>");
               }

            try (PreparedStatement ps9 = con.prepareStatement(query9);
                    ResultSet rs9 = ps9.executeQuery()) {

                   pw.println("<br>");
                   pw.println("<hr>");
                   pw.println("<br>");

                   pw.println("9)Show patients with their total billing amount and count of prescriptions:");
                   pw.println("<table class=\"w3-table-all w3-centered\">");
                   pw.println("<tr class=\"w3-hover\" style=\"background-color: #290066; color: white;\">");
                   pw.println("<th>Patient ID</th>");
                   pw.println("<th>First Name</th>");
                   pw.println("<th>Last Name</th>");
                   pw.println("<th>Total Amount</th>");
                   pw.println("<th>Prescription Count</th>");
                   pw.println("</tr>");

                   while (rs9.next()) {
                       pw.println("<tr>");
                       pw.println("<td>" + rs9.getInt(1) + "</td>");
                       pw.println("<td>" + rs9.getString(2) + "</td>");
                       pw.println("<td>" + rs9.getString(3) + "</td>");
                       pw.println("<td>" + rs9.getFloat(4) + "</td>");
                       pw.println("<td>" + rs9.getInt(4) + "</td>");
                       pw.println("</tr>");
                   }

                   pw.println("</table>");
               }

            try (PreparedStatement ps10 = con.prepareStatement(query10);
                    ResultSet rs10 = ps10.executeQuery()) {

                   pw.println("<br>");
                   pw.println("<hr>");
                   pw.println("<br>");

                   pw.println("10)List doctors who have appointments on a specific date and display the count:");
                   pw.println("<table class=\"w3-table-all w3-centered\">");
                   pw.println("<tr class=\"w3-hover\" style=\"background-color: #290066; color: white;\">");
                   pw.println("<th>Doctor ID</th>");
                   pw.println("<th>First Name</th>");
                   pw.println("<th>Last Name</th>");
                   pw.println("<th>Appointment Count</th>");
                   pw.println("</tr>");

                   while (rs10.next()) {
                       pw.println("<tr>");
                       pw.println("<td>" + rs10.getInt(1) + "</td>");
                       pw.println("<td>" + rs10.getString(2) + "</td>");
                       pw.println("<td>" + rs10.getString(3) + "</td>");
                       pw.println("<td>" + rs10.getInt(4) + "</td>");
                       pw.println("</tr>");
                   }

                   pw.println("</table>");
               }

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}