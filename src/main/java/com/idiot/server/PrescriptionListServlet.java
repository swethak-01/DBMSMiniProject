package com.idiot.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@jakarta.servlet.annotation.WebServlet("/prescriptionList")
public class PrescriptionListServlet extends HttpServlet {
	private static final String query = "SELECT PrescriptionID,DoctorID, PatientID, Diagnosis, Medication, PrescriptionDate FROM HOSPITALMANAGEMENT.PRESCRIPTION";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
       
     // LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
     // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///hospitalmanagement", "root", "root");
        	PreparedStatement ps = con.prepareStatement(query);){
        	ResultSet rs=ps.executeQuery();
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
        			+ "  <a class='tag' href='Admin.html'>ADMIN</a></header><br>");
        	pw.println("<center>\r\n"
        	        + "    <h3 style=\"font-family: 'Courier New', monospace; font-weight: bold; font-size: 25px;\">PRESCRIPTION LIST</h3>\r\n"
        	        + "  </center>");
pw.println("<a href='AddPrescription.html' class=\"button\" style=\"vertical-align:middle\"><span>Add</span></a><br>");
        	pw.println("<table class=\"w3-table-all w3-centered\">");
        	pw.println("<tr class=\"w3-hover\" style=\"background-color: #290066; color: white;\">");
            pw.println("<th>Prescription ID</th>");
            pw.println("<th>Doctor ID</th>");
            pw.println("<th>Patient ID</th>");
            pw.println("<th>Diagnosis</th>");
            pw.println("<th>Medication</th>");
            pw.println("<th>PrescriptionDate</th>");
            pw.println("<th>Edit</th>");
            pw.println("<th>Delete</th>");
            pw.println("</tr>");

        	while(rs.next()) {
        		pw.println("<tr>");
                pw.println("<td>" + rs.getInt(1) + "</td>");
                pw.println("<td>" + rs.getInt(2) + "</td>");
                pw.println("<td>" + rs.getInt(3) + "</td>");
                pw.println("<td>" + rs.getString(4) +"</td>");
                pw.println("<td>" + rs.getString(5) + "</td>");
                pw.println("<td>" + rs.getDate(6) + "</td>");
                pw.println("<td><a class='tag2' href='PrescriptionEditScreen?PrescriptionID="+rs.getInt("PrescriptionID")+"'>Edit</a></td>");
                pw.println("<td><a class='tag2'href='prescriptiondeleteurl?PrescriptionID="+rs.getInt(1)+"'>Delete</a></td>");
                pw.println("</tr>");

        	}
        	pw.println("</table>");
        	pw.println("</body>");
        	pw.println("</html>");

        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>"+se.getMessage()+"</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>"+e.getMessage()+"</h2>");
        }

        
    }
        		@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
