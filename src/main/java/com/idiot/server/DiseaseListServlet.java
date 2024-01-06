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
@jakarta.servlet.annotation.WebServlet("/diseaseList")
public class DiseaseListServlet extends HttpServlet {
	private static final String query = "SELECT DiseaseID, DiseaseName,Vaccination, Symptoms, Treatment FROM HOSPITALMANAGEMENT.DISEASE";

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
        	pw.println("<table border='1' align='center'>");
            pw.println("<tr>");
            pw.println("<th>Disease ID</th>");
            pw.println("<th>Disease Name</th>");
            pw.println("<th>Vaccination</th>");
            pw.println("<th>Symptoms</th>");
            pw.println("<th>Treatment</th>");
            
            pw.println("<th>Edit</th>");
            pw.println("<th>Delete</th>");
            pw.println("</tr>");

        	while(rs.next()) {
        		pw.println("<tr>");
                pw.println("<td>" + rs.getInt(1) + "</td>");
                pw.println("<td>" + rs.getString(2) + "</td>");
                pw.println("<td>" + rs.getString(3) + "</td>");
                pw.println("<td>" + rs.getString(4) +"</td>");
                pw.println("<td>" + rs.getString(5) + "</td>");
                
                pw.println("<td><a href='DiseaseEditScreen?DiseaseID="+rs.getInt("DiseaseID")+"'>Edit</a></td>");
                pw.println("<td><a href='diseasedeleteurl?DiseaseID="+rs.getInt(1)+"'>Delete</a></td>");
                pw.println("</tr>");

        	}
        	pw.println("</table>");
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>"+se.getMessage()+"</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>"+e.getMessage()+"</h2>");
        }
        pw.println("<a href='AddDisease.html'>Add Disease</a>");
        
    }
        		@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
