package com.twilio;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/store-message")
public class StoreMessageServlet extends HttpServlet {

    private static final String DATABASE_URL = "jdbc:postgresql://127.0.0.1:5432/Message";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    static {
        try {
            // Explicitly load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Extract the custom message from the form submission
        String message = request.getParameter("message");

        // Save the message in the database
        MessageDAO.saveMessage(message);

        // Redirect to the original form or a thank you page
        response.sendRedirect("TryCall.html");
    }
}
