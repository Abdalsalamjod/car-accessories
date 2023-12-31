package application.services;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import static application.Main.logger;

public class EmailSender {

  private static final String SENDER_EMAIL = "s12027747@stu.najah.edu";
  private static final String PASSWORD = "eicf ohjt igww owsa";
  private EmailSender() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated\n");
  }

  public static void sendEmail(String recipientEmail, String subject, String body) {

    Properties properties = new Properties();
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");


    Session session = Session.getInstance(properties, new Authenticator() {
     @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(SENDER_EMAIL, PASSWORD);
      }
    });

    try {

      Message message = new MimeMessage(session);

      message.setFrom(new InternetAddress(SENDER_EMAIL));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

      message.setSubject(subject);
      message.setText(body);

      Transport.send(message);

    } catch (MessagingException e) {
      logger.info(e.getMessage());

    }
  }

}