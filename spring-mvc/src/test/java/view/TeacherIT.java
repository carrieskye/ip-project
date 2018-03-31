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

        driver.get("http://localhost:8080/IP/teacher/new.htm");
        addObject("teacher", personFields(teacherNumberOld, teacherFirstNameOld, teacherLastNameOld), personSelects());

        driver.get("http://localhost:8080/IP/teacher.htm");
        records = driver.findElements(By.id("tr")).size();
    }

    @After
    public void clean() throws Exception {
        driver.get("http://localhost:8080/IP/teacher.htm");
        if (getByUniqueValue("teacher", teacherNumberOld) != null) {
            removeObjectByValue("teacher", teacherNumberOld);
        }

        driver.get("http://localhost:8080/IP/teacher.htm");
        if (driver.findElements(By.id("tr")).size() != records) {
            throw new Exception("State of db could not be recovered.");
        }
        driver.quit();
    }

    @Test
    public void addTeacher_with_correct_parameters() {
        driver.get("http://localhost:8080/IP/teacher/new.htm");
        addObject("teacher", personFields(teacherNumberNew, teacherFirstNameNew, teacherLastNameNew), personSelects());

        String title = driver.getTitle();
        assertEquals("Teachers", title);
        assertNotNull(getByUniqueValue("teacher", teacherNumberNew));

        removeObjectByValue("teacher", teacherNumberNew);
    }


    @Test
    public void addTeacher_with_empty_number() {
        driver.get("http://localhost:8080/IP/teacher/new.htm");
        addObject("teacher", personFields("", teacherFirstNameNew, teacherLastNameNew), personSelects());

        String title = driver.getTitle();
        assertEquals("Add teacher", title);

        WebElement errorMsg = driver.findElement(By.cssSelector(".has-error"));
        assertEquals("Please enter a teacher number.", errorMsg.getText());

        WebElement fieldNumber = driver.findElement(By.id("number"));
        assertEquals("", fieldNumber.getAttribute("value"));

        WebElement fieldFirstName = driver.findElement(By.id("firstName"));
        assertEquals(teacherFirstNameNew, fieldFirstName.getAttribute("value"));

        WebElement fieldLastName = driver.findElement(By.id("lastName"));
        assertEquals(teacherLastNameNew, fieldLastName.getAttribute("value"));
    }

    @Test
    public void addTeacher_with_empty_first_name() {
        driver.get("http://localhost:8080/IP/teacher/new.htm");
        addObject("teacher", personFields(teacherNumberNew, "", teacherLastNameNew), personSelects());

        String title = driver.getTitle();
        assertEquals("Add teacher", title);

        WebElement errorMsg = driver.findElement(By.cssSelector(".has-error"));
        assertEquals("Please enter a first name.", errorMsg.getText());

        WebElement fieldNumber = driver.findElement(By.id("number"));
        assertEquals(teacherNumberNew, fieldNumber.getAttribute("value"));

        WebElement fieldFirstName = driver.findElement(By.id("firstName"));
        assertEquals("", fieldFirstName.getAttribute("value"));

        WebElement fieldLastName = driver.findElement(By.id("lastName"));
        assertEquals(teacherLastNameNew, fieldLastName.getAttribute("value"));
    }

    @Test
    public void addTeacher_with_empty_last_name() {
        driver.get("http://localhost:8080/IP/teacher/new.htm");
        addObject("teacher", personFields(teacherNumberNew, teacherFirstNameNew, ""), personSelects());

        String title = driver.getTitle();
        assertEquals("Add teacher", title);

        WebElement errorMsg = driver.findElement(By.cssSelector(".has-error"));
        assertEquals("Please enter a last name.", errorMsg.getText());

        WebElement fieldNumber = driver.findElement(By.id("number"));
        assertEquals(teacherNumberNew, fieldNumber.getAttribute("value"));

        WebElement fieldFirstName = driver.findElement(By.id("firstName"));
        assertEquals(teacherFirstNameNew, fieldFirstName.getAttribute("value"));

        WebElement fieldLastName = driver.findElement(By.id("lastName"));
        assertEquals("", fieldLastName.getAttribute("value"));
    }

    @Test
    public void updateTeacher_with_correct_parameters() {
        driver.get("http://localhost:8080/IP/teacher.htm");
        WebElement teacher = getByUniqueValue("teacher", teacherNumberOld);
        assertNotNull(teacher);
        teacher.findElement(By.linkText(teacherNumberOld)).click();

        String title = driver.getTitle();
        assertEquals("Update teacher", title);
        fillOutField("firstName", "FName5");
        fillOutField("lastName", "LName5");
        driver.findElement(By.id("save")).click();

        teacher = getByUniqueValue("teacher", teacherNumberOld);
        assertNotNull(teacher);
        assertTrue(teacher.getText().contains("FName5") && teacher.getText().contains("LName5"));
    }

    @Test
    public void updateTeacher_with_empty_first_name() {
        driver.get("http://localhost:8080/IP/teacher.htm");
        WebElement teacher = getByUniqueValue("teacher", teacherNumberOld);
        assertNotNull(teacher);
        teacher.findElement(By.linkText(teacherNumberOld)).click();

        WebElement location = driver.findElement(By.id("firstName"));
        location.clear();
        driver.findElement(By.id("save")).click();

        String title = driver.getTitle();
        assertEquals("Update teacher", title);

        WebElement errorMsg = driver.findElement(By.cssSelector(".has-error"));
        assertEquals("Please enter a first name.", errorMsg.getText());

        WebElement fieldNumber = driver.findElement(By.id("number"));
        assertEquals(teacherNumberOld, fieldNumber.getAttribute("value"));

        WebElement fieldFirstName = driver.findElement(By.id("firstName"));
        assertEquals("", fieldFirstName.getAttribute("value"));

        WebElement fieldLastName = driver.findElement(By.id("lastName"));
        assertEquals(teacherLastNameOld, fieldLastName.getAttribute("value"));
    }

    @Test
    public void removeTeacher_removes_teacher_from_overview() {
        driver.get("http://localhost:8080/IP/teacher.htm");
        removeObjectByValue("teacher", teacherNumberOld);

        String title = driver.getTitle();
        assertEquals("Teachers", title);
        assertNull(getByUniqueValue("teacher", teacherNumberOld));
    }

    @Test
    public void removeTeacher_does_not_remove_if_course_with_teacher_exists() {
        addObject("course", courseFields(courseCodeOld, courseNameOld), courseSelects(courseTeacherOld));

        driver.get("http://localhost:8080/IP/teacher.htm");
        WebElement teacher = getByUniqueValue("teacher", teacherNumberOld);
        if (teacher != null) {
            teacher.findElement(By.linkText("Remove")).click();
        }

        String title = driver.getTitle();
        assertEquals("Remove teacher", title);

        assertTrue(driver.getPageSource().contains(teacherNumberOld));

        removeObjectByValue("course", courseCodeOld);
    }


}
