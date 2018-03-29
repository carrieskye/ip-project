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

    private void addTestData(){
        classrooms.put((long) classrooms.size()+1,new Classroom(classrooms.size()+1, "D1.80", 200, "Aula"));
        classrooms.put((long) classrooms.size()+1,new Classroom(classrooms.size()+1, "D1.22", 40, "PC"));
        classrooms.put((long) classrooms.size()+1,new Classroom(classrooms.size()+1, "D1.28", 20, "Regular"));
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