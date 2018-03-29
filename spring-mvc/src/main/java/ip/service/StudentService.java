package ip.service;

import ip.db.*;
import ip.domain.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private Db db;

    public StudentService(String type) {
        if (type.equals("Memory")) {
            db = InMemoryDbFactory.createDb("Student");
        } else if (type.equals("SQL")) {
            db = SqlDbFactory.createDb("Student");
        }
    }

    public Student get(long id) {
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
        student.setId(getAll().size() + 1);
        db.add(student);
    }

    public void update(Student student) {
        db.update(student);
    }

    public void delete(long id) {
        db.delete(id);
    }
}
