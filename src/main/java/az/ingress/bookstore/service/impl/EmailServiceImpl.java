package az.ingress.bookstore.service.impl;

import az.ingress.bookstore.service.EmailService;
import az.ingress.bookstore.wrapper.StudentWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;
    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailToStudents(List<StudentWrapper> students, String subject, String message) {
        for (StudentWrapper student : students) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(student.getUsername());
            mailMessage.setText(message);
            mailMessage.setSubject(subject);
            mailMessage.setFrom(fromEmail);
            javaMailSender.send(mailMessage);
        }
    }

}