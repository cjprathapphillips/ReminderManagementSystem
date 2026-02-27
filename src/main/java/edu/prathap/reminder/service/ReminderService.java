package edu.prathap.reminder.service;

import edu.prathap.reminder.entity.Reminder;
import edu.prathap.reminder.repo.ReminderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReminderService {
    @Autowired
    private ReminderRepo reminderRepo;

    public List<Reminder> reminderRenewable(){
        List<Reminder> reminderList=reminderRepo.reminderRenewable();
        return reminderList;
    }

    public List<Reminder> allNonRenewable(){
        List<Reminder> reminderList=reminderRepo.allNonRenewable();
        return reminderList;
    }
}
