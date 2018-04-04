package service;

import ip.domain.DomainException;
import ip.domain.Teacher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TeacherServiceTest extends ServiceTest {
    private int records;

    @BeforeClass
    public static void initialize() {
        ServiceTest.initialize();
    }

    @Before
    public void setUp() {
        records = teacherService.getAll().size() - 2;
        teacherService.add(teacherOld);
    }

    @After
    public void clean() throws Exception {
        if (teacherService.get(teacherOld.getId()) != null) {
            teacherService.delete(teacherOld.getId());
        }
        if (teacherService.getAll().size() - 2 != records) {
            throw new Exception("State of db could not be recovered.");
        }
    }

    @Test
    public void addTeacher_with_correct_parameters() {
        teacherService.add(teacherNew);

        Teacher retrievedTeacher = teacherService.get(teacherNew.getId());
        assertEquals(teacherNew, retrievedTeacher);
        teacherService.delete(teacherNew.getId());
    }


    @Test(expected = DomainException.class)
    public void addTeacher_with_empty_number() {
        Teacher illegalTeacher = new Teacher("", teacherNew.getFirstName(), teacherNew.getLastName());
        teacherService.add(illegalTeacher);
    }

    @Test(expected = DomainException.class)
    public void addTeacher_with_empty_first_name() {
        Teacher illegalTeacher = new Teacher(teacherNew.getNumber(), "", teacherNew.getLastName());
        teacherService.add(illegalTeacher);
    }

    @Test(expected = DomainException.class)
    public void addTeacher_with_empty_last_name() {
        Teacher illegalTeacher = new Teacher(teacherNew.getNumber(), teacherNew.getFirstName(), "");
        teacherService.add(illegalTeacher);
    }

    @Test
    public void updateTeacher_with_correct_parameters() {
        Teacher teacher = teacherService.get(teacherOld.getId());
        teacher.setFirstName(teacherNew.getFirstName());
        teacher.setLastName(teacherNew.getLastName());
        teacherService.update(teacher);

        Teacher retrievedTeacher = teacherService.get(teacherOld.getId());
        assertEquals(teacherOld.getNumber(), retrievedTeacher.getNumber());
        assertEquals(teacherNew.getFirstName(), retrievedTeacher.getFirstName());
        assertEquals(teacherNew.getLastName(), retrievedTeacher.getLastName());
    }

    @Test(expected = DomainException.class)
    public void updateTeacher_with_empty_first_name() {
        Teacher teacher = teacherService.get(teacherOld.getId());
        teacher.setFirstName("");
        teacherService.update(teacher);
    }

    @Test
    public void removeTeacher_removes_teacher_from_overview() {
        teacherService.delete(teacherOld.getId());
        assertTrue(!teacherService.getAll().contains(teacherOld));
    }

}
