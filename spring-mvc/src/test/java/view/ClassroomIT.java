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

        driver.get("http://localhost:8080/IP/classroom/new.htm");
        addObject("classroom", classroomFields(classroomLocationOld, classroomSeatsOld), classroomSelects(classroomTypeOld));

        driver.get("http://localhost:8080/IP/classroom.htm");
        records = driver.findElements(By.id("tr")).size();
    }

    @After
    public void clean() throws Exception {
        if (getByUniqueValue("classroom", classroomLocationOld) != null) {
            removeObjectByValue("classroom", classroomLocationOld);
        }
        driver.get("http://localhost:8080/IP/classroom.htm");
        if (driver.findElements(By.id("tr")).size() != records) {
            throw new Exception("State of db could not be recovered.");
        }
        driver.quit();
    }


    @Test
    public void addClassroom_with_correct_parameters() {
        driver.get("http://localhost:8080/IP/classroom/new.htm");
        addObject("classroom", classroomFields(classroomLocationNew, classroomSeatsNew), classroomSelects(classroomTypeNew));

        String title = driver.getTitle();
        assertEquals("Classrooms", title);
        assertNotNull(getByUniqueValue("classroom", classroomLocationNew));

        removeObjectByValue("classroom", classroomLocationNew);
    }

    @Test
    public void addClassroom_with_empty_location() {
        driver.get("http://localhost:8080/IP/classroom/new.htm");
        addObject("classroom", classroomFields("", classroomSeatsNew), classroomSelects(classroomTypeNew));

        String title = driver.getTitle();
        assertEquals("Add classroom", title);

        WebElement errorMsg = driver.findElement(By.cssSelector(".has-error"));
        assertEquals("Please enter a classroom location.", errorMsg.getText());

        WebElement fieldLocation = driver.findElement(By.id("location"));
        assertEquals("", fieldLocation.getAttribute("value"));

        WebElement fieldSeats = driver.findElement(By.id("seats"));
        assertEquals(String.valueOf(classroomSeatsNew), fieldSeats.getAttribute("value"));

        Select selectType = new Select(driver.findElement(By.id("type")));
        assertEquals(classroomTypeNew, selectType.getFirstSelectedOption().getAttribute("value"));
    }

    @Test
    public void addClassroom_with_zero_seats() {
        driver.get("http://localhost:8080/IP/classroom/new.htm");
        addObject("classroom", classroomFields(classroomLocationNew, 0), classroomSelects(classroomTypeNew));

        String title = driver.getTitle();
        assertEquals("Add classroom", title);

        WebElement errorMsg = driver.findElement(By.cssSelector(".has-error"));
        assertEquals("Please enter a positive number of seats.", errorMsg.getText());

        WebElement fieldLocation = driver.findElement(By.id("location"));
        assertEquals(classroomLocationNew, fieldLocation.getAttribute("value"));

        WebElement fieldSeats = driver.findElement(By.id("seats"));
        assertEquals("0", fieldSeats.getAttribute("value"));

        Select selectType = new Select(driver.findElement(By.id("type")));
        assertEquals(classroomTypeNew, selectType.getFirstSelectedOption().getAttribute("value"));
    }

    @Test
    public void updateClassroom_with_correct_parameters() {
        driver.get("http://localhost:8080/IP/classroom.htm");
        WebElement classroom = getByUniqueValue("classroom", classroomLocationOld);
        assertNotNull(classroom);
        classroom.findElement(By.linkText(classroomLocationOld)).click();

        String title = driver.getTitle();
        assertEquals("Update classroom", title);
        fillOutField("seats", String.valueOf(classroomSeatsNew));
        driver.findElement(By.id("save")).click();

        classroom = getByUniqueValue("classroom", classroomLocationOld);
        assertNotNull(classroom);
        assertTrue(classroom.getText().contains(String.valueOf(classroomSeatsNew)));
    }

    @Test
    public void addClassroom_with_empty_type() {
        driver.get("http://localhost:8080/IP/classroom/new.htm");
        addObject("classroom", classroomFields(classroomLocationNew, classroomSeatsNew), classroomSelects("Select type"));

        String title = driver.getTitle();
        assertEquals("Add classroom", title);

        WebElement errorMsg = driver.findElement(By.cssSelector(".has-error"));
        assertEquals("Please select a classroom type.", errorMsg.getText());

        WebElement fieldLocation = driver.findElement(By.id("location"));
        assertEquals(classroomLocationNew, fieldLocation.getAttribute("value"));

        WebElement fieldSeats = driver.findElement(By.id("seats"));
        assertEquals(String.valueOf(classroomSeatsNew), fieldSeats.getAttribute("value"));

        Select selectType = new Select(driver.findElement(By.id("type")));
        assertEquals("", selectType.getFirstSelectedOption().getAttribute("value"));
    }

    @Test
    public void updateClassroom_with_empty_location() {
        driver.get("http://localhost:8080/IP/classroom.htm");
        WebElement classroom = getByUniqueValue("classroom", classroomLocationOld);
        assertNotNull(classroom);
        classroom.findElement(By.linkText(classroomLocationOld)).click();

        WebElement location = driver.findElement(By.id("location"));
        location.clear();
        driver.findElement(By.id("save")).click();

        String title = driver.getTitle();
        assertEquals("Update classroom", title);

        WebElement errorMsg = driver.findElement(By.cssSelector(".has-error"));
        assertEquals("Please enter a classroom location.", errorMsg.getText());

        WebElement fieldLocation = driver.findElement(By.id("location"));
        assertEquals("", fieldLocation.getAttribute("value"));

        WebElement fieldSeats = driver.findElement(By.id("seats"));
        assertEquals(String.valueOf(classroomSeatsOld), fieldSeats.getAttribute("value"));

        Select selectType = new Select(driver.findElement(By.id("type")));
        assertEquals(classroomTypeOld, selectType.getFirstSelectedOption().getAttribute("value"));
    }

    @Test
    public void removeClassroom_removes_classroom_from_overview() {
        driver.get("http://localhost:8080/IP/classroom.htm");
        removeObjectByValue("classroom", classroomLocationOld);

        String title = driver.getTitle();
        assertEquals("Classrooms", title);
        assertNull(getByUniqueValue("classroom", classroomLocationOld));
    }

    @Test
    public void removeClassroom_does_not_remove_if_exam_in_classroom_exists() {
        addObject("teacher", personFields(teacherNumberOld, teacherFirstNameOld, teacherLastNameOld), personSelects());
        addObject("course", courseFields(courseCodeOld, courseNameOld), courseSelects(courseTeacherOld));
        addObject("exam", examFields(examDate, examBegin, examEnd), examSelects(examCourse, examClassroom));

        driver.get("http://localhost:8080/IP/classroom.htm");
        WebElement classroom = getByUniqueValue("classroom", classroomLocationOld);
        if (classroom != null) {
            classroom.findElement(By.linkText("Remove")).click();
        }

        String title = driver.getTitle();
        assertEquals("Remove classroom", title);

        assertTrue(driver.getPageSource().contains("exam"));

        removeObjectByValue("exam", courseNameOld);
        removeObjectByValue("course", courseCodeOld);
        removeObjectByValue("teacher", teacherNumberOld);
    }


}
