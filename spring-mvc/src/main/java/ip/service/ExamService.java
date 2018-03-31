package ip.service;

import ip.db.*;
import ip.domain.Classroom;
import ip.domain.Exam;

import java.util.ArrayList;
import java.util.List;

public class ExamService {
    private Db db;

    public ExamService(String type) {
        if (type.equals("Memory")) {
            db = InMemoryDbFactory.createDb("Exam");
        } else if (type.equals("SQL")) {
            db = SqlDbFactory.createDb("Exam");
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
        exam.setId(getAll().size() + 1);
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
}
