package ip.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class ClassroomOccupation {

    private LocalDate date;
    private LocalTime begin;
    private LocalTime end;

    public ClassroomOccupation(LocalDate date, LocalTime begin, LocalTime end) {
        setDate(date);
        setBegin(begin);
        setEnd(end);
    }

    public LocalDate getDate() {
        return date;
    }

    private void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getBegin() {
        return begin;
    }

    private void setBegin(LocalTime begin) {
        this.begin = begin;
    }

    public LocalTime getEnd() {
        return end;
    }

    private void setEnd(LocalTime end) {
        this.end = end;
    }

}
