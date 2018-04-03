package ip.service;

import ip.db.Db;
import ip.db.DbException;
import ip.db.DbFactoryInMemory;
import ip.db.DbFactoryJPA;
import ip.domain.Classroom;

import java.util.ArrayList;
import java.util.List;

public class ClassroomService {
    private Db db;

    public ClassroomService(String type) {
        if (type.equals("Memory")) {
            db = DbFactoryInMemory.createDb("Classroom");
        } else if (type.equals("JPA")) {
            db = DbFactoryJPA.createDb("Classroom");
        }
    }

    public Classroom get(long id) {
        return (Classroom) db.get(id);
    }

    public List<Classroom> getAll() {
        List<Classroom> classrooms = new ArrayList<>();
        for (Object object : db.getAll()) {
            classrooms.add((Classroom) object);
        }
        return classrooms;
    }

    public void add(Classroom classroom) throws DbException {
        db.add(classroom);
    }

    public void update(Classroom classroom) {
        db.update(classroom);
    }

    public void delete(long id) {
        db.delete(id);
    }

    public boolean alreadyExists(Classroom newClassroom) {
        for (Classroom classroom : getAll()) {
            if (classroom.equals(newClassroom)) {
                return true;
            }
        }
        return false;
    }

}
