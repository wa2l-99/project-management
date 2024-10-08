package com.pmt.project_management.email;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;


    // Méthode pour envoyer un e-mail de notification avec un template HTML
    @Async
    public void sendTaskAssignmentEmail(String to, String username, String taskName, String projectName, String subject) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name());

            Map<String, Object> properties = new HashMap<>();
            properties.put("username", username);
            properties.put("taskName", taskName);
            properties.put("projectName", projectName);

            Context context = new Context();
            context.setVariables(properties);

            mimeMessageHelper.setFrom("contact@Mpmt.org");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);

            String htmlContent = templateEngine.process("task-assignment", context);
            mimeMessageHelper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            // Log successful email sending
            System.out.println("Email sent successfully to " + to);
        } catch (MessagingException e) {
            // Log the error
            System.err.println("Failed to send email to " + to + ": " + e.getMessage());
            // You could also throw a custom exception or handle it accordingly
        }
    }
}