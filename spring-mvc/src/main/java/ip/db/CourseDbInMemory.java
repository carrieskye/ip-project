package ip.db;

import ip.domain.Course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDbInMemory implements Db {
    private Map<Long, Course> courses = new HashMap<>();
    private int nextId;

    public CourseDbInMemory() {
        nextId = 1;
        addTestData();
    }

    private void addTestData() {
        add(new Course("MBI65X", "Webontwikkeling 3", 1, 1));
        add(new Course("MBI88X", "Testing", 2));
        add(new Course("MBI51X", "OO ontwerpen", 2));
        add(new Course("MBI39X", "Internetprogrammeren minor", 3));
    }

    public Course get(long id) {
        if (id == 0) {
            throw new DbException("No id given");
        }
        return courses.get(id);
    }

    public List<Object> getAll() {
        return new ArrayList<>(courses.values());
    }

    public List<Object> getAllSorted(String column) {
        //TODO
        return new ArrayList<>(courses.values());
    }

    public void add(Object object) throws DbException {
        Course course = (Course) object;
        if (course == null) {
            throw new DbException("No course given");
        }
        if (courses.containsKey(course.getId())) {
            throw new DbException("Course already exists");
        }
        course.setId(nextId);
        courses.put(course.getId(), course);
        nextId += 1;
    }

    public void update(Object object) {
        Course course = (Course) object;
        if (course == null) {
            throw new DbException("No course given");
        }
        if (!courses.containsKey(course.getId())) {
            throw new DbException("No course found");
        }
        courses.put(course.getId(), course);
    }

    public void delete(long id) {
        if (id == 0) {
            throw new DbException("No id given");
        }
        courses.remove(id);
    }

}