package ip.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Exam {
    private long id;
    private long course;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime datetime;
    private long classroom;
    private ArrayList<Student> students;
    private HashMap<String, Object> attributes = new HashMap<>();

    public Exam() {
    }

    public Exam(long id, long course, LocalDateTime datetime, long classroom) {
        setId(id);
        setCourse(course);
        setDatetime(datetime);
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
        this.course = course;
    }


    public void setDatetime(LocalDateTime datetime) {
        if (datetime.isBefore(LocalDateTime.now())) {
            throw new DomainException("Date is in the past");
        }
        this.datetime = datetime;
    }

    public LocalDateTime getDatetime(){
        return datetime;
    }

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return formatter.format(datetime);
    }

    public String getTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return formatter.format(datetime);
    }

    public long getClassroom() {
        return classroom;
    }

    public void setClassroom(long classroom) {
        this.classroom = classroom;
    }

    public HashMap<String, Object> getAttributes(){
        return this.attributes;
    }

    public void setAttribute(String key, Object object){
        attributes.put(key, object);
    }
}