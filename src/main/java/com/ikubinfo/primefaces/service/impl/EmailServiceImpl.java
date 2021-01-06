package com.ikubinfo.primefaces.service.impl;

import com.ikubinfo.primefaces.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.SendFailedException;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(
            String to, String subject, String text) {
        try{SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("canajrediana@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);}
        catch(MailException ex){
            System.out.println(ex.getMessage());
        }
    }
}