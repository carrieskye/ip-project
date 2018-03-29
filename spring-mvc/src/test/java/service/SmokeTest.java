package service;

import ip.domain.Course;
import ip.domain.Teacher;
import ip.service.CourseService;
import ip.service.TeacherService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SmokeTest {
    TeacherService teacherService = new TeacherService("Memory");
    CourseService courseService = new CourseService("Memory");
    Teacher teacher1;
    Course course1, course2, course3;

    @Before
    public void testData() {
        teacher1 = new Teacher(teacherService.getAll().size() + 1, "u03942345", "An", "Goossens");
        teacherService.add(teacher1);
        course1 = new Course(courseService.getAll().size() + 1, "XYZ1234", "Wiskunde", teacher1.getId());
        courseService.add(course1);
        course2 = new Course(courseService.getAll().size() + 1, "ABC5678", "Nederlands", teacher1.getId());
        courseService.add(course2);
        course3 = new Course(courseService.getAll().size() + 1, "EFG7890", "Frans", teacher1.getId());
        courseService.add(course3);
    }


    @Test
    public void add_adds_new_course_if_valid() {
        Teacher teacher2 = new Teacher(teacherService.getAll().size() + 1, "u04879878", "Tom", "Verschueren");
        teacherService.add(teacher2);
        Course course4 = new Course(courseService.getAll().size() + 1, "IJK3456", "Informatica", teacher2.getId());
        courseService.add(course4);

        assertEquals(course4, courseService.get(course4.getId()));
        assertEquals(course4.getTeacher(), teacher2.getId());
    }

    @Test
    public void getAll_shows_all_courses() {
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);

        assertEquals(courses, courseService.getAll());
    }

    @Test
    public void update_updates_values_of_course() {
        course1.setName("Fysica");
        courseService.update(course1);

        assertEquals(course1, courseService.get(course1.getId()));
    }

    @Test
    public void delete_removes_course_from_db() {
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);

        courseService.delete(course3.getId());

        assertEquals(courses, courseService.getAll());
        assertEquals(null, courseService.get(course3.getId()));
    }

    @After
    public void cleanData() {
        teacherService.delete(teacher1.getId());
        courseService.delete(course1.getId());
        courseService.delete(course2.getId());
        courseService.delete(course3.getId());
    }


}
