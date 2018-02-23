package ip.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Exam {
    private LocalDateTime date;
    private Classroom classroom;
    private ArrayList<Student> students;

    public Exam() {
    }

    public Exam(LocalDateTime date, Classroom classroom) {
        setDate(date);
        setClassroom(classroom);
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