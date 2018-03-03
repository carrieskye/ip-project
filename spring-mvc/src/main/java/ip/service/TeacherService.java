package ip.service;

import ip.db.*;
import ip.domain.Teacher;

import java.util.ArrayList;
import java.util.List;

public class TeacherService {
    private Db db;

    public TeacherService(String type) {
        if (type == "Memory") {
            db = InMemoryDbFactory.createDb("Teacher");
        } else if (type == "SQL") {
            db = SqlDbFactory.createDb("Teacher");
        }
    }

    public Teacher get(String id) {
        return (Teacher) db.get(id);
    }

    public List<Teacher> getAll() {
        List<Teacher> teachers = new ArrayList<>();
        for (Object object : db.getAll()) {
            teachers.add((Teacher) object);
        }
        return teachers;
    }

    public void add(Teacher teacher) throws DbException {
        db.add(teacher);
    }

    public void update(Teacher teacher) {
        db.update(teacher);
    }

    public void delete(String id) {
        db.delete(id);
    }
}
