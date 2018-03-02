package ip.service;

import ip.db.*;
import ip.domain.Course;
import ip.domain.Exam;

import java.util.ArrayList;
import java.util.List;

public class ExamService implements Service {
    private Db db;

    public ExamService(String type) {
        if (type == "Memory") {
            db = new InMemoryDb();
        } else if (type == "SQL") {
            db = new SQLDb();
        }
        db.createDb("Exam");
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
        db.add(exam);
    }

    public void update(Exam exam) {
        db.update(exam);
    }

    public void delete(String id) {
        db.delete(id);
    }
}
