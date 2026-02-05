package edu.prathap.reminder.service;

import edu.prathap.reminder.entity.EmailDetails;

public interface EmailService {
    void sendMail(EmailDetails details);
    void sendReminderMail(Long id);
}
