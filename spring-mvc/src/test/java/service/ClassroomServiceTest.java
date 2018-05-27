package service;

import ip.db.ClassroomDbJPA;
import ip.domain.Classroom;
import ip.service.ClassroomService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClassroomServiceTest {
    private ClassroomService service;

    @Mock
    private ClassroomDbJPA db;

    @InjectMocks
    private Classroom classroom = new Classroom("Helsinki", 150, "PC");

    @Before
    public void setup() throws Exception {
        initMocks(this);
        service = new ClassroomService(db);
    }

    @Test
    public void addClassroom_should_ask_database_class_to_add_the_classroom() {
        Classroom newClassroom = new Classroom("Oslo", 140, "Aula");
        service.add(newClassroom);
        verify(db).add(newClassroom);
    }

    @Test
    public void updateClassroom_should_ask_database_to_update_given_classroom(){
        Classroom updatedClassroom = classroom;
        updatedClassroom.setSeats(200);
        service.update(updatedClassroom);
        verify(db).update(updatedClassroom);
    }

    @Test
    public void getAllClassrooms_should_ask_database_to_return_all_classrooms(){
        service.getAll();
        verify(db).getAll();
    }

    @Test
    public void removeClassroom_should_ask_database_to_remove_given_classroom(){
        long id = classroom.getId();
        service.delete(id);
        verify(db).delete(id);
    }

    @Test
    public void getClassroom_should_ask_database_to_return_classroom(){
        long id = classroom.getId();
        service.get(id);
        verify(db).get(id);
    }

}
