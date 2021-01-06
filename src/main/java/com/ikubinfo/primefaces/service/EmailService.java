package com.ikubinfo.primefaces.service;

public interface EmailService {
    void sendSimpleMessage(
            String to, String subject, String text);
}
