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

public class ClassroomIT extends ObjectIT {
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

        driver.get("http://localhost:8080/IP/classroom.htm");
        fillOutField("username", "admin");
        fillOutField("password", "adminpw345?");
        driver.findElement(By.id("login")).click();

        driver.get("http://localhost:8080/IP/classroom.htm");
        records = driver.findElements(By.cssSelector("table tr")).size() - 2;
        addObject("classroom", classroomFields(classroomLocationOld, classroomSeatsOld), classroomSelects(classroomTypeOld));
    }

    @After
    public void clean() throws Exception {
        if (getByUniqueValue("classroom", classroomLocationOld) != null) {
            removeObjectByValue("classroom", classroomLocationOld);
        }

        driver.get("http://localhost:8080/IP/classroom.htm");
        if (driver.findElements(By.cssSelector("table tr")).size() - 2 != records) {
            throw new Exception("State of db could not be recovered.");
        }
        driver.get("http://localhost:8080/IP/logout");
        driver.quit();
    }


    @Test
    public void addClassroom_with_correct_parameters() {
        addObject("classroom", classroomFields(classroomLocationNew, classroomSeatsNew), classroomSelects(classroomTypeNew));

        assertEquals("Classrooms", driver.getTitle());
        assertNotNull(getByUniqueValue("classroom", classroomLocationNew));

        removeObjectByValue("classroom", classroomLocationNew);
    }

    @Test
    public void addClassroom_with_empty_location() {
        addObject("classroom", classroomFields("", classroomSeatsNew), classroomSelects(classroomTypeNew));

        assertEquals("Add classroom", driver.getTitle());
        assertEquals("Please enter a classroom location.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals("", driver.findElement(By.id("location")).getAttribute("value"));
        assertEquals(String.valueOf(classroomSeatsNew), driver.findElement(By.id("seats")).getAttribute("value"));
        assertEquals(classroomTypeNew, new Select(driver.findElement(By.id("type"))).getFirstSelectedOption().getAttribute("value"));
    }

    @Test
    public void addClassroom_with_zero_seats() {
        addObject("classroom", classroomFields(classroomLocationNew, 0), classroomSelects(classroomTypeNew));

        assertEquals("Add classroom", driver.getTitle());
        assertEquals("Please enter a positive number of seats.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(classroomLocationNew, driver.findElement(By.id("location")).getAttribute("value"));
        assertEquals("0", driver.findElement(By.id("seats")).getAttribute("value"));
        assertEquals(classroomTypeNew, new Select(driver.findElement(By.id("type"))).getFirstSelectedOption().getAttribute("value"));
    }

    @Test
    public void addClassroom_without_type() {
        addObject("classroom", classroomFields(classroomLocationNew, classroomSeatsNew), new HashMap<>());

        assertEquals("Add classroom", driver.getTitle());
        assertEquals("Please select a classroom type.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(classroomLocationNew, driver.findElement(By.id("location")).getAttribute("value"));
        assertEquals(String.valueOf(classroomSeatsNew), driver.findElement(By.id("seats")).getAttribute("value"));

        Select selectType = new Select(driver.findElement(By.id("type")));
        assertEquals("", selectType.getFirstSelectedOption().getAttribute("value"));
        assertEquals("Select type", selectType.getFirstSelectedOption().getAttribute("label"));
    }

    @Test
    public void updateClassroom_with_correct_parameters() {
        WebElement classroom = getByUniqueValue("classroom", classroomLocationOld);
        assertNotNull(classroom);
        classroom.findElement(By.linkText(classroomLocationOld)).click();

        assertEquals("Update classroom", driver.getTitle());
        fillOutField("seats", String.valueOf(classroomSeatsNew));
        driver.findElement(By.id("save")).click();

        classroom = getByUniqueValue("classroom", classroomLocationOld);
        assertNotNull(classroom);
        assertTrue(classroom.getText().contains(String.valueOf(classroomSeatsNew)));
    }

    @Test
    public void updateClassroom_with_empty_location() {
        WebElement classroom = getByUniqueValue("classroom", classroomLocationOld);
        assertNotNull(classroom);
        classroom.findElement(By.linkText(classroomLocationOld)).click();

        WebElement location = driver.findElement(By.id("location"));
        location.clear();
        driver.findElement(By.id("save")).click();

        assertEquals("Update classroom", driver.getTitle());
        assertEquals("Please enter a classroom location.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals("", driver.findElement(By.id("location")).getAttribute("value"));
        assertEquals(String.valueOf(classroomSeatsOld), driver.findElement(By.id("seats")).getAttribute("value"));
        assertEquals(classroomTypeOld, new Select(driver.findElement(By.id("type"))).getFirstSelectedOption().getAttribute("value"));
    }

    @Test
    public void removeClassroom_removes_classroom_from_overview() {
        removeObjectByValue("classroom", classroomLocationOld);

        assertEquals("Classrooms", driver.getTitle());
        assertNull(getByUniqueValue("classroom", classroomLocationOld));
    }

    @Test
    public void removeClassroom_does_not_remove_if_exam_in_classroom_exists() {
        addObject("teacher", personFields(teacherNumberOld, teacherFirstNameOld, teacherLastNameOld), personSelects());
        addObject("course", courseFields(courseCodeOld, courseNameOld), courseSelects(courseTeacherOld));
        addObject("exam", examFields(examDateOld, examBeginTimeOld, examEndTimeOld), examSelects(examCourseOld, examClassroomOld));

        WebElement classroom = getByUniqueValue("classroom", classroomLocationOld);
        if (classroom != null) {
            classroom.findElement(By.linkText("Remove")).click();
        }

        assertEquals("Remove classroom", driver.getTitle());
        assertTrue(driver.getPageSource().contains("exam"));

        removeObjectByValue("exam", examCourseOld);
        removeObjectByValue("course", courseCodeOld);
        removeObjectByValue("teacher", teacherNumberOld);
    }


}
