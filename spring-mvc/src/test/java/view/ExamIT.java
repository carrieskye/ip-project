package view;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ExamIT extends ObjectIT {
    private WebDriver driver;
    private int records;

    @BeforeClass
    public static void initialize() {
        ObjectIT.initialize();
    }

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Applications/App_downloads/chromedriver");
        driver = new ChromeDriver();
        super.setUp(driver);

        driver.get("http://localhost:8080/IP/exam.htm");
        records = driver.findElements(By.cssSelector("table tr")).size() - 2;

        addObject("teacher", personFields(teacherNumberOld, teacherFirstNameOld, teacherLastNameOld), personSelects());
        addObject("teacher", personFields(teacherNumberNew, teacherFirstNameNew, teacherLastNameNew), personSelects());
        addObject("course", courseFields(courseCodeOld, courseNameOld), courseSelects(courseTeacherOld));
        addObject("course", courseFields(courseCodeNew, courseNameNew), courseSelects(courseTeacherNew));
        addObject("classroom", classroomFields(classroomLocationOld, classroomSeatsOld), classroomSelects(classroomTypeOld));
        addObject("classroom", classroomFields(classroomLocationNew, classroomSeatsNew), classroomSelects(classroomTypeNew));
        addObject("exam", examFields(examDateOld, examBeginTimeOld, examEndTimeOld), examSelects(examCourseOld, examClassroomOld));
    }

    @After
    public void clean() throws Exception {
        if (getByUniqueValue("exam", examCourseOld) != null) {
            removeObjectByValue("exam", examCourseOld);
        }
        removeObjectByValue("course", courseCodeOld);
        removeObjectByValue("course", courseCodeNew);
        removeObjectByValue("teacher", teacherNumberOld);
        removeObjectByValue("teacher", teacherNumberNew);
        removeObjectByValue("classroom", classroomLocationOld);
        removeObjectByValue("classroom", classroomLocationNew);

        driver.get("http://localhost:8080/IP/exam.htm");
        if (driver.findElements(By.cssSelector("table tr")).size() - 2 != records) {
            throw new Exception("State of db could not be recovered.");
        }
        driver.quit();
    }


    @Test
    public void addExam_with_correct_parameters() {
        addObject("exam", examFields(examDateNew, examBeginTimeNew, examEndTimeNew), examSelects(examCourseNew, examClassroomNew));

        assertEquals("Exams", driver.getTitle());
        assertNotNull(getByUniqueValue("exam", examCourseNew));

        removeObjectByValue("exam", examCourseNew);
    }

    @Test
    public void addExam_without_course() {
        HashMap<String, String> examSelects = new HashMap<>();
        examSelects.put("classroom", examClassroomNew);
        addObject("exam", examFields(examDateNew, examBeginTimeNew, examEndTimeNew), examSelects);

        assertEquals("Add exam", driver.getTitle());
        assertEquals("Please enter an exam course.", driver.findElement(By.cssSelector(".has-error")).getText());
        Select selectCourse = new Select(driver.findElement(By.id("course")));
        assertEquals("0", selectCourse.getFirstSelectedOption().getAttribute("value"));
        assertEquals("Select course", selectCourse.getFirstSelectedOption().getAttribute("label"));
        assertEquals(toStringReversed(examDateNew), driver.findElement(By.id("date")).getAttribute("value"));
        assertEquals(toString(examBeginTimeNew), driver.findElement(By.id("beginTime")).getAttribute("value"));
        assertEquals(toString(examEndTimeNew), driver.findElement(By.id("endTime")).getAttribute("value"));
        assertEquals(examClassroomNew, new Select(driver.findElement(By.id("classroom"))).getFirstSelectedOption().getAttribute("label"));
    }


    @Test
    public void addExam_with_past_date() {
        LocalDate date = LocalDate.of(2000, 6, 20);
        addObject("exam", examFields(date, examBeginTimeNew, examEndTimeNew), examSelects(examCourseNew, examClassroomNew));

        assertEquals("Add exam", driver.getTitle());
        assertEquals("Please enter a valid date.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(examCourseNew, new Select(driver.findElement(By.id("course"))).getFirstSelectedOption().getAttribute("label"));
        assertEquals(toStringReversed(date), driver.findElement(By.id("date")).getAttribute("value"));
        assertEquals(toString(examBeginTimeNew), driver.findElement(By.id("beginTime")).getAttribute("value"));
        assertEquals(toString(examEndTimeNew), driver.findElement(By.id("endTime")).getAttribute("value"));
        assertEquals(examClassroomNew, new Select(driver.findElement(By.id("classroom"))).getFirstSelectedOption().getAttribute("label"));
    }

    @Test
    public void addExam_with_empty_date() {
        HashMap<String, String> examFields = new HashMap<>();
        examFields.put("beginTime", toString(examBeginTimeNew));
        examFields.put("endTime", toString(examEndTimeNew));
        addObject("exam", examFields, examSelects(examCourseNew, examClassroomNew));

        assertEquals("Add exam", driver.getTitle());
        assertEquals("Please enter a valid date.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(examCourseNew, new Select(driver.findElement(By.id("course"))).getFirstSelectedOption().getAttribute("label"));
        assertEquals("", driver.findElement(By.id("date")).getAttribute("value"));
        assertEquals(toString(examBeginTimeNew), driver.findElement(By.id("beginTime")).getAttribute("value"));
        assertEquals(toString(examEndTimeNew), driver.findElement(By.id("endTime")).getAttribute("value"));
        assertEquals(examClassroomNew, new Select(driver.findElement(By.id("classroom"))).getFirstSelectedOption().getAttribute("label"));
    }

    @Test
    public void addExam_with_empty_begin_time() {
        HashMap<String, String> examFields = new HashMap<>();
        examFields.put("date", toString(examDateNew));
        examFields.put("endTime", toString(examEndTimeNew));
        addObject("exam", examFields, examSelects(examCourseNew, examClassroomNew));

        assertEquals("Add exam", driver.getTitle());
        assertEquals("Please enter a valid time.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(examCourseNew, new Select(driver.findElement(By.id("course"))).getFirstSelectedOption().getAttribute("label"));
        assertEquals(toStringReversed(examDateNew), driver.findElement(By.id("date")).getAttribute("value"));
        assertEquals("", driver.findElement(By.id("beginTime")).getAttribute("value"));
        assertEquals(toString(examEndTimeNew), driver.findElement(By.id("endTime")).getAttribute("value"));
        assertEquals(examClassroomNew, new Select(driver.findElement(By.id("classroom"))).getFirstSelectedOption().getAttribute("label"));
    }

    @Test
    public void addExam_with_empty_end_time() {
        HashMap<String, String> examFields = new HashMap<>();
        examFields.put("date", toString(examDateNew));
        examFields.put("beginTime", toString(examBeginTimeNew));
        addObject("exam", examFields, examSelects(examCourseNew, examClassroomNew));

        assertEquals("Add exam", driver.getTitle());
        assertEquals("Please enter a valid time.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(examCourseNew, new Select(driver.findElement(By.id("course"))).getFirstSelectedOption().getAttribute("label"));
        assertEquals(toStringReversed(examDateNew), driver.findElement(By.id("date")).getAttribute("value"));
        assertEquals(toString(examBeginTimeNew), driver.findElement(By.id("beginTime")).getAttribute("value"));
        assertEquals("", driver.findElement(By.id("endTime")).getAttribute("value"));
        assertEquals(examClassroomNew, new Select(driver.findElement(By.id("classroom"))).getFirstSelectedOption().getAttribute("label"));
    }

    @Test
    public void addExam_with_classroom_occupied_on_begin_time() {
        addObject("exam", examFields(examDateOld, examBeginTimeOld.plusHours(1), examEndTimeOld.plusHours(1)), examSelects(examCourseNew, examClassroomOld));

        assertEquals("Add exam", driver.getTitle());
        assertEquals("Classroom is already occupied between 09:00 and 12:00.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(examCourseNew, new Select(driver.findElement(By.id("course"))).getFirstSelectedOption().getAttribute("label"));
        assertEquals(toStringReversed(examDateOld), driver.findElement(By.id("date")).getAttribute("value"));
        assertEquals(toString(examBeginTimeOld.plusHours(1)), driver.findElement(By.id("beginTime")).getAttribute("value"));
        assertEquals(toString(examEndTimeOld.plusHours(1)), driver.findElement(By.id("endTime")).getAttribute("value"));
        assertEquals(examClassroomOld, new Select(driver.findElement(By.id("classroom"))).getFirstSelectedOption().getAttribute("label"));

    }

    @Test
    public void addExam_with_classroom_occupied_on_end_time() {
        addObject("exam", examFields(examDateOld, examBeginTimeOld.minusHours(1), examEndTimeOld.minusHours(1)), examSelects(examCourseNew, examClassroomOld));

        assertEquals("Add exam", driver.getTitle());
        assertEquals("Classroom is already occupied between 09:00 and 12:00.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(examCourseNew, new Select(driver.findElement(By.id("course"))).getFirstSelectedOption().getAttribute("label"));
        assertEquals(toStringReversed(examDateOld), driver.findElement(By.id("date")).getAttribute("value"));
        assertEquals(toString(examBeginTimeOld.minusHours(1)), driver.findElement(By.id("beginTime")).getAttribute("value"));
        assertEquals(toString(examEndTimeOld.minusHours(1)), driver.findElement(By.id("endTime")).getAttribute("value"));
        assertEquals(examClassroomOld, new Select(driver.findElement(By.id("classroom"))).getFirstSelectedOption().getAttribute("label"));
    }

    @Test
    public void addExam_with_classroom_occupied_in_between_begin_and_end_time() {
        addObject("exam", examFields(examDateOld, examBeginTimeOld.minusHours(1), examEndTimeOld.plusHours(1)), examSelects(examCourseNew, examClassroomOld));

        assertEquals("Add exam", driver.getTitle());
        assertEquals("Classroom is already occupied between 09:00 and 12:00.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(examCourseNew, new Select(driver.findElement(By.id("course"))).getFirstSelectedOption().getAttribute("label"));
        assertEquals(toStringReversed(examDateOld), driver.findElement(By.id("date")).getAttribute("value"));
        assertEquals(toString(examBeginTimeOld.minusHours(1)), driver.findElement(By.id("beginTime")).getAttribute("value"));
        assertEquals(toString(examEndTimeOld.plusHours(1)), driver.findElement(By.id("endTime")).getAttribute("value"));
        assertEquals(examClassroomOld, new Select(driver.findElement(By.id("classroom"))).getFirstSelectedOption().getAttribute("label"));
    }

    @Test
    public void addExam_without_classroom() {
        HashMap<String, String> examSelects = new HashMap<>();
        examSelects.put("course", examCourseNew);
        addObject("exam", examFields(examDateNew, examBeginTimeNew, examEndTimeNew), examSelects);

        assertEquals("Add exam", driver.getTitle());
        assertEquals("Please enter an exam classroom.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(examCourseNew, new Select(driver.findElement(By.id("course"))).getFirstSelectedOption().getAttribute("label"));
        assertEquals(toStringReversed(examDateNew), driver.findElement(By.id("date")).getAttribute("value"));
        assertEquals(toString(examBeginTimeNew), driver.findElement(By.id("beginTime")).getAttribute("value"));
        assertEquals(toString(examEndTimeNew), driver.findElement(By.id("endTime")).getAttribute("value"));
        Select selectClassroom = new Select(driver.findElement(By.id("classroom")));
        assertEquals("0", selectClassroom.getFirstSelectedOption().getAttribute("value"));
        assertEquals("Select classroom", selectClassroom.getFirstSelectedOption().getAttribute("label"));
    }

    @Test
    public void updateExam_with_correct_parameters() {
        driver.get("http://localhost:8080/IP/exam.htm");
        WebElement exam = getByUniqueValue("exam", examCourseOld);
        assertNotNull(exam);
        exam.findElement(By.linkText(examCourseOld)).click();

        assertEquals("Update exam", driver.getTitle());
        selectField("classroom", examClassroomNew);
        driver.findElement(By.id("save")).click();

        exam = getByUniqueValue("exam", examCourseOld);
        assertNotNull(exam);
        assertTrue(exam.getText().contains(examClassroomNew));
    }

    @Test
    public void updateExam_and_remove_classroom() {
        driver.get("http://localhost:8080/IP/exam.htm");
        WebElement exam = getByUniqueValue("exam", examCourseOld);
        assertNotNull(exam);
        exam.findElement(By.linkText(examCourseOld)).click();

        assertEquals("Update exam", driver.getTitle());
        selectField("classroom", "Select classroom");
        driver.findElement(By.id("save")).click();

        assertEquals("Update exam", driver.getTitle());
        assertEquals("Please enter an exam classroom.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(examCourseOld, new Select(driver.findElement(By.id("course"))).getFirstSelectedOption().getAttribute("label"));
        assertEquals(toStringReversed(examDateOld), driver.findElement(By.id("date")).getAttribute("value"));
        assertEquals(toString(examBeginTimeOld), driver.findElement(By.id("beginTime")).getAttribute("value"));
        assertEquals(toString(examEndTimeOld), driver.findElement(By.id("endTime")).getAttribute("value"));
        assertEquals("Select classroom", new Select(driver.findElement(By.id("classroom"))).getFirstSelectedOption().getAttribute("label"));
    }

    @Test
    public void removeExam_removes_exam_from_overview() {
        driver.get("http://localhost:8080/IP/exam.htm");
        removeObjectByValue("exam", examCourseOld);

        String title = driver.getTitle();
        assertEquals("Exams", title);
        assertNull(getByUniqueValue("exam", examCourseOld));
    }

}
