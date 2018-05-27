package service;

import ip.db.StudentDbJPA;
import ip.domain.Student;
import ip.service.StudentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class StudentServiceTest {
    private StudentService service;

    @Mock
    private StudentDbJPA db;

    @InjectMocks
    private Student student = new Student("r0012345","Kate","Jones");

    @Before
    public void setup() throws Exception {
        initMocks(this);
        service = new StudentService(db);
    }

    @Test
    public void addStudent_should_ask_database_class_to_add_the_student() {
        Student newStudent = new Student("r0012346","Lucy","Jones");
        service.add(newStudent);
        verify(db).add(newStudent);
    }

    @Test
    public void updateStudent_should_ask_database_to_update_given_student(){
        Student updatedStudent = student;
        updatedStudent.setFirstName("John");
        service.update(updatedStudent);
        verify(db).update(updatedStudent);
    }

    @Test
    public void getAllStudents_should_ask_database_to_return_all_students(){
        service.getAll();
        verify(db).getAll();
    }

    @Test
    public void removeStudent_should_ask_database_to_remove_given_student(){
        long id = student.getId();
        service.delete(id);
        verify(db).delete(id);
    }

    @Test
    public void getStudent_should_ask_database_to_return_student(){
        long id = student.getId();
        service.get(id);
        verify(db).get(id);
    }

}
