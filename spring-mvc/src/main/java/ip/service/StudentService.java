package ip.service;

import ip.db.*;
import ip.domain.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private Db db;

    public StudentService(String type) {
        if (type == "Memory") {
            db = InMemoryDbFactory.createDb("Student");
        } else if (type == "SQL") {
            db = SqlDbFactory.createDb("Student");
        }
    }

    public Student get(String id) {
        return (Student) db.get(id);
    }

    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        for (Object object : db.getAll()) {
            students.add((Student) object);
        }
        return students;
    }

    public void add(Student student) throws DbException {
        db.add(student);
    }

    public void update(Student student) {
        db.update(student);
    }

    public void delete(String id) {
        db.delete(id);
    }
}
