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

        driver.get("http://localhost:8080/IP/student/new.htm");
        addObject("student", personFields(studentNumberOld, studentFirstNameOld, studentLastNameOld), personSelects());

        driver.get("http://localhost:8080/IP/student.htm");
        records = driver.findElements(By.id("tr")).size();
    }

    @After
    public void clean() throws Exception {
        if (getByUniqueValue("student", studentNumberOld) != null) {
            removeObjectByValue("student", studentNumberOld);
        }
        driver.get("http://localhost:8080/IP/student.htm");
        if (driver.findElements(By.id("tr")).size() != records) {
            throw new Exception("State of db could not be recovered.");
        }
        driver.quit();
    }

    @Test
    public void addStudent_with_correct_parameters() {
        driver.get("http://localhost:8080/IP/student/new.htm");
        addObject("student", personFields(studentNumberNew, studentFirstNameNew, studentLastNameNew), personSelects());

        String title = driver.getTitle();
        assertEquals("Students", title);
        assertNotNull(getByUniqueValue("student", studentNumberNew));

        removeObjectByValue("student", studentNumberNew);
    }


    @Test
    public void addStudent_with_empty_number() {
        driver.get("http://localhost:8080/IP/student/new.htm");
        addObject("student", personFields("", studentFirstNameNew, studentLastNameNew), personSelects());

        String title = driver.getTitle();
        assertEquals("Add student", title);

        WebElement errorMsg = driver.findElement(By.cssSelector(".has-error"));
        assertEquals("Please enter a student number.", errorMsg.getText());

        WebElement fieldNumber = driver.findElement(By.id("number"));
        assertEquals("", fieldNumber.getAttribute("value"));

        WebElement fieldFirstName = driver.findElement(By.id("firstName"));
        assertEquals(studentFirstNameNew, fieldFirstName.getAttribute("value"));

        WebElement fieldLastName = driver.findElement(By.id("lastName"));
        assertEquals(studentLastNameNew, fieldLastName.getAttribute("value"));
    }

    @Test
    public void addStudent_with_empty_first_name() {
        driver.get("http://localhost:8080/IP/student/new.htm");
        addObject("student", personFields(studentNumberNew, "", studentLastNameNew), personSelects());

        String title = driver.getTitle();
        assertEquals("Add student", title);

        WebElement errorMsg = driver.findElement(By.cssSelector(".has-error"));
        assertEquals("Please enter a first name.", errorMsg.getText());

        WebElement fieldNumber = driver.findElement(By.id("number"));
        assertEquals(studentNumberNew, fieldNumber.getAttribute("value"));

        WebElement fieldFirstName = driver.findElement(By.id("firstName"));
        assertEquals("", fieldFirstName.getAttribute("value"));

        WebElement fieldLastName = driver.findElement(By.id("lastName"));
        assertEquals(studentLastNameNew, fieldLastName.getAttribute("value"));
    }

    @Test
    public void addStudent_with_empty_last_name() {
        driver.get("http://localhost:8080/IP/student/new.htm");
        addObject("student", personFields(studentNumberNew, studentFirstNameNew, ""), personSelects());

        String title = driver.getTitle();
        assertEquals("Add student", title);

        WebElement errorMsg = driver.findElement(By.cssSelector(".has-error"));
        assertEquals("Please enter a last name.", errorMsg.getText());

        WebElement fieldNumber = driver.findElement(By.id("number"));
        assertEquals(studentNumberNew, fieldNumber.getAttribute("value"));

        WebElement fieldFirstName = driver.findElement(By.id("firstName"));
        assertEquals(studentFirstNameNew, fieldFirstName.getAttribute("value"));

        WebElement fieldLastName = driver.findElement(By.id("lastName"));
        assertEquals("", fieldLastName.getAttribute("value"));
    }

    @Test
    public void updateStudent_with_correct_parameters() {
        driver.get("http://localhost:8080/IP/student.htm");
        WebElement student = getByUniqueValue("student", studentNumberOld);
        assertNotNull(student);
        student.findElement(By.linkText(studentNumberOld)).click();

        String title = driver.getTitle();
        assertEquals("Update student", title);
        fillOutField("firstName", "FName5");
        fillOutField("lastName", "LName5");
        driver.findElement(By.id("save")).click();

        student = getByUniqueValue("student", studentNumberOld);
        assertNotNull(student);
        assertTrue(student.getText().contains("FName5") && student.getText().contains("LName5"));
    }

    @Test
    public void updateStudent_with_empty_first_name() {
        driver.get("http://localhost:8080/IP/student.htm");
        WebElement student = getByUniqueValue("student", studentNumberOld);
        assertNotNull(student);
        student.findElement(By.linkText(studentNumberOld)).click();

        WebElement location = driver.findElement(By.id("firstName"));
        location.clear();
        driver.findElement(By.id("save")).click();

        String title = driver.getTitle();
        assertEquals("Update student", title);

        WebElement errorMsg = driver.findElement(By.cssSelector(".has-error"));
        assertEquals("Please enter a first name.", errorMsg.getText());

        WebElement fieldNumber = driver.findElement(By.id("number"));
        assertEquals(studentNumberOld, fieldNumber.getAttribute("value"));

        WebElement fieldFirstName = driver.findElement(By.id("firstName"));
        assertEquals("", fieldFirstName.getAttribute("value"));

        WebElement fieldLastName = driver.findElement(By.id("lastName"));
        assertEquals(studentLastNameOld, fieldLastName.getAttribute("value"));
    }

    @Test
    public void removeStudent_removes_student_from_overview() {
        driver.get("http://localhost:8080/IP/student.htm");
        removeObjectByValue("student", studentNumberOld);

        String title = driver.getTitle();
        assertEquals("Students", title);
        assertNull(getByUniqueValue("student", studentNumberOld));
    }

}
