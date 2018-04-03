package ip.db;

import ip.domain.Exam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamDbInMemory implements Db {
    private Map<Long, Exam> exams = new HashMap<>();

    public ExamDbInMemory() {
        addTestData();
    }

    private void addTestData() {
        add(new Exam(1, LocalDate.of(2018, 6, 12), LocalTime.of(9, 0), LocalTime.of(12, 0), 2));
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
        exam.setId(exams.size() + 1);
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