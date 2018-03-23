package ip.db;

import ip.domain.Course;
import ip.domain.Exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamDbInMemory implements Db {
    private Map<Long, Exam> exams = new HashMap<>();

    public ExamDbInMemory() {
    }

    @Override
    public Exam get(long id) {
        if (id == 0) {
            throw new DbException("No id given");
        }
        return exams.get(id);
    }

    @Override
    public List<Object> getAll() {
        return new ArrayList<>(exams.values());
    }

    @Override
    public void add(Object object) throws DbException {
        Exam exam = (Exam) object;
        if (exam == null) {
            throw new DbException("No exam given");
        }
        if (exams.containsKey(exam.getId())) {
            throw new DbException("Exam already exists");
        }
        exams.put(exam.getId(), exam);
    }

    @Override
    public void update(Object object) {
        Exam exam = (Exam) object;
        if (exam == null) {
            throw new DbException("No exam given");
        }
        if (!exams.containsKey(exam.getId())) {
            throw new DbException("No exam found");
        }
        exams.put(exam.getId(), exam);
    }

    @Override
    public void delete(long id) {
        if (id == 0) {
            throw new DbException("No id given");
        }
        exams.remove(id);
    }

}