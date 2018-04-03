package ip.db;

import ip.domain.Classroom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassroomDbInMemory implements Db {
    private Map<Long, Classroom> classrooms = new HashMap<>();

    public ClassroomDbInMemory() {
        addTestData();
    }

    private void addTestData() {
        add(new Classroom("D1.80", 200, "Aula"));
        add(new Classroom("D1.22", 40, "PC", 1));
        add(new Classroom("D1.28", 20, "Regular"));
    }

    @Override
    public Classroom get(long id) {
        if (id == 0) {
            throw new DbException("No id given");
        }
        return classrooms.get(id);
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
        if (classrooms.containsKey(classroom.getId())) {
            throw new DbException("Classroom already exists");
        }
        classroom.setId(classrooms.size() + 1);
        classrooms.put(classroom.getId(), classroom);
    }

    @Override
    public void update(Object object) {
        Classroom classroom = (Classroom) object;
        if (classroom == null) {
            throw new DbException("No classroom given");
        }
        if (!classrooms.containsKey(classroom.getId())) {
            throw new DbException("No classroom found");
        }
        classrooms.put(classroom.getId(), classroom);
    }

    @Override
    public void delete(long id) {
        if (id == 0) {
            throw new DbException("No id given");
        }
        classrooms.remove(id);
    }

}