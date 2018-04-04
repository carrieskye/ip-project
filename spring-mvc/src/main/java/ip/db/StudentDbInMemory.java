package ip.db;

import ip.domain.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDbInMemory implements Db {
    private Map<Long, Student> students = new HashMap<>();
    private int nextId;

    public StudentDbInMemory() {
        nextId = 1;
        addTestData();
    }

    private void addTestData() {
        add(new Student("r0458882", "Carolyne", "Peelman"));
        add(new Student( "r0567231", "Jonas", "Goovaerts"));
    }


    @Override
    public Student get(long id) {
        if (id == 0) {
            throw new DbException("No id given");
        }
        return students.get(id);
    }

    @Override
    public List<Object> getAll() {
        return new ArrayList<>(students.values());
    }

    @Override
    public void add(Object object) throws DbException {
        Student student = (Student) object;
        if (student == null) {
            throw new DbException("No student given");
        }
        if (students.containsKey(student.getId())) {
            throw new DbException("Student already exists");
        }
        student.setId(nextId);
        students.put(student.getId(), student);
        nextId += 1;
    }

    @Override
    public void update(Object object) {
        Student student = (Student) object;
        if (student == null) {
            throw new DbException("No student given");
        }
        if (!students.containsKey(student.getId())) {
            throw new DbException("No student found");
        }
        students.put(student.getId(), student);
    }

    @Override
    public void delete(long id) {
        if (id == 0) {
            throw new DbException("No id given");
        }
        students.remove(id);
    }

}