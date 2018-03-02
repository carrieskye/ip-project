package ip.service;

import ip.db.*;
import ip.domain.Classroom;

import java.util.ArrayList;
import java.util.List;

public class ClassroomService {
    private Db db;

    public ClassroomService(String type) {
        if (type == "Memory") {
            db = InMemoryDb.createDb("Classroom");
        } else if (type == "SQL") {
            db = SQLDb.createDb("Classroom");
        }
    }

    public Classroom get(String id) {
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

    public void delete(String id) {
        db.delete(id);
    }
}
