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

import java.util.HashMap;

import static org.junit.Assert.*;

public class CourseIT extends ObjectIT {
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

        driver.get("http://localhost:8080/IP/course.htm");
        records = driver.findElements(By.cssSelector("table tr")).size() - 2;

        addObject("teacher", personFields(teacherNumberOld, teacherFirstNameOld, teacherLastNameOld), personSelects());
        addObject("teacher", personFields(teacherNumberNew, teacherFirstNameNew, teacherLastNameNew), personSelects());
        addObject("course", courseFields(courseCodeOld, courseNameOld), courseSelects(courseTeacherOld));
    }

    @After
    public void clean() throws Exception {
        if (getByUniqueValue("course", courseCodeOld) != null) {
            removeObjectByValue("course", courseCodeOld);
        }
        removeObjectByValue("teacher", teacherNumberOld);
        removeObjectByValue("teacher", teacherNumberNew);

        driver.get("http://localhost:8080/IP/course.htm");
        if (driver.findElements(By.cssSelector("table tr")).size() - 2 != records) {
            throw new Exception("State of db could not be recovered.");
        }
        driver.quit();
    }


    @Test
    public void addCourse_with_correct_parameters() {
        addObject("course", courseFields(courseCodeNew, courseNameNew), courseSelects(courseTeacherNew));

        assertEquals("Courses", driver.getTitle());
        assertNotNull(getByUniqueValue("course", courseCodeNew));

        removeObjectByValue("course", courseCodeNew);
    }

    @Test
    public void addCourse_with_empty_code() {
        addObject("course", courseFields("", courseNameNew), courseSelects(courseTeacherNew));

        assertEquals("Add course", driver.getTitle());
        assertEquals("Please enter a course code.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals("", driver.findElement(By.id("code")).getAttribute("value"));
        assertEquals(courseNameNew, driver.findElement(By.id("name")).getAttribute("value"));
        assertEquals(courseTeacherNew, new Select(driver.findElement(By.id("teacher"))).getFirstSelectedOption().getAttribute("label"));
    }

    @Test
    public void addCourse_with_empty_name() {
        addObject("course", courseFields(courseCodeNew, ""), courseSelects(courseTeacherNew));

        assertEquals("Add course", driver.getTitle());
        assertEquals("Please enter a course name.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(courseCodeNew, driver.findElement(By.id("code")).getAttribute("value"));
        assertEquals("", driver.findElement(By.id("name")).getAttribute("value"));
        assertEquals(courseTeacherNew, new Select(driver.findElement(By.id("teacher"))).getFirstSelectedOption().getAttribute("label"));
    }

    @Test
    public void addCourse_without_teacher() {
        addObject("course", courseFields(courseCodeNew, courseNameNew), new HashMap<>());

        assertEquals("Add course", driver.getTitle());
        assertEquals("Please enter a course teacher.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(courseCodeNew, driver.findElement(By.id("code")).getAttribute("value"));
        assertEquals(courseNameNew, driver.findElement(By.id("name")).getAttribute("value"));

        Select selectTeacher = new Select(driver.findElement(By.id("teacher")));
        assertEquals("0", selectTeacher.getFirstSelectedOption().getAttribute("value"));
        assertEquals("Select teacher", selectTeacher.getFirstSelectedOption().getAttribute("label"));
    }

    @Test
    public void updateCourse_with_correct_parameters() {
        WebElement course = getByUniqueValue("course", courseCodeOld);
        assertNotNull(course);
        course.findElement(By.linkText(courseCodeOld)).click();

        assertEquals("Update course", driver.getTitle());
        fillOutField("name", courseNameNew);
        driver.findElement(By.id("save")).click();

        course = getByUniqueValue("course", courseCodeOld);
        assertNotNull(course);
        assertTrue(course.getText().contains(courseNameNew));
    }

    @Test
    public void updateCourse_with_empty_name() {
        WebElement course = getByUniqueValue("course", courseCodeOld);
        assertNotNull(course);
        course.findElement(By.linkText(courseCodeOld)).click();

        WebElement name = driver.findElement(By.id("name"));
        name.clear();
        driver.findElement(By.id("save")).click();

        assertEquals("Update course", driver.getTitle());
        assertEquals("Please enter a course name.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(courseCodeOld, driver.findElement(By.id("code")).getAttribute("value"));
        assertEquals("", driver.findElement(By.id("name")).getAttribute("value"));
        assertEquals(courseTeacherOld, new Select(driver.findElement(By.id("teacher"))).getFirstSelectedOption().getAttribute("label"));
    }

    @Test
    public void removeCourse_removes_course_from_overview() {
        removeObjectByValue("course", courseCodeOld);

        assertEquals("Courses", driver.getTitle());
        assertNull(getByUniqueValue("course", courseCodeOld));
    }

    @Test
    public void removeCourse_does_not_remove_if_exam_for_course_exists() {
        addObject("classroom", classroomFields(classroomLocationOld, classroomSeatsOld), classroomSelects(classroomTypeOld));
        addObject("exam", examFields(examDateOld, examBeginTimeOld, examEndTimeOld), examSelects(examCourseOld, examClassroomOld));

        WebElement course = getByUniqueValue("course", courseCodeOld);
        if (course != null) {
            course.findElement(By.linkText("Remove")).click();
        }

        assertEquals("Remove course", driver.getTitle());
        assertTrue(driver.getPageSource().contains("exam"));

        removeObjectByValue("exam", examCourseOld);
        removeObjectByValue("classroom", classroomLocationOld);
    }


}
