package edu.prathap.reminder.service;
import java.io.File;
import java.util.Date;
import java.util.Properties;

import edu.prathap.reminder.entity.EmailDetails;
import edu.prathap.reminder.entity.Reminder;
import edu.prathap.reminder.repo.ReminderRepo;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;


@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    ReminderRepo reminderRepo;

    public void sendReminderMail(Long id){
        Reminder reminder = reminderRepo.getReferenceById(id);
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(reminder.getUser().getUsername());
        emailDetails.setSubject("Renewal Reminder For -"+reminder.getName()+"("+reminder.getReminderType()+") Sent On "+(new Date()).toString());
        emailDetails.setMsgBody(" Hi "+reminder.getUser().getUsername()
                +", \n Please renew Your "+reminder.getName() +"("+reminder.getReminderType()+") as it is getting expired on "+reminder.getRenewDate() +"\n"
                +"<table border=1 style=\"background-color:#FFFFE0;\">"
                +"<tr><td>Owner:</td><td>"+reminder.getUser().getUsername()+"</td></tr>"
                +"<tr><td>Type:</td><td>"+reminder.getReminderType()+"</td></tr>"
                +"<tr><td>Name:</td><td>"+reminder.getName()+"</td></tr>"
                +"<tr><td>Expire Date:</td><td>"+reminder.getRenewDate()+"</td></tr>"
//                +"Owner:"+reminder.getUser().getFirstName()+"\n"
//                +"Type:"+reminder.getReminderType()+"\n"
//                +"Name:"+reminder.getName()+"\n"
//                +"Expire Date:"+reminder.getRenewDate()+"\n"
                +"</table>"
        +" \n Thank You \n Have a Great Day \n Reminder Management System) Sent On "+(new Date()).toString());
        sendMail(emailDetails);
    }

    public void sendMail(EmailDetails emailDetails){
        Session session =getSendGridSession();
        Message message = new MimeMessage(session);
        try {
//            message.setFrom(new InternetAddress("prathapphillips@gmail.com"));
            message.setFrom(new InternetAddress("prathapreminderms@outlook.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(emailDetails.getRecipient())
            );
            message.setContent(emailDetails.getMsgBody(), "text/html");
            message.setSubject(emailDetails.getSubject());
//            message.setText(emailDetails.getMsgBody());

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    private Session getSendGridSession(){
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.sendgrid.net");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("apikey", "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");
                    }
                });
        return session;
    }

    private Session getYahooSession(){
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.mail.yahoo.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("yahoomailexternalaccess", "kzsmsknumtokxsnm");
                    }
                });
        return session;
    }

    private Session getGmailSession(){
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("prathapemailapplication", "fsiexwyuiaxshhud");
                    }
                });
        return session;
    }
}
