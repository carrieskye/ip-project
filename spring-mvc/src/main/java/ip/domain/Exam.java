package ip.domain;

import ip.converter.LocalDateConverter;
import ip.converter.LocalTimeConverter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Entity
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long course, classroom;
    @Convert(converter = LocalDateConverter.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @Convert(converter = LocalTimeConverter.class)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime beginTime, endTime;

    @Transient
    private HashMap<String, Object> attributes = new HashMap<>();

    public Exam() {
    }

    public Exam(long course, LocalDate date, LocalTime beginTime, LocalTime endTime, long classroom) {
        setDate(date);
        setCourse(course);
        setBeginTime(beginTime);
        setEndTime(endTime);
        setClassroom(classroom);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCourse() {
        return course;
    }

    public void setCourse(long course) {
        if (course <= 0) {
            throw new DomainException("Please enter an exam course.");
        }
        this.course = course;
    }

    public void setBeginTime(LocalTime begin) {
        this.beginTime = begin;
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public void setEndTime(LocalTime end) {
        if (end.isBefore(beginTime)) {
            throw new DomainException("End time has to be after begin time.");
        }
        this.endTime = end;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new DomainException("Date has to be in the future.");
        }
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return formatter.format(date);
    }

    public String getTimeString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return formatter.format(beginTime) + " - " + formatter.format(endTime);
    }

    public long getClassroom() {
        return classroom;
    }

    public void setClassroom(long classroom) {
        if (classroom <= 0) {
            throw new DomainException("Please enter an exam location.");
        }
        this.classroom = classroom;
    }

    public HashMap<String, Object> getAttributes() {
        return this.attributes;
    }

    public void setAttribute(String key, Object object) {
        attributes.put(key, object);
    }

    @Override
    public boolean equals(Object object) {
        Exam exam = (Exam) object;
        return exam.course == this.course && exam.date.equals(this.date) && exam.beginTime.equals(this.beginTime) && exam.endTime.equals(this.endTime) && exam.classroom == this.classroom;
    }
}