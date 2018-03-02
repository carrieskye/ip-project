package ip.db;

import java.util.List;

public class SQLDb implements Db {

    public SQLDb createDb(String type) {
        switch (type) {
            case "Classroom":
                //TODO
            case "Course":
                //TODO
            case "Exam":
                //TODO
            case "Student":
                //TODO
            case "Teacher":
                //TODO
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
