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

public class StudentIT extends ObjectIT {
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

        driver.get("http://localhost:8080/IP/student.htm");
        fillOutField("username", "admin");
        fillOutField("password", "adminpw345?");
        driver.findElement(By.id("login")).click();

        driver.get("http://localhost:8080/IP/student.htm");
        records = driver.findElements(By.cssSelector("table tr")).size() - 2;
        addObject("student", personFields(studentNumberOld, studentFirstNameOld, studentLastNameOld), personSelects());
    }

    @After
    public void clean() throws Exception {
        if (getByUniqueValue("student", studentNumberOld) != null) {
            removeObjectByValue("student", studentNumberOld);
        }

        driver.get("http://localhost:8080/IP/student.htm");
        if (driver.findElements(By.cssSelector("table tr")).size() - 2 != records) {
            throw new Exception("State of db could not be recovered.");
        }
        driver.get("http://localhost:8080/IP/logout");
        driver.quit();
    }

    @Test
    public void addStudent_with_correct_parameters() {
        addObject("student", personFields(studentNumberNew, studentFirstNameNew, studentLastNameNew), personSelects());

        assertEquals("Students", driver.getTitle());
        assertNotNull(getByUniqueValue("student", studentNumberNew));

        removeObjectByValue("student", studentNumberNew);
    }


    @Test
    public void addStudent_with_empty_number() {
        addObject("student", personFields("", studentFirstNameNew, studentLastNameNew), personSelects());

        assertEquals("Add student", driver.getTitle());
        assertEquals("Please enter a student number.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals("", driver.findElement(By.id("number")).getAttribute("value"));
        assertEquals(studentFirstNameNew, driver.findElement(By.id("firstName")).getAttribute("value"));
        assertEquals(studentLastNameNew, driver.findElement(By.id("lastName")).getAttribute("value"));
    }

    @Test
    public void addStudent_with_empty_first_name() {
        addObject("student", personFields(studentNumberNew, "", studentLastNameNew), personSelects());

        assertEquals("Add student", driver.getTitle());
        assertEquals("Please enter a first name.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(studentNumberNew, driver.findElement(By.id("number")).getAttribute("value"));
        assertEquals("", driver.findElement(By.id("firstName")).getAttribute("value"));
        assertEquals(studentLastNameNew, driver.findElement(By.id("lastName")).getAttribute("value"));
    }

    @Test
    public void addStudent_with_empty_last_name() {
        addObject("student", personFields(studentNumberNew, studentFirstNameNew, ""), personSelects());

        assertEquals("Add student", driver.getTitle());
        assertEquals("Please enter a last name.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(studentNumberNew, driver.findElement(By.id("number")).getAttribute("value"));
        assertEquals(studentFirstNameNew, driver.findElement(By.id("firstName")).getAttribute("value"));
        assertEquals("", driver.findElement(By.id("lastName")).getAttribute("value"));
    }

    @Test
    public void updateStudent_with_correct_parameters() {
        WebElement student = getByUniqueValue("student", studentNumberOld);
        assertNotNull(student);
        student.findElement(By.linkText(studentNumberOld)).click();

        assertEquals("Update student", driver.getTitle());
        fillOutField("firstName", studentFirstNameNew);
        fillOutField("lastName", studentLastNameNew);
        driver.findElement(By.id("save")).click();

        student = getByUniqueValue("student", studentNumberOld);
        assertNotNull(student);
        assertTrue(student.getText().contains(studentFirstNameNew) && student.getText().contains(studentLastNameNew));
    }

    @Test
    public void updateStudent_with_empty_first_name() {
        WebElement student = getByUniqueValue("student", studentNumberOld);
        assertNotNull(student);
        student.findElement(By.linkText(studentNumberOld)).click();

        WebElement location = driver.findElement(By.id("firstName"));
        location.clear();
        driver.findElement(By.id("save")).click();

        assertEquals("Update student", driver.getTitle());
        assertEquals("Please enter a first name.", driver.findElement(By.cssSelector(".has-error")).getText());
        assertEquals(studentNumberOld, driver.findElement(By.id("number")).getAttribute("value"));
        assertEquals("", driver.findElement(By.id("firstName")).getAttribute("value"));
        assertEquals(studentLastNameOld, driver.findElement(By.id("lastName")).getAttribute("value"));
    }

    @Test
    public void removeStudent_removes_student_from_overview() {
        removeObjectByValue("student", studentNumberOld);

        assertEquals("Students", driver.getTitle());
        assertNull(getByUniqueValue("student", studentNumberOld));
    }

}
