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
@WebServlet("/DoctorEditScreen")
public class DoctorEditScreenServlet extends HttpServlet {
	private static final String query = "SELECT FirstName, LastName, DateOfBirth, Gender, Address, PhoneNumber, Email, Position, Specialization FROM HOSPITALMANAGEMENT.DOCTOR WHERE DoctorID=?";

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
    	pw.println("<title>Edit Doctor </title>");
    	pw.println("</head>");
    	pw.println("<body>");
    	pw.println("<header> <span>GLOBAL HOSPITALS...</span>\r\n"
    			+ "        			<a class='tag' href='AdminList'>ADMIN</a></header><br>\r\n"
    			+ "<center><h3 class='h3'>EDIT DOCTOR DETAILS</h3></center>");
        //get the id of the record
        int DoctorID = Integer.parseInt(req.getParameter("DoctorID"));
        
       
      

     
     // LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
     // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///hospitalmanagement", "root", "root");
        	PreparedStatement ps = con.prepareStatement(query);){
        	ps.setInt(1, DoctorID);
        	ResultSet rs=ps.executeQuery();
        	rs.next();
        	pw.println("<center>");
        	pw.println("<div class=\"content\">");
        	pw.println("<nav class='add'>");
        	pw.println("<form action='doctorediturl?DoctorID=" + DoctorID + "' method='post'>");
        	pw.println("<table>");
        	pw.println("<tr>");
        	pw.println("<td>Doctor First Name:<td>");
        	pw.println("<td><input class='box' type='text' name='doctorFName' value='"+rs.getString(1)+"'></td>");
        	pw.println("</tr>");
        	pw.println("<tr>");
        	pw.println("<td>Doctor Last Name:<td>");
        	pw.println("<td><input class='box' type='text' name='doctorLName' value='"+rs.getString(2)+"'></td>");
        	pw.println("</tr>");
        	pw.println("<tr>");
        	pw.println("<td>Doctor Date Of Birth:<td>");
        	pw.println("<td><input class='box' type='date' name='doctorDOB' value='"+rs.getDate(3)+"'></td>");
        	pw.println("</tr>");
        	pw.println("<tr>");
        	pw.println("<td>Doctor Gender:</td>");
        	pw.println("<td>");
        	pw.println("<input class=\"radio1\" type='radio' name='doctorGender' value='Male' " + (rs.getString(4).equals("Male") ? "checked" : "") + "> Male");
        	pw.println("<input class=\"radio2\" type='radio' name='doctorGender' value='Female' " + (rs.getString(4).equals("Female") ? "checked" : "") + "> Female");
        	pw.println("</td>");
        	pw.println("</tr>");
        	pw.println("<tr>");
        	pw.println("<td>Doctor Address:<td>");
        	pw.println("<td><input class='box' type='text' name='doctorAddress' value='"+rs.getString(5)+"'></td>");
        	pw.println("</tr>");
        	pw.println("<tr>");
        	pw.println("<td>Doctor Phone Number:<td>");
        	pw.println("<td><input class='box' type='text' name='doctorNumber' value='"+rs.getString(6)+"'></td>");
        	pw.println("</tr>");
        	pw.println("<tr>");
        	pw.println("<td>Doctor Email:<td>");
        	pw.println("<td><input class='box' type='email' name='doctorEmail' value='"+rs.getString(7)+"'></td>");
        	pw.println("</tr>");
        	pw.println("<tr>");
        	pw.println("<td>Doctor Position:<td>");
        	pw.println("<td><input class='box' type='text' name='doctorPosition' value='"+rs.getString(8)+"'></td>");
        	pw.println("</tr>");
        	pw.println("<tr>");
        	pw.println("<td>Doctor Specialization:<td>");
        	pw.println("<td><input class='box' type='text' name='doctorSpecialization' value='"+rs.getString(9)+"'></td>");
        	pw.println("</tr>");
        	pw.println("<tr>");

        	pw.println("<td><input class='button1' type='submit' value='Edit'</td>");
        	pw.println("<td><input class='button3' type='reset' value='Reset'</td>");
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