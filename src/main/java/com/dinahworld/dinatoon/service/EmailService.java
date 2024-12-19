package com.dinahworld.dinatoon.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendSimpleMessage(String email, String s, String confirmAccountMsg);

    void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException;
}