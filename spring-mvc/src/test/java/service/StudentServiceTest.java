package service;

import ip.domain.Student;
import ip.service.StudentService;
import org.junit.Test;
import static org.junit.Assert.*;

public class StudentServiceTest {
    private StudentService service = new StudentService("Memory");

    @Test
    public void addStudent_stores_new_student(){
        Student newStudent = new Student(service.getAll().size() + 1, "r02934392","Jonas","Peeters" );
        service.add(newStudent);

        Student retrievedStudent = service.get(newStudent.getId());
        assertEquals(newStudent,retrievedStudent);
    }

}
