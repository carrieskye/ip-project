package ip.service;

import ip.db.*;
import ip.domain.Teacher;

import java.util.ArrayList;
import java.util.List;

public class TeacherService {
    private Db db;

    public TeacherService(String type) {
        if (type.equals("Memory")) {
            db = InMemoryDbFactory.createDb("Teacher");
        } else if (type.equals("SQL")) {
            db = SqlDbFactory.createDb("Teacher");
        }
    }

    public Teacher get(long id) {
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
        teacher.setId(getAll().size() + 1);
        db.add(teacher);
    }

    public void update(Teacher teacher) {
        db.update(teacher);
    }

    public void delete(long id) {
        db.delete(id);
    }
}
