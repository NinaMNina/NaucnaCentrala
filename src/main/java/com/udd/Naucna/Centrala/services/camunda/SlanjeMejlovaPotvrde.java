package com.udd.Naucna.Centrala.services.camunda;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SlanjeMejlovaPotvrde implements JavaDelegate{

	private String smtpSender = "ninamns1095@gmail.com";
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication(smtpSender, "*");
		      }
		   });
		   try {
			   Message msg = new MimeMessage(session);
			   msg.setFrom(new InternetAddress(smtpSender, false));
	
			   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("ninamiladinovc@hotmail.com"));
			   msg.setSubject("UPP2019: Potvrda apliciranog rada");
			   msg.setContent("UPP2019: Potvrda apliciranog rada", "text/html");
			//   msg.setSentDate(new Date());
	
			   MimeBodyPart messageBodyPart = new MimeBodyPart();
			   messageBodyPart.setContent("Ovim mejlom ste dobili potvrdu da je vaš rad primljen u izbor radova za objavljivanje", "text/html");
	
			   Multipart multipart = new MimeMultipart();
			   multipart.addBodyPart(messageBodyPart);
	
			   Transport.send(msg);;
			   System.out.println("******************************POSLAO MEJLOVE****************************");
			   
			   Message msg1 = new MimeMessage(session);
			   msg1.setFrom(new InternetAddress(smtpSender, false));
	
			   msg1.setRecipients(Message.RecipientType.TO, InternetAddress.parse("moj.cicak@hotmail.com"));
			   msg1.setSubject("UPP2019: Novi aplicirani rad");
			   msg1.setContent("UPP2019: Novi aplicirani rad", "text/html");
		//	   msg1.setSentDate(new Date());
	
			   MimeBodyPart messageBodyPart1 = new MimeBodyPart();
			   messageBodyPart1.setContent("Ovim mejlom ste dobili potvrdu da je podnet zahtev za novi rad", "text/html");
	
			   Multipart multipart1 = new MimeMultipart();
			   multipart1.addBodyPart(messageBodyPart1);
	
			   Transport.send(msg1);
			   System.out.println("******************************POSLAO MEJLOVE****************************");
		   }catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }
	}

}
