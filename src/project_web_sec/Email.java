package project_web_sec;

/**
 * Method adapted from one demonstrated by Pankaj
 * Pankaj, (2017). JavaMail Example - Send Mail in Java using SMTP. [online] JournalDev. 
 * Available at: http://www.journaldev.com/2532/javamail-example-send-mail-in-java-smtp 
 * [Accessed 1 Mar. 2017].
 */

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	
	public static void sendEmail (String adminAddress, String body) {
		
		// Authentication. The To email address is set in the Controller
		final String fromAddress = "projectwebsec@gmail.com";
		final String password = "Excite10";
		final String toAddress = adminAddress;
		
		// Set the properties of the email
		// SMTP Host, SSL Port, SSL Factory Class, enable SMTP authentication, SMTP Port 
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class",	"javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
		
		// This enables us to create a session and override the getPasswordAuthentication method
		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromAddress, password);
			}
		};		
		Session session = Session.getDefaultInstance(properties, authenticator);
		
		// 
		emailUtil(session, toAddress, "Unauthorised Modification Warning", body);
	}
	
	public static void emailUtil(Session session, String toAddress, String subject, String body) {
		try {
		      MimeMessage message = new MimeMessage(session);

		      // Set message headers
		      message.addHeader("Content-type", "text/HTML; charset=UTF-8");
		      message.addHeader("format", "flowed");
		      message.addHeader("Content-Transfer-Encoding", "8bit");

		      message.setFrom(new InternetAddress("projectwebsec@gmail.com", "Web_Sec_Monitor"));
		      message.setReplyTo(InternetAddress.parse("projectwebsec@gmail.com", false));

		      message.setSubject(subject, "UTF-8");
		      message.setText(body, "UTF-8");

		      message.setSentDate(new Date());

		      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress, false));
	    	  Transport.send(message);
	    	  System.out.println("Email sent successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

