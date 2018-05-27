package ip.service;

import ip.db.Db;
import ip.db.DbException;
import ip.db.DbFactoryInMemory;
import ip.db.DbFactoryJPA;
import ip.domain.Teacher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {
    private Db db;

    public TeacherService() {
        this("Memory");
    }

    public TeacherService(String type) {
        if (type.equals("Memory")) {
            db = DbFactoryInMemory.createDb("Teacher");
        } else if (type.equals("JPA")) {
            db = DbFactoryJPA.createDb("Teacher");
        }
    }

    public TeacherService(Db db) {
        this.db = db;
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
        db.add(teacher);
    }

    public void update(Teacher teacher) {
        db.update(teacher);
    }

    public void delete(long id) {
        db.delete(id);
    }

    public boolean alreadyExists(Teacher newTeacher) {
        for (Teacher teacher : getAll()) {
            if (teacher.equals(newTeacher)) {
                return true;
            }
        }
        return false;
    }

    public void increaseCourses(Teacher teacher) {
        teacher.increaseCourses();
        update(teacher);
    }

    public void decreaseCourses(Teacher teacher) {
        teacher.decreaseCourses();
        update(teacher);
    }
}
