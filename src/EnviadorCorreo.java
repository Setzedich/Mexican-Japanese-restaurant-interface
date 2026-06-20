import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EnviadorCorreo {

    public static boolean enviarCorreo(String correoDestino, String asunto, String mensaje) {
        boolean enviado = false;

        String correoRestaurante = "resmexjap@gmail.com";
        String claveAplicacion = "vtyjkgdbqoqpavon";

        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "587");
        propiedades.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session sesion = Session.getInstance(propiedades, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correoRestaurante, claveAplicacion);
            }
        });

        try {
            Message correo = new MimeMessage(sesion);

            correo.setFrom(new InternetAddress(correoRestaurante));
            correo.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino));
            correo.setSubject(asunto);
            correo.setText(mensaje);

            Transport.send(correo);

            enviado = true;

        } catch (MessagingException e) {
            System.out.println("Error al enviar correo:");
            System.out.println(e.getMessage());
            enviado = false;
        }

        return enviado;
    }
}