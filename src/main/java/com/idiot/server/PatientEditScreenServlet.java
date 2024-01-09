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
@WebServlet("/PatientEditScreen")
public class PatientEditScreenServlet extends HttpServlet {
	private static final String query = "SELECT FirstName, LastName, DateOfBirth, Gender, Address, PhoneNumber, Email, EmergencyContact FROM HOSPITALMANAGEMENT.PATIENT where PatientID=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
        pw.println("<html>");
    	pw.println("<head>");
    	pw.println("<link rel='stylesheet' type='text/css' href='styleedit.css'>");
    	pw.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
    	pw.println("<link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">");
    	pw.println("<meta charset=\"ISO-8859-1\">");
    	pw.println("<title>Edit Patient </title>");
    	pw.println("</head>");
    	pw.println("<body>");
    	pw.println("<header> <span>GLOBAL HOSPITALS...</span>\r\n"
    			+ "        			<a class='tag' href='AdminList'>ADMIN</a></header><br>\r\n"
    			+ "<center><h3 class='h3'>EDIT PATIENT DETAILS</h3></center>");
     //get the id of record
        int PatientID=Integer.parseInt(req.getParameter("PatientID"));
     // LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
     // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///hospitalmanagement", "root", "root");
        	PreparedStatement ps = con.prepareStatement(query);){
        	ps.setInt(1, PatientID);
        	ResultSet rs=ps.executeQuery();
        	rs.next();
        	pw.println("<center>");
        	pw.println("<div class=\"content\">");
        	pw.println("<nav class='add'>");
        	pw.println("<form action='patientediturl?PatientID=" + PatientID + "' method='post'>");
        	pw.println("<table>");

        	// First Column
        	pw.println("<tr>");
        	pw.println("<td>Patient First Name:</td>");
        	pw.println("<td><input class=\"box\" type='text' name='patientFName' value='" + rs.getString(1) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Patient Last Name:</td>");
        	pw.println("<td><input class=\"box\" type='text' name='patientLName' value='" + rs.getString(2) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Patient Date Of Birth:</td>");
        	pw.println("<td><input class=\"box\" type='date' name='patientDOB' value='" + rs.getDate(3) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Patient Gender:</td>");
        	pw.println("<td>");
        	pw.println("<input class=\"radio1\" type='radio' name='patientGender' value='Male' " + (rs.getString(4).equals("Male") ? "checked" : "") + "> Male");
        	pw.println("<input class=\"radio1\" type='radio' name='patientGender' value='Female' " + (rs.getString(4).equals("Female") ? "checked" : "") + "> Female");
        	pw.println("</td>");
        	pw.println("</tr>");

        	// Second Column
        	pw.println("<tr>");
        	pw.println("<td>Patient Address:</td>");
        	pw.println("<td><input class=\"box\" type='text' name='patientAddress' value='" + rs.getString(5) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Patient Phone Number:</td>");
        	pw.println("<td><input class=\"box\" type='text' name='patientNumber' value='" + rs.getLong(6) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Patient Email:</td>");
        	pw.println("<td><input class=\"box\" type='email' name='patientEmail' value='" + rs.getString(7) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Patient Emergency Contact:</td>");
        	pw.println("<td><input class=\"box\" type='text' name='patientEContact' value='" + rs.getLong(8) + "'></td>");
        	pw.println("</tr>");

        	// Buttons
        	pw.println("<tr>");
        	pw.println("<td><input class =\"button1\" type='submit' value='Edit'></td>");
        	pw.println("<td><input class =\"button2\" type='reset' value='Reset'></td>");
        	pw.println("</tr>");

        	pw.println("</table>");
        	pw.println("</form>");

        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>"+se.getMessage()+"</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>"+e.getMessage()+"</h2>");
        }
        pw.println("</nav>");
        pw.println("</div>");
        pw.println("</center>");
        pw.println("</body>");
        pw.println("</html>");
    }
        		@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
