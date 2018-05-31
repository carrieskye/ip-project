package service;

import ip.db.CourseDbJPA;
import ip.domain.Course;
import ip.service.CourseService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class CourseServiceTest {
    private CourseService service;

    @Mock
    private CourseDbJPA db;

    @InjectMocks
    private Course course = new Course("MBI987","Frans",1);

    @Before
    public void setup() {
        initMocks(this);
        service = new CourseService(db);
    }

    @Test
    public void addCourse_should_ask_database_class_to_add_the_course() {
        Course newCourse = new Course("MBI986","Engels",2);
        service.add(newCourse);
        verify(db).add(newCourse);
    }

    @Test
    public void updateCourse_should_ask_database_to_update_given_course(){
        Course updatedCourse = course;
        updatedCourse.setName("German");
        service.update(updatedCourse);
        verify(db).update(updatedCourse);
    }

    @Test
    public void getAllCourses_should_ask_database_to_return_all_courses(){
        service.getAll();
        verify(db).getAll();
    }

    @Test
    public void removeCourse_should_ask_database_to_remove_given_course(){
        long id = course.getId();
        service.delete(id);
        verify(db).delete(id);
    }

    @Test
    public void getCourse_should_ask_database_to_return_course(){
        long id = course.getId();
        service.get(id);
        verify(db).get(id);
    }

}
