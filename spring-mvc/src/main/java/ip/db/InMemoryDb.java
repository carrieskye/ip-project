package ip.db;

import java.util.List;

public class InMemoryDb implements Db {

    public InMemoryDb createDb(String type) {
        switch (type) {
            case "Classroom":
                return new ClassroomDbInMemory();
            case "Course":
                return new CourseDbInMemory();
            case "Exam":
                return new ExamDbInMemory();
            case "Student":
                return new StudentDbInMemory();
            case "Teacher":
                return new TeacherDbInMemory();
        }
        return null;
    }

    public Object get(String id) {
        throw new DbException("No database specified.");
    }

    public List<Object> getAll() {
        throw new DbException("No database specified.");
    }

    public void add(Object object) {
        throw new DbException("No database specified.");
    }

    public void update(Object object) {
        throw new DbException("No database specified.");
    }

    public void delete(String id) {
        throw new DbException("No database specified.");
    }
}
