package dk.eamv.ferrari.sharedcomponents.email;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

// Made by: Mikkel

/**
 * Class to handle use of the SimpleJavaMail library.
 */
public class EmailService {

    /**
     * Standard method for sending an email from seller to sales manager.
     */
    public static void sendEmail() {
        Email email = EmailBuilder.startingBlank()
                .from("Sælgeren", "EMAIL_ADDRESS_HERE")
                .to("Salgschefen", "TO_EMAIL_ADDRESS_HERE")
                .withSubject("Lån overskrider min grænse")
                .withPlainText("""
                        Hej salgschef
                        
                        Jeg har oprettet et lånetilbud som overskrider min tilladte beløbsgrænse.
                        Vil du godkende aftalen?
                        
                        Mvh
                        Sælger
                        """)
                .buildEmail();

        // Sends the email on a new thread to keep the system responsive.
        new Thread(() -> MailerBuilder
                .withSMTPServer("Smtp.gmail.com", 587, "EMAIL_ADDRESS_HERE", "EMAIL_PASSWORD_HERE")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer()
                .sendMail(email)).start();
    }
}
