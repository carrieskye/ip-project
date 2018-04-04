package service;

import ip.domain.Course;
import ip.domain.Teacher;
import ip.service.CourseService;
import ip.service.TeacherService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SmokeTest {
    private TeacherService teacherService = new TeacherService("Memory");
    private CourseService courseService = new CourseService("Memory");
    private Teacher teacher1;
    private Course course1, course2, course3;

    @Before
    public void testData() {
        teacher1 = new Teacher("u03942345", "An", "Goossens", 3);
        teacherService.add(teacher1);
        course1 = new Course( "XYZ1234", "Wiskunde", teacher1.getId());
        courseService.add(course1);
        course2 = new Course("ABC5678", "Nederlands", teacher1.getId());
        courseService.add(course2);
        course3 = new Course("EFG7890", "Frans", teacher1.getId());
        courseService.add(course3);
    }


    @Test
    public void add_adds_new_course_if_valid() {
        Teacher teacher2 = new Teacher("u04879878", "Tom", "Verschueren", 1);
        teacherService.add(teacher2);
        Course course4 = new Course("IJK3456", "Informatica", teacher2.getId());
        courseService.add(course4);

        assertEquals(course4, courseService.get(course4.getId()));
        assertEquals(course4.getTeacher(), teacher2.getId());
    }

    @Test
    public void getAll_shows_all_courses() {
        assertTrue(courseService.getAll().contains(course1));
        assertTrue(courseService.getAll().contains(course2));
        assertTrue(courseService.getAll().contains(course3));
    }

    @Test
    public void update_updates_values_of_course() {
        course1.setName("Fysica");
        courseService.update(course1);

        assertEquals(course1, courseService.get(course1.getId()));
    }

    @Test
    public void delete_removes_course_from_db() {
        courseService.delete(course3.getId());

        assertTrue(courseService.getAll().contains(course1));
        assertTrue(courseService.getAll().contains(course2));
        assertTrue(!courseService.getAll().contains(course3));
    }

    @After
    public void cleanData() {
        teacherService.delete(teacher1.getId());
        courseService.delete(course1.getId());
        courseService.delete(course2.getId());
        courseService.delete(course3.getId());
    }


}
