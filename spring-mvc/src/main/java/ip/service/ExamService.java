package ip.service;

import ip.db.*;
import ip.domain.Exam;

import java.util.ArrayList;
import java.util.List;

public class ExamService {
    private Db db;

    public ExamService(String type) {
        if (type == "Memory") {
            db = InMemoryDbFactory.createDb("Exam");
        } else if (type == "SQL") {
            db = SqlDbFactory.createDb("Exam");
        }
    }

    public Exam get(String id) {
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
        if (db.get(exam.getId()) == null) {
            db.add(exam);
        } else {
            db.update(exam);
        }
    }

    public void update(Exam exam) {
        db.update(exam);
    }

    public void delete(String id) {
        db.delete(id);
    }
}
