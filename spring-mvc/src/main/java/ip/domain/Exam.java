package ip.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Exam {
    private String id;
    private LocalDateTime date;
    private Classroom classroom;
    private ArrayList<Student> students;

    public Exam() {
    }

    public Exam(String id, LocalDateTime date, Classroom classroom) {
        setDate(date);
        setClassroom(classroom);
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        if (id.isEmpty()){
            throw new DomainException("No id given");
        }
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        if (date.isBefore(LocalDateTime.now())){
            throw new DomainException("Date is in the past");
        }
        this.date = date;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        if (classroom == null){
            throw new DomainException("No classroom given");
        }
        this.classroom = classroom;
    }
}