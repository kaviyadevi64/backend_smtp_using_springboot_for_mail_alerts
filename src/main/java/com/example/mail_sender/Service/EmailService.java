package com.example.mail_sender.Service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public String sendMail(String param, String value) {
        try {
            String[] cc = {"jjeyaprakash58@gmail.com"};

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom("mkeerthish6@gmail.com");
            mimeMessageHelper.setTo("mkaviyadevi53@gmail.com");
            cc = Arrays.stream(cc)
                    .filter(email -> !email.isEmpty())
                    .toArray(String[]::new);

            // Log the updated contents of 'cc' array
            System.out.println("CC addresses: " + Arrays.toString(cc));

            // Check if cc is not empty before setting it
            if (cc.length > 0) {
                // Ensure each email address is properly formatted
                for (String ccAddress : cc) {
                    if (!isValidEmailAddress(ccAddress)) {
                        throw new RuntimeException("Invalid email address in 'cc' field: " + ccAddress);
                    }
                }
                mimeMessageHelper.setCc(cc);
            }

            // Set the subject and body of the email using the param and value
            mimeMessageHelper.setSubject("Threshold Exceeded: " + param);
            mimeMessageHelper.setText("Threshold value exceeded for parameter " + param + ". Value: " + value);

            javaMailSender.send(mimeMessage);

            return "Mail sent successfully";
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private boolean isValidEmailAddress(String email) {
        // Use a regular expression or a library for more robust validation
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }
}
