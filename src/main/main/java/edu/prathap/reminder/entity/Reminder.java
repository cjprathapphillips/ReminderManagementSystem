package edu.prathap.reminder.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name="reminder")
public class Reminder implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne()
    @JoinColumn(name = "reminder_type_id")
    private ReminderType reminderTypeId;
    private String frequency;
    private String reminderType;
    private Integer unit;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date renewDate;
    private Timestamp createdDate;
    private Boolean deleted;
    @ManyToOne()
//    @JoinColumn(name = "user_id")
    @JoinColumn(name = "user_id")
    private RmsUser user;
    @Transient
    private Boolean urgent;
    @Transient
    private Long urgentCountDays;
    @Transient
    private Long frequencyTypeCode;
    @Transient
    private Integer urgentCountMonth;
    @Transient
    private String renewDateString;

}
