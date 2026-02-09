package edu.prathap.reminder.repo;

import edu.prathap.reminder.entity.Reminder;
import edu.prathap.reminder.entity.ReminderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReminderTypeRepo extends JpaRepository<ReminderType,Long> {
    public List<ReminderType> findAllByDeletedFalse();
}
