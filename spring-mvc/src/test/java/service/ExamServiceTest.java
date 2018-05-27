package service;

import ip.db.ExamDbJPA;
import ip.domain.Exam;
import ip.service.ExamService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ExamServiceTest {
    private ExamService service;

    @Mock
    private ExamDbJPA db;

    @InjectMocks
    private Exam exam = new Exam(1, LocalDate.of(2018,9,6), LocalTime.of(9,0), LocalTime.of(12,0), 1);

    @Before
    public void setup() throws Exception {
        initMocks(this);
        service = new ExamService(db);
    }

    @Test
    public void addExam_should_ask_database_class_to_add_the_exam() {
        Exam newExam = new Exam(2, LocalDate.of(2018,12,6), LocalTime.of(9,0), LocalTime.of(12,0), 2);
        service.add(newExam);
        verify(db).add(newExam);
    }

    @Test
    public void updateExam_should_ask_database_to_update_given_exam(){
        Exam updatedExam = exam;
        updatedExam.setDate(LocalDate.of(2020,8,5));
        service.update(updatedExam);
        verify(db).update(updatedExam);
    }

    @Test
    public void getAllExams_should_ask_database_to_return_all_exams(){
        service.getAll();
        verify(db).getAll();
    }

    @Test
    public void removeExam_should_ask_database_to_remove_given_exam(){
        long id = exam.getId();
        service.delete(id);
        verify(db).delete(id);
    }

    @Test
    public void getExam_should_ask_database_to_return_exam(){
        long id = exam.getId();
        service.get(id);
        verify(db).get(id);
    }

}
