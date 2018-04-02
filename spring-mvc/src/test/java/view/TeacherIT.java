package view;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.*;

public class TeacherIT extends ObjectIT {
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

        driver.get("http://localhost:8080/IP/teacher.htm");
        records = driver.findElements(By.cssSelector("table tr")).size() - 2;
        addObject("teacher", personFields(teacherNumberOld, teacherFirstNameOld, teacherLastNameOld), personSelects());
    }

    @After
    public void clean() throws Exception {
        if (getByUniqueValue("teacher", teacherNumberOld) != null) {
            removeObjectByValue("teacher", teacherNumberOld);
        }

        driver.get("http://localhost:8080/IP/teacher.htm");
        if (driver.findElements(By.cssSelector("table tr")).size() - 2 != records) {
            throw new Exception("State of db could not be recovered.");
        }
        driver.quit();
    }

    @Test
    public void addTeacher_with_correct_parameters() {
        addObject("teacher", personFields(teacherNumberNew, teacherFirstNameNew, teacherLastNameNew), personSelects());

        assertEquals("Teachers", driver.getTitle());
        assertNotNull(getByUniqueValue("teacher", teacherNumberNew));

        removeObjectByValue("teacher", teacherNumberNew);
    }


    @Test
    public void addTeacher_with_empty_number() {
        addObject("teacher", personFields("", teacherFirstNameNew, teacherLastNameNew), personSelects());

        assertEquals("Add teacher", driver.getTitle());
        assertEquals("Please enter a teacher number.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals("", driver.findElement(By.id("number")).getAttribute("value"));
        assertEquals(teacherFirstNameNew, driver.findElement(By.id("firstName")).getAttribute("value"));
        assertEquals(teacherLastNameNew, driver.findElement(By.id("lastName")).getAttribute("value"));
    }

    @Test
    public void addTeacher_with_empty_first_name() {
        addObject("teacher", personFields(teacherNumberNew, "", teacherLastNameNew), personSelects());

        assertEquals("Add teacher", driver.getTitle());
        assertEquals("Please enter a first name.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(teacherNumberNew, driver.findElement(By.id("number")).getAttribute("value"));
        assertEquals("", driver.findElement(By.id("firstName")).getAttribute("value"));
        assertEquals(teacherLastNameNew, driver.findElement(By.id("lastName")).getAttribute("value"));
    }

    @Test
    public void addTeacher_with_empty_last_name() {
        addObject("teacher", personFields(teacherNumberNew, teacherFirstNameNew, ""), personSelects());

        assertEquals("Add teacher", driver.getTitle());
        assertEquals("Please enter a last name.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(teacherNumberNew, driver.findElement(By.id("number")).getAttribute("value"));
        assertEquals(teacherFirstNameNew, driver.findElement(By.id("firstName")).getAttribute("value"));
        assertEquals("", driver.findElement(By.id("lastName")).getAttribute("value"));
    }

    @Test
    public void updateTeacher_with_correct_parameters() {
        WebElement teacher = getByUniqueValue("teacher", teacherNumberOld);
        assertNotNull(teacher);
        teacher.findElement(By.linkText(teacherNumberOld)).click();

        assertEquals("Update teacher", driver.getTitle());
        fillOutField("firstName", teacherFirstNameNew);
        fillOutField("lastName", teacherLastNameNew);
        driver.findElement(By.id("save")).click();

        teacher = getByUniqueValue("teacher", teacherNumberOld);
        assertNotNull(teacher);
        assertTrue(teacher.getText().contains(teacherFirstNameNew) && teacher.getText().contains(teacherLastNameNew));
    }

    @Test
    public void updateTeacher_with_empty_first_name() {
        WebElement teacher = getByUniqueValue("teacher", teacherNumberOld);
        assertNotNull(teacher);
        teacher.findElement(By.linkText(teacherNumberOld)).click();

        WebElement location = driver.findElement(By.id("firstName"));
        location.clear();
        driver.findElement(By.id("save")).click();

        assertEquals("Update teacher", driver.getTitle());
        assertEquals("Please enter a first name.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(teacherNumberOld, driver.findElement(By.id("number")).getAttribute("value"));
        assertEquals("", driver.findElement(By.id("firstName")).getAttribute("value"));
        assertEquals(teacherLastNameOld, driver.findElement(By.id("lastName")).getAttribute("value"));
    }

    @Test
    public void removeTeacher_removes_teacher_from_overview() {
        removeObjectByValue("teacher", teacherNumberOld);

        assertEquals("Teachers", driver.getTitle());
        assertNull(getByUniqueValue("teacher", teacherNumberOld));
    }

    @Test
    public void removeTeacher_does_not_remove_if_course_with_teacher_exists() {
        addObject("course", courseFields(courseCodeOld, courseNameOld), courseSelects(courseTeacherOld));

        WebElement teacher = getByUniqueValue("teacher", teacherNumberOld);
        if (teacher != null) {
            teacher.findElement(By.linkText("Remove")).click();
        }

        assertEquals("Remove teacher", driver.getTitle());
        assertTrue(driver.getPageSource().contains(teacherNumberOld));

        removeObjectByValue("course", courseCodeOld);
    }


}
