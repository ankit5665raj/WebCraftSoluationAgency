package user;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserDetails
 */
@WebServlet("/UserDetails")
public class UserDetails extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public UserDetails() {
        // Default constructor
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameters from the request
        String projectType = request.getParameter("projectType");
        String budget = request.getParameter("budget");
        String name = request.getParameter("name");
        String number = request.getParameter("number");
        String projectDes = request.getParameter("pd");
        String ownerEmail = "ankitanandraj9563@gmail.com";

        // Send email
        SendOwnerData(ownerEmail, projectType, budget, name, number, projectDes);
    }

    private void SendOwnerData(String ownerEmail, String projectType, String budget, String name, String mobile, String projectDes) {
        try {
            String host = "smtp.gmail.com";
            final String user = "aman2000anand2000@gmail.com";
            final String password = "dbgn vmnd akgh trca"; // Use App Password instead of real password

            Properties properties = new Properties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            // Get the session object
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
                }
            });

            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(ownerEmail));
            message.setSubject("Project Details Submission");

            // Construct email content
            String emailContent = "Name: " + name + "\n"
                               
                                + "Mobile No: " + mobile + "\n"
                                + "Project Type: " + projectType + "\n"
                                + "Budget: " + budget + "\n"
                                + "Project Description: " + projectDes;

            message.setText(emailContent);

            // Send the message
            Transport.send(message);
            System.out.println("Owner email sent successfully");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
