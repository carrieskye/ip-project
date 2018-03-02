package ip.db;

import ip.domain.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDbInMemory extends InMemoryDb {
    private Map<String, Student> students = new HashMap<>();

    public StudentDbInMemory() {
    }

    @Override
    public Student get(String id) {
        if (id == null) {
            throw new DbException("No id given");
        }
        return students.get(id);
    }

    @Override
    public List<Object> getAll() {
        return new ArrayList<>(students.values());
    }

    @Override
    public void add(Object object) throws DbException {
        Student student = (Student) object;
        if (student == null) {
            throw new DbException("No student given");
        }
        if (students.containsKey(student.getId())) {
            throw new DbException("Student already exists");
        }
        students.put(student.getId(), student);
    }

    @Override
    public void update(Object object) {
        Student student = (Student) object;
        if (student == null) {
            throw new DbException("No student given");
        }
        if (!students.containsKey(student.getId())) {
            throw new DbException("No student found");
        }
        students.put(student.getId(), student);
    }

    @Override
    public void delete(String id) {
        if (id == null) {
            throw new DbException("No id given");
        }
        students.remove(id);
    }

}