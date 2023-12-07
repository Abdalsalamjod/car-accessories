package application.services;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

  private static final String senderEmail = "s12027747@stu.najah.edu";
  private static final String password = "eicf ohjt igww owsa";


  public static boolean sendEmail(String recipientEmail, String subject, String body) {

    Properties properties = new Properties();
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");


    Session session = Session.getInstance(properties, new Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(senderEmail, password);
      }
    });

    try {

      Message message = new MimeMessage(session);

      message.setFrom(new InternetAddress(senderEmail));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

      message.setSubject(subject);
      message.setText(body);

      Transport.send(message);
      return true;

    } catch (MessagingException e) {
      e.printStackTrace();
      return false;
    }
  }

}