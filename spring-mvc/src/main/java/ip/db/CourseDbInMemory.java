package ip.db;

import ip.domain.Course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDbInMemory implements Db {
    private Map<Long, Course> courses = new HashMap<>();

    public CourseDbInMemory() {
        addTestData();
    }

    private void addTestData() {
        courses.put((long) (courses.size() + 1), new Course(courses.size() + 1, "MBI65X", "Webontwikkeling 3", 1));
        courses.put((long) (courses.size() + 1), new Course(courses.size() + 1, "MBI88X", "Testing", 2));
        courses.put((long) (courses.size() + 1), new Course(courses.size() + 1, "MBI51X", "OO ontwerpen", 2));
        courses.put((long) (courses.size() + 1), new Course(courses.size() + 1, "MBI39X", "Internetprogrammeren minor", 3));
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

    public void add(Object object) throws DbException {
        Course course = (Course) object;
        if (course == null) {
            throw new DbException("No course given");
        }
        if (courses.containsKey(course.getId())) {
            throw new DbException("Course already exists");
        }
        courses.put(course.getId(), course);
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