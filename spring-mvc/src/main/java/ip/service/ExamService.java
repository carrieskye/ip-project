package ip.service;

import ip.db.Db;
import ip.db.DbException;
import ip.db.DbFactoryInMemory;
import ip.db.DbFactoryJPA;
import ip.domain.Exam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ExamService {
    private Db db;

    public ExamService(String type) {
        if (type.equals("Memory")) {
            db = DbFactoryInMemory.createDb("Exam");
        } else if (type.equals("JPA")) {
            db = DbFactoryJPA.createDb("Exam");
        }
    }

    public Exam get(long id) {
        return (Exam) db.get(id);
    }

    public List<Exam> getAll() {
        List<Exam> exams = new ArrayList<>();
        for (Object object : db.getAll()) {
            exams.add((Exam) object);
        }
        return exams;
    }

    public void add(Exam exam) throws DbException {
        db.add(exam);
    }

    public void update(Exam exam) {
        db.update(exam);
    }

    public void delete(long id) {
        db.delete(id);
    }

    public boolean alreadyExists(Exam newExam) {
        for (Exam exam : getAll()) {
            if (exam.equals(newExam)) {
                return true;
            }
        }
        return false;
    }

    public String classroomIsStillAvailable(long newExam, long classroom, LocalDate date, LocalTime beginTime, LocalTime endTime) {
        for (Exam exam : getAll()) {
            if (newExam != exam.getId() && exam.getClassroom() == classroom && exam.getDate().equals(date)) {
                if ((beginTime.isAfter(exam.getBeginTime()) && beginTime.isBefore(exam.getEndTime()))
                        || (endTime.isAfter(exam.getBeginTime()) && endTime.isBefore(exam.getEndTime()))
                        || (beginTime.isBefore(exam.getBeginTime()) && endTime.isAfter(exam.getEndTime()))) {
                    return "Classroom is already occupied between " + exam.getBeginTime().toString() + " and " + exam.getEndTime().toString() + ".";
                }
            }
        }
        return null;
    }
}
