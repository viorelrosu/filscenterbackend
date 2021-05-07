package com.fc.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fc.exceptions.ResourceNotFoundException;

@Service
public class MailService {
	
	@Autowired
	UsuarioService usuarioService;

	@Value("${email}")
	String username;
	@Value("${password}")
	String password;

	// Get system properties
	Properties properties = System.getProperties();

	public void contactMail(String from, String subject, String text) {

		// Setup mail server
		properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.enable", "false");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        
     // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(username, password);

            }

        });
		
		 try {
	            // Create a default MimeMessage object.
	            MimeMessage message = new MimeMessage(session);


	            // Set To: header field of the header.
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(username));

	            message.setSubject(subject);

	            message.setText("De: "+from+"\n\n"+text);

	            // Send message
	            Transport.send(message);

	        } catch (MessagingException mex) {
	            mex.printStackTrace();
	        }

	}
	
	public void resetPassword(String to) throws ResourceNotFoundException {

		// Setup mail server
		properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.enable", "false");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        
     // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(username, password);

            }

        });
        
		 try {
	            // Create a default MimeMessage object.
	            MimeMessage message = new MimeMessage(session);


	            // Set To: header field of the header.
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	            message.setSubject("Reset Password");

	            message.setText("Nueva contraseña: " + usuarioService.resetPassword(usuarioService.getUsuarioByEmail(to).getId()));

	            // Send message
	            Transport.send(message);

	        } catch (MessagingException mex) {
	            mex.printStackTrace();
	        }

	}

}
