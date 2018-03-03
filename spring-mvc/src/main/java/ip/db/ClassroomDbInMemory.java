package ip.db;

import ip.domain.Classroom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassroomDbInMemory implements Db {
    private Map<String, Classroom> classrooms = new HashMap<>();

    public ClassroomDbInMemory() {
    }

    @Override
    public Classroom get(String location) {
        if (location == null) {
            throw new DbException("No location given");
        }
        return classrooms.get(location);
    }

    @Override
    public List<Object> getAll() {
        return new ArrayList<>(classrooms.values());
    }

    @Override
    public void add(Object object) throws DbException {
        Classroom classroom = (Classroom) object;
        if (classroom == null) {
            throw new DbException("No classroom given");
        }
        if (classrooms.containsKey(classroom.getLocation())) {
            throw new DbException("Classroom already exists");
        }
        classrooms.put(classroom.getLocation(), classroom);
    }

    @Override
    public void update(Object object) {
        Classroom classroom = (Classroom) object;
        if (classroom == null) {
            throw new DbException("No classroom given");
        }
        if (!classrooms.containsKey(classroom.getLocation())) {
            throw new DbException("No classroom found");
        }
        classrooms.put(classroom.getLocation(), classroom);
    }

    @Override
    public void delete(String location) {
        if (location == null) {
            throw new DbException("No location given");
        }
        classrooms.remove(location);
    }

}