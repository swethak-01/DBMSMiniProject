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
@WebServlet("/AppointmentEditScreen")
public class AppointmentEditScreenServlet extends HttpServlet {
	private static final String query = "SELECT PatientID, DoctorID, Date, Time, Status FROM HOSPITALMANAGEMENT.APPOINTMENT where AppointmentID=?";

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
    	pw.println("<title>Edit Appointment </title>");
    	pw.println("</head>");
    	pw.println("<body>");
    	pw.println("<header> <span>GLOBAL HOSPITALS...</span>\r\n"
    			+ "        			<a class='tag' href='Admin.html'>ADMIN</a></header><br>\r\n"
    			+ "<center><h3 class='h3'>EDIT APPOINTMENT DETAILS</h3></center>");
     //get the id of record
        int AppointmentID=Integer.parseInt(req.getParameter("AppointmentID"));
     // LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
     // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///hospitalmanagement", "root", "root");
        	PreparedStatement ps = con.prepareStatement(query);){
        	ps.setInt(1, AppointmentID);
        	ResultSet rs=ps.executeQuery();
        	rs.next();
        	pw.println("<center>");
        	pw.println("<div class=\"content\">");
        	pw.println("<nav class='add'>");
        	pw.println("<form action='appointmentediturl?AppointmentID=" + AppointmentID + "' method='post'>");
        	pw.println("<table>");

        	// First Column
        	pw.println("<tr>");
        	pw.println("<td>Patient ID:</td>");
        	pw.println("<td><input class='box' type='text' name='PatientID' value='" + rs.getString(1) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Doctor ID:</td>");
        	pw.println("<td><input class='box' type='text' name='DoctorID' value='" + rs.getString(2) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Date Of Appointment:</td>");
        	pw.println("<td><input class='box' type='date' name='appointmentDate' value='" + rs.getDate(3) + "'></td>");
        	pw.println("</tr>");

        	
        	// Second Column
        	pw.println("<tr>");
        	pw.println("<td>Time Of Appointment:</td>");
        	pw.println("<td><input class='box' type='time' name='appointmentTime' value='" + rs.getTime(4) + "'></td>");
        	pw.println("</tr>");

        	pw.println("<tr>");
        	pw.println("<td>Status Of Appointment:</td>");
        	pw.println("<td><input class='box' type='text' name='appointmentStatus' value='" + rs.getString(5) + "'></td>");
        	pw.println("</tr>");

        	

        	// Buttons
        	pw.println("<tr>");
        	pw.println("<td><input class='button4' type='submit' value='Edit'></td>");
        	pw.println("<td><input class='button2' type='reset' value='Reset'></td>");
        	pw.println("</tr>");

        	pw.println("</table>");
        	pw.println("</form>");

        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h3>"+se.getMessage()+"</h3>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h3>"+e.getMessage()+"</h3>");
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
