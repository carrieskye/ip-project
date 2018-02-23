package ip.db;

import ip.domain.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherDbInMemory {
    private Map<String, Teacher> teachers = new HashMap<String, Teacher>();

    public TeacherDbInMemory() {
    }

    public Teacher get(String id) {
        if (id == null) {
            throw new DbException("No id given");
        }
        return teachers.get(id);
    }

    public List<Teacher> getAll() {
        return new ArrayList<Teacher>(teachers.values());
    }

    public void add(Teacher teacher) throws DbException {
        if (teacher == null) {
            throw new DbException("No teacher given");
        }
        if (teachers.containsKey(teacher.getId())) {
            throw new DbException("Teacher already exists");
        }
        teachers.put(teacher.getId(), teacher);
    }

    public void update(Teacher teacher) {
        if (teacher == null) {
            throw new DbException("No teacher given");
        }
        if (!teachers.containsKey(teacher.getId())) {
            throw new DbException("No teacher found");
        }
        teachers.put(teacher.getId(), teacher);
    }

    public void delete(String id) {
        if (id == null) {
            throw new DbException("No id given");
        }
        teachers.remove(id);
    }

}