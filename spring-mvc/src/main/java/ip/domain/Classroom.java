package ip.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Classroom {
    private Map<LocalDateTime, Boolean> available = new HashMap<LocalDateTime, Boolean>();
    private String location;
    private int seats;
    private boolean pc;

    public Classroom() {
    }

    public Classroom(String location, int seats, boolean pc){
        setLocation(location);
        setSeats(seats);
        setPc(pc);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (location.isEmpty()){
            throw new DomainException("No location given");
        }
        this.location = location;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        if (seats <= 0){
            throw new DomainException("Number of seats cannot be 0 or a negative");
        }
        this.seats = seats;
    }

    public boolean hasPc() {
        return pc;
    }

    public void setPc(boolean pc) {
        this.pc = pc;
    }

}