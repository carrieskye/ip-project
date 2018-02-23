package ip.db;

import ip.domain.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDbInMemory {
    private Map<String, Student> students = new HashMap<String, Student>();

    public StudentDbInMemory() {
    }

    public Student get(String id) {
        if (id == null) {
            throw new DbException("No id given");
        }
        return students.get(id);
    }

    public List<Student> getAll() {
        return new ArrayList<Student>(students.values());
    }

    public void add(Student student) throws DbException {
        if (student == null) {
            throw new DbException("No student given");
        }
        if (students.containsKey(student.getId())) {
            throw new DbException("Student already exists");
        }
        students.put(student.getId(), student);
    }

    public void update(Student student) {
        if (student == null) {
            throw new DbException("No student given");
        }
        if (!students.containsKey(student.getId())) {
            throw new DbException("No student found");
        }
        students.put(student.getId(), student);
    }

    public void delete(String id) {
        if (id == null) {
            throw new DbException("No id given");
        }
        students.remove(id);
    }

}