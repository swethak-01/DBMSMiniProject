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


@jakarta.servlet.annotation.WebServlet("/diseaseediturl")
public class DiseaseEditServlet extends HttpServlet {
    private static final String query = "update HOSPITALMANAGEMENT.DISEASE set DiseaseName=?, Vaccination=?, Symptoms=?, Treatment=?  where DiseaseID=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get PrintWriter
        PrintWriter pw = res.getWriter();
        // set content type
        res.setContentType("text/html");
        // get the id of the record
        int DiseaseID = Integer.parseInt(req.getParameter("DiseaseID"));

        // get the edited data we want to edit
        String diseaseName = req.getParameter("diseaseName");
        String vaccination = req.getParameter("vaccination");
        String symptoms = req.getParameter("symptoms");
        

        String treatment = req.getParameter("treatment");
        

        // LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        // generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///hospitalmanagement", "root", "root");
                PreparedStatement ps = con.prepareStatement(query);) {
            ps.setString(1, diseaseName);
            ps.setString(2, vaccination);
            ps.setString(3, symptoms);
            ps.setString(4, treatment);
            ps.setInt(5, DiseaseID);
            int count = ps.executeUpdate();
            if (count == 1) {
                pw.println("<h2>Record is edited successfully...</h2>");
            } else {
                pw.println("<h2>Record is not edited successfully...</h2>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h2>");
        }
        pw.println("<a href='AddDisease.html'>Add Disease</a>");
        pw.println("<a href='diseaseList'>Disease List</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
