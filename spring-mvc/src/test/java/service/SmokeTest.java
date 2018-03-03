package service;

import ip.domain.Course;
import ip.domain.Teacher;
import ip.service.CourseService;
import ip.service.TeacherService;
import org.junit.Test;
import static org.junit.Assert.*;

public class SmokeTest {
    TeacherService teacherService = new TeacherService("Memory");
    CourseService courseService = new CourseService("Memory");

    @Test
    public void getAll_shows_all_courses(){
        Teacher teacher1 = new Teacher("u03942345","An","Goossens");
        teacherService.add(teacher1);

        Course course1 = new Course("XYZ1234","Wiskunde",teacherService.get(teacher1.getId()));
        courseService.add(course1);

        assertEquals(course1,courseService.get(course1.getCode()));
        assertEquals(course1.getTeacher(),teacherService.get(teacher1.getId()));
    }
}
