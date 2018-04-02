package ip.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Exam {
    private long id;
    private long course;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime begin;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime end;
    private long classroom;
    private HashMap<String, Object> attributes = new HashMap<>();

    public Exam() {
    }

    public Exam(long id, long course, LocalDate date, LocalTime begin, LocalTime end, long classroom) {
        setId(id);
        setDate(date);
        setCourse(course);
        setBegin(begin);
        setEnd(end);
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


    public void setBegin(LocalTime begin) {
        if (begin.equals(end)) {
            throw new DomainException("Begin time has to be before end time.");
        }
        this.begin = begin;
    }

    public LocalTime getBegin() {
        return begin;
    }

    public void setEnd(LocalTime end) {
        if (end.isBefore(begin)) {
            throw new DomainException("End time has to be after begin time.");
        }
        this.end = end;
    }

    public LocalTime getEnd() {
        return end;
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
        return formatter.format(begin) + " - " + formatter.format(end);
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
        return exam.course == this.course && exam.date.equals(this.date) && exam.begin.equals(this.begin) && exam.end.equals(this.end) && exam.classroom == this.classroom;
    }
}