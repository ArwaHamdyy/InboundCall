package com.twilio;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.twiml.voice.Say;
import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;

@WebServlet("/receive-call")
public class IncomingCallServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the latest message from the database
        String message = MessageDAO.getLatestMessage();

        // Customize the spoken message
        Say say = new Say.Builder(message).build();

        // Build the VoiceResponse including the Say instruction
        VoiceResponse twiml = new VoiceResponse.Builder().say(say).build();

        // Set the response content type to XML
        response.setContentType("text/xml");

        try {
            // Send the TwiML response back to Twilio
            response.getWriter().print(twiml.toXml());
        } catch (TwiMLException e) {
            // Handle TwiML exception (e.g., log error, return an error response)
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
