package com.maximus.hcm.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Part;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

@Component
public class EmailSender {
	
	private static Logger logger = LoggerFactory.getLogger(EmailSender.class.getName());
	private static String MAIL_SERVER_PROP = "common.mail_server";
	private static String MAIL_SERVER_PORT_PROP = "common.mail.smtp.port";
	private static String MAIL_SERVER_STARTTLS_PROP = "common.mail.smtp.starttls.enable";
	private static String MAIL_HIGH_PRIORITY_FLAG_PROP = "common.mail.smtp.high.priority";
	private static String FROM_ADDRESS_ID_PROP = "common.mail_from_id";
	private static String DEFAULT_FROM_ADDRESS_ID = "donotreply@maximus.com";
	private static String DEFAULT_MAIL_SERVER = "WindowsMail.hhsc.texas.gov";
	private static String DEFAULT_MAIL_SERVER_PORT = "25";
	private static Environment environment;	
	private static String fromEmailAddressId;
	
	@Autowired
	public EmailSender(Environment env) {
		environment = env;
		fromEmailAddressId = environment.getProperty(FROM_ADDRESS_ID_PROP,DEFAULT_FROM_ADDRESS_ID);
	}
	
	public static boolean sendTextMail(ArrayList<String> recipients, String subject, String messageBody) 
		throws MessagingException, AddressException, Exception {
		return sendTextMail(recipients, null, null, subject, messageBody);
	}

	public static boolean sendTextMail(ArrayList<String> recipients,
									ArrayList<String> cc_recipients,
									ArrayList<String> bcc_recipients,
									String subject,
									String messageBody) 
								throws MessagingException, AddressException, Exception {
		
		// Setup mail server
		Properties props = System.getProperties();
		props.put("mail.smtp.host", environment.getProperty(MAIL_SERVER_PROP, DEFAULT_MAIL_SERVER));
		props.put("mail.smtp.port", environment.getProperty(MAIL_SERVER_PORT_PROP, DEFAULT_MAIL_SERVER_PORT));
		props.put("mail.smtp.starttls.enable", environment.getProperty(MAIL_SERVER_STARTTLS_PROP, "false"));
		props.put("mail.smtp.auth", "false");

		// Get a mail session
		Session session = Session.getDefaultInstance(props, null);

		// Define a new mail message
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromEmailAddressId));
		//Set To addresses
		message.setRecipients(Message.RecipientType.TO, 
				convertStringToInternetAddress(recipients));
		
		//Set CC addresses
		if(cc_recipients != null && cc_recipients.size() > 0){
			message.setRecipients(Message.RecipientType.CC, 
					convertStringToInternetAddress(cc_recipients));
		}
		
		//Set BCC addresses
		if(bcc_recipients != null && bcc_recipients.size() > 0){
			message.setRecipients(Message.RecipientType.BCC, 
						convertStringToInternetAddress(bcc_recipients));
		}
		
		message.setSubject(subject);

		// Create a message part to represent the body text
		BodyPart messageBodyPart = new MimeBodyPart();
//		messageBodyPart.setText(messageBody);
		messageBodyPart.setContent(messageBody, "text/html");
		messageBodyPart.setDisposition(Part.INLINE);
		
		if("true".equalsIgnoreCase(environment.getProperty(MAIL_HIGH_PRIORITY_FLAG_PROP, "false"))) {
			message.setHeader("X-Priority", "1");
		}

		// use a MimeMultipart as we need to handle the file attachments
		Multipart multipart = new MimeMultipart();

		// add the message body to the mime message
		multipart.addBodyPart(messageBodyPart);

		// Put all message parts in the message
		message.setContent(multipart);

		// Send the message
		Transport.send(message);
		
		logger.info("Email sent successfully to {} with subject {} and message body {}", Arrays.asList(recipients.toArray()).toString() ,subject, messageBody);
		
		return true;
	}

	protected static InternetAddress[] convertStringToInternetAddress(ArrayList<String> ids)
				throws MessagingException, AddressException {
		InternetAddress[] iAddresses = new InternetAddress[ids.size()];
		for (int i = 0; i < ids.size(); i++) {
			iAddresses[i] = new InternetAddress(ids.get(i));
		}
		
		return iAddresses;
	}

}
