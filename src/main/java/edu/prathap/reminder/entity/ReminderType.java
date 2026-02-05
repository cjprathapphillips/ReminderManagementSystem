package edu.prathap.reminder.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name="reminder_type")
public class ReminderType implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String defaultFrequency;
    private Integer defaultUnit;
    private Timestamp createdDate;
    private Boolean deleted;
}
