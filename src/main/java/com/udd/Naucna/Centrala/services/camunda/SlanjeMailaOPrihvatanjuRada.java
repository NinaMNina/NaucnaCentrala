package com.udd.Naucna.Centrala.services.camunda;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udd.Naucna.Centrala.model.Korisnik;
import com.udd.Naucna.Centrala.model.Proces;
import com.udd.Naucna.Centrala.repository.ProcessRepository;
import com.udd.Naucna.Centrala.services.KorisnikService;

@Service
public class SlanjeMailaOPrihvatanjuRada implements JavaDelegate{

	@Autowired
	private KorisnikService korisnikService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ProcessRepository procesRepository;
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Korisnik autor = korisnikService.findByKorisnickoIme(execution.getVariable("ps_korisnickoIme").toString());
		String mailAutor = autor.getEmail();
		Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", "587"); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("ninamns1095@gmail.com","Nina Miladinovic"));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mailAutor));
        msg.setSubject("UPP2019: Vas rad je prihvacen");
        msg.setContent("Castitamo! Ovim putem Vas obaveštavamo da je Vaš rad prihvaćen za objavu u caopisu. Potrebno je samo jos da uploadujete verziju rada koja će biti objavljena.","text/html");
        
        // Add a configuration set header. Comment or delete the 
        // next line if you are not using a configuration set
        msg.setHeader("X-SES-CONFIGURATION-SET", "ConfigSet");
        
            
        // Create a transport.
        Transport transport = session.getTransport();
                    
        // Send the message.
        try
        {
            System.out.println("Sending...");
            
            // Connect to Amazon SES using the SMTP username and password you specified above.
//          transport.connect("smtp.gmail.com", "ninamns1095@gmail.com", "**");
        	
            // Send the email.
//              transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
            Proces p = procesRepository.findByAutor(autor.getKorisnickoIme());
    		if(p!=null){
    			 Task zadatak = taskService.createTaskQuery().active().singleResult();
    			 if(zadatak!=null)
    				 p.setTaskId(zadatak.getId());
    		}
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
        finally
        {
            transport.close();
        }
	}
}