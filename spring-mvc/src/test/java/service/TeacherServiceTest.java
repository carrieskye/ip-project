package service;

import ip.db.TeacherDbJPA;
import ip.domain.Teacher;
import ip.service.TeacherService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class TeacherServiceTest {
    private TeacherService service;

    @Mock
    private TeacherDbJPA db;

    @InjectMocks
    private Teacher teacher = new Teacher("u0012345","Kate","Jones");

    @Before
    public void setup() {
        initMocks(this);
        service = new TeacherService(db);
    }

    @Test
    public void addTeacher_should_ask_database_class_to_add_the_teacher() {
        Teacher newTeacher =  new Teacher("u0012345","Lucy","Jones");
        service.add(newTeacher);
        verify(db).add(newTeacher);
    }

    @Test
    public void updateTeacher_should_ask_database_to_update_given_teacher(){
        Teacher updatedTeacher = teacher;
        updatedTeacher.setFirstName("John");
        service.update(updatedTeacher);
        verify(db).update(updatedTeacher);
    }

    @Test
    public void getAllTeacher_should_ask_database_to_return_all_teachers(){
        service.getAll();
        verify(db).getAll();
    }

    @Test
    public void removeTeacher_should_ask_database_to_remove_given_teacher(){
        long id = teacher.getId();
        service.delete(id);
        verify(db).delete(id);
    }

    @Test
    public void getTeacher_should_ask_database_to_return_teacher(){
        long id = teacher.getId();
        service.get(id);
        verify(db).get(id);
    }

}
