package service;

import ip.domain.DomainException;
import ip.domain.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StudentServiceIntegrationTest extends ServiceIntegrationTest {
    private int records;

    @BeforeClass
    public static void initialize() {
        ServiceIntegrationTest.initialize();
    }

    @Before
    public void setUp() {
        records = studentService.getAll().size() - 2;
        studentService.add(studentOld);
    }

    @After
    public void clean() throws Exception {
        if (studentService.get(studentOld.getId()) != null) {
            studentService.delete(studentOld.getId());
        }

        if (studentService.getAll().size() - 2 != records) {
            throw new Exception("State of db could not be recovered.");
        }
    }

    @Test
    public void addStudent_with_correct_parameters() {
        studentService.add(studentNew);
        Student retrievedStudent = studentService.get(studentNew.getId());
        assertEquals(studentNew, retrievedStudent);
        studentService.delete(studentNew.getId());
    }


    @Test(expected = DomainException.class)
    public void addStudent_with_empty_number() {
        Student illegalStudent = new Student("", studentNew.getFirstName(), studentNew.getLastName());
        studentService.add(illegalStudent);
    }

    @Test(expected = DomainException.class)
    public void addStudent_with_empty_first_name() {
        Student illegalStudent = new Student(studentNew.getNumber(), "", studentNew.getLastName());
        studentService.add(illegalStudent);
    }

    @Test(expected = DomainException.class)
    public void addStudent_with_empty_last_name() {
        Student illegalStudent = new Student(studentNew.getNumber(), studentNew.getFirstName(), "");
        studentService.add(illegalStudent);
    }

    @Test
    public void updateStudent_with_correct_parameters() {
        Student student = studentService.get(studentOld.getId());
        student.setFirstName(studentNew.getFirstName());
        student.setLastName(studentNew.getLastName());
        studentService.update(student);

        Student retrievedStudent = studentService.get(studentOld.getId());
        assertEquals(studentOld.getNumber(), retrievedStudent.getNumber());
        assertEquals(studentNew.getFirstName(), retrievedStudent.getFirstName());
        assertEquals(studentNew.getLastName(), retrievedStudent.getLastName());
    }

    @Test(expected = DomainException.class)
    public void updateStudent_with_empty_first_name() {
        Student student = studentService.get(studentOld.getId());
        student.setFirstName("");
        studentService.update(student);
    }

    @Test
    public void removeStudent_removes_student_from_overview() {
        studentService.delete(studentOld.getId());
        assertTrue(!studentService.getAll().contains(studentOld));
    }

}
