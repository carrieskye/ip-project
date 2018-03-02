package ip.db;

import ip.domain.Course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDbInMemory extends InMemoryDb {
    private Map<String, Course> courses = new HashMap<>();

    public CourseDbInMemory() {
    }

    @Override
    public Course get(String code) {
        if (code == null) {
            throw new DbException("No code given");
        }
        return courses.get(code);
    }

    @Override
    public List<Object> getAll() {
        return new ArrayList<>(courses.values());
    }

    @Override
    public void add(Object object) throws DbException {
        Course course = (Course) object;
        if (course == null) {
            throw new DbException("No course given");
        }
        if (courses.containsKey(course.getCode())) {
            throw new DbException("Course already exists");
        }
        courses.put(course.getCode(), course);
    }

    @Override
    public void update(Object object) {
        Course course = (Course) object;
        if (course == null) {
            throw new DbException("No course given");
        }
        if (!courses.containsKey(course.getCode())) {
            throw new DbException("No course found");
        }
        courses.put(course.getCode(), course);
    }

    @Override
    public void delete(String code) {
        if (code == null) {
            throw new DbException("No code given");
        }
        courses.remove(code);
    }

}