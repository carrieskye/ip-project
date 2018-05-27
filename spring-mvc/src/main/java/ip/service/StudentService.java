package ip.service;

import ip.db.Db;
import ip.db.DbException;
import ip.db.DbFactoryInMemory;
import ip.db.DbFactoryJPA;
import ip.domain.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private Db db;

    public StudentService(String type) {
        if (type.equals("Memory")) {
            db = DbFactoryInMemory.createDb("Student");
        } else if (type.equals("JPA")) {
            db = DbFactoryJPA.createDb("Student");
        }
    }

    public StudentService(Db db){
        this.db = db;
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
        db.add(student);
    }

    public void update(Student student) {
        db.update(student);
    }

    public void delete(long id) {
        db.delete(id);
    }

    public boolean alreadyExists(Student newStudent) {
        for (Student student : getAll()) {
            if (student.equals(newStudent)) {
                return true;
            }
        }
        return false;
    }
}
