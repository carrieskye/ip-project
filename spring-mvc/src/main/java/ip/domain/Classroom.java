package ip.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Classroom {
    public static ArrayList<String> allTypes = new ArrayList<>(Arrays.asList("Aula", "PC", "Regular", "Meeting room", "Other"));
    private long id;
    private String location;
    private int seats;
    private String type;
    private HashMap<Long, ClassroomOccupation> notAvailable = new HashMap<>();
    private int exams = 0;

    public Classroom() {

    }

    public Classroom(long id, String location, int seats, String type, int exams) {
        setId(id);
        setLocation(location);
        setSeats(seats);
        setType(type);
        this.exams = exams;
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

    public void increaseExams() {
        this.exams += 1;
    }

    public void decreaseExams() {
        this.exams -= 1;
    }

    public void occupation(long examId, LocalDate date, LocalTime begin, LocalTime end) {
        if (!notAvailable.containsKey(examId)) {
            notAvailable.put(examId, new ClassroomOccupation(date, begin, end));
        } else {
            notAvailable.replace(examId, new ClassroomOccupation(date, begin, end));
        }
    }

    public String isAvailable(LocalDate date, LocalTime begin, LocalTime end) {
        for (ClassroomOccupation occupation : notAvailable.values()) {
            if (occupation.getDate().equals(date)) {
                if ((begin.isAfter(occupation.getBegin()) && begin.isBefore(occupation.getEnd()))
                        || (end.isAfter(occupation.getBegin()) && end.isBefore(occupation.getEnd()))
                        || (begin.isBefore(occupation.getBegin()) && end.isAfter(occupation.getEnd()))) {
                    return "Classroom is already occupied between " + occupation.getBegin().toString() + " and " + occupation.getEnd().toString() + ".";
                }
            }
        }
        return null;
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