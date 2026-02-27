package edu.prathap.reminder.repo;

import edu.prathap.reminder.entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReminderRepo extends JpaRepository<Reminder,Long> {

    public List<Reminder> findAllByDeletedFalseOrderByRenewDate();

    @Query(value=" from Reminder reminder where reminder.reminderType in ('Birthday','Marriage Anniversary','Annual Day and Graduation') " +
            "and reminder.deleted=false order by month(renewDate),day(renewDate)")
    public List<Reminder> allNonRenewable();

    @Query(value=" from Reminder reminder where " +
            "reminder.reminderType in ('Vehicle Insurance','Vehicle Registration Certificate','Driving Licence','House Tax','Life Insurance') " +
            "and reminder.deleted=false order by renewDate")
    public List<Reminder> reminderRenewable();

    @Query(value=" from Reminder reminder where reminder.reminderType='Vehicle Insurance' and reminder.deleted=false order by renewDate")
    public List<Reminder> findAllByDeletedFalseAndReminderTypeVehicleInsuranceOrderByRenewDate();

    @Query(value=" from Reminder reminder where reminder.reminderType='Vehicle Registration Certificate' and reminder.deleted=false order by renewDate")
    public List<Reminder> findAllByDeletedFalseAndReminderTypeVehicleRegistrationCertificateOrderByRenewDate();

    @Query(value=" from Reminder reminder where reminder.reminderType='Driving Licence' and reminder.deleted=false order by renewDate")
    public List<Reminder> findAllByDeletedFalseAndReminderTypeDrivingLicenceOrderByRenewDate();

    @Query(value=" from Reminder reminder where reminder.reminderType='House Tax' and reminder.deleted=false order by renewDate")
    public List<Reminder> findAllByDeletedFalseAndReminderTypeHouseTaxOrderByRenewDate();

    @Query(value=" from Reminder reminder where reminder.reminderType='Birthday' and reminder.deleted=false order by renewDate")
    public List<Reminder> findAllByDeletedFalseAndReminderTypeBirthdayOrderByRenewDate();

    @Query(value=" from Reminder reminder where reminder.reminderType='Marriage Anniversary' and reminder.deleted=false order by renewDate")
    public List<Reminder> findAllByDeletedFalseAndReminderTypeMarriageAnniversaryOrderByRenewDate();


}
