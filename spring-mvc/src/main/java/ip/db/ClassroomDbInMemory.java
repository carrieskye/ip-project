package ip.db;

import ip.domain.Classroom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassroomDbInMemory {
    private Map<String, Classroom> classrooms = new HashMap<String, Classroom>();

    public ClassroomDbInMemory() {
    }

    public Classroom get(String location) {
        if (location == null) {
            throw new DbException("No location given");
        }
        return classrooms.get(location);
    }

    public List<Classroom> getAll() {
        return new ArrayList<Classroom>(classrooms.values());
    }

    public void add(Classroom classroom) throws DbException {
        if (classroom == null) {
            throw new DbException("No classroom given");
        }
        if (classrooms.containsKey(classroom.getLocation())) {
            throw new DbException("Classroom already exists");
        }
        classrooms.put(classroom.getLocation(), classroom);
    }

    public void update(Classroom classroom) {
        if (classroom == null) {
            throw new DbException("No classroom given");
        }
        if (!classrooms.containsKey(classroom.getLocation())) {
            throw new DbException("No classroom found");
        }
        classrooms.put(classroom.getLocation(), classroom);
    }

    public void delete(String location) {
        if (location == null) {
            throw new DbException("No location given");
        }
        classrooms.remove(location);
    }

}