package ip.domain;

import javax.persistence.*;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.Arrays;

@Entity
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String location, type;
    private int seats;
    private int exams = 0;

    public static ArrayList<String> allTypes = new ArrayList<>(Arrays.asList("Aula", "PC", "Regular", "Meeting room", "Other"));

    public Classroom() {

    }

    public Classroom(String location, int seats, String type){
        setLocation(location);
        setSeats(seats);
        setType(type);
    }

    public Classroom(String location, int seats, String type, int exams) {
        this(location, seats, type);
        setExams(exams);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (location.isEmpty()) {
            throw new DomainException("No location given");
        }
        this.location = location;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        if (seats <= 0) {
            throw new DomainException("Number of seats cannot be 0 or a negative");
        }
        this.seats = seats;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type.trim().isEmpty()) {
            throw new DomainException("Please select a valid type.");
        }
        this.type = type;
    }

    public int getExams() {
        return this.exams;
    }

    public void setExams(int exams) {
        this.exams = exams;
    }

    public void increaseExams() {
        this.exams += 1;
    }

    public void decreaseExams() {
        this.exams -= 1;
    }

    public String getInfo() {
        return getLocation() + " (" + getSeats() + " seats, " + getType() + ")";
    }

    @Override
    public boolean equals(Object object) {
        Classroom classroom = (Classroom) object;
        return classroom.location.equals(this.location);
    }


}