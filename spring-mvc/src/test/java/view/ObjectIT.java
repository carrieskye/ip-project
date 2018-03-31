package view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ObjectIT {
    private WebDriver driver;
    static String classroomLocationOld, classroomTypeOld, classroomLocationNew, classroomTypeNew;
    static int classroomSeatsOld, classroomSeatsNew;
    static String courseCodeOld, courseNameOld, courseTeacherOld;
    //static String courseCodeNew, courseNameNew, courseTeacherNew;
    static String examCourse, examDate, examBegin, examEnd, examClassroom;
    static String studentNumberOld, studentFirstNameOld, studentLastNameOld, studentNumberNew, studentFirstNameNew, studentLastNameNew;
    static String teacherNumberOld, teacherFirstNameOld, teacherLastNameOld, teacherNumberNew, teacherFirstNameNew, teacherLastNameNew;

    static void initialize() {
        setUpStudent();
        setUpTeacher();
        setUpClassroom();
        setUpCourse();
        setUpExam();
    }

    public void setUp(WebDriver driver) {
        this.driver = driver;
    }

    private static void setUpClassroom() {
        classroomLocationOld = "Jamaica";
        classroomSeatsOld = 40;
        classroomTypeOld = "PC";

        classroomLocationNew = "New York";
        classroomSeatsNew = 100;
        classroomTypeNew = "Aula";
    }

    private static void setUpCourse() {
        courseCodeOld = "MBX00X";
        courseNameOld = "English VII";
        courseTeacherOld = teacherFirstNameOld + " " + teacherLastNameOld + " (" + teacherNumberOld + ")";

        //courseCodeNew = "MBX20X";
        //courseNameNew = "French XI";
        //courseTeacherNew = teacherFirstNameNew + " " + teacherLastNameNew + " (" + teacherNumberNew + ")";
    }

    private static void setUpExam() {
        examCourse = courseCodeOld + " (" + courseNameOld + ")";
        examDate = "14/06/2020";
        examBegin = "09:00";
        examEnd = "13:00";
        examClassroom = classroomLocationOld + " (" + classroomSeatsOld + " seats, " + classroomTypeOld + ")";
    }

    private static void setUpStudent() {
        studentNumberOld = "r1111111";
        studentFirstNameOld = "FName1";
        studentLastNameOld = "lName1";

        studentNumberNew = "r3333333";
        studentFirstNameNew = "FName3";
        studentLastNameNew = "LName3";
    }

    private static void setUpTeacher() {
        teacherNumberOld = "u1111111";
        teacherFirstNameOld = "FName1";
        teacherLastNameOld = "lName1";

        teacherNumberNew = "u3333333";
        teacherFirstNameNew = "FName3";
        teacherLastNameNew = "LName3";
    }

    void fillOutField(String name, String value) {
        WebElement field = driver.findElement(By.id(name));
        if (field.getAttribute("type").equals("text") || field.getAttribute("type").equals("number")) {
            field.clear();
        }
        field.sendKeys(value);
    }

    private void selectField(String name, String value) {
        Select select = new Select(driver.findElement(By.id(name)));
        select.selectByVisibleText(value);
    }

    void addObject(String type, HashMap<String, String> fields, HashMap<String, String> selects) {
        driver.get("http://localhost:8080/IP/" + type + "/new.htm");

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            fillOutField(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, String> entry : selects.entrySet()) {
            selectField(entry.getKey(), entry.getValue());
        }

        WebElement button = driver.findElement(By.id("save"));
        button.click();
    }

    void removeObjectByValue(String type, String value) {
        driver.get("http://localhost:8080/IP/" + type + ".htm");
        WebElement object = getByUniqueValue(type, value);
        if (object != null) {
            object.findElement(By.linkText("Remove")).click();
        }
        driver.findElement(By.linkText("OK")).click();
    }

    WebElement getByUniqueValue(String type, String value) {
        driver.get("http://localhost:8080/IP/" + type + ".htm");
        ArrayList<WebElement> listItems = (ArrayList<WebElement>) driver.findElements(By.cssSelector("table tr"));
        for (WebElement listItem : listItems) {
            if (listItem.getText().contains(value)) {
                return listItem;
            }
        }
        return null;
    }

    HashMap<String, String> personFields(String number, String firstName, String lastName) {
        HashMap<String, String> values = new HashMap<>();
        values.put("number", number);
        values.put("firstName", firstName);
        values.put("lastName", lastName);
        return values;
    }

    HashMap<String, String> personSelects() {
        return new HashMap<>();
    }

    HashMap<String, String> courseFields(String code, String name) {
        HashMap<String, String> values = new HashMap<>();
        values.put("code", code);
        values.put("name", name);
        return values;
    }

    HashMap<String, String> courseSelects(String teacher) {
        HashMap<String, String> values = new HashMap<>();
        values.put("teacher", teacher);
        return values;
    }

    HashMap<String, String> classroomFields(String location, int seats) {
        HashMap<String, String> values = new HashMap<>();
        values.put("location", location);
        values.put("seats", String.valueOf(seats));
        return values;
    }

    HashMap<String, String> classroomSelects(String type) {
        HashMap<String, String> values = new HashMap<>();
        values.put("type", type);
        return values;
    }

    HashMap<String, String> examFields(String date, String begin, String end) {
        HashMap<String, String> values = new HashMap<>();
        values.put("date", date);
        values.put("begin", begin);
        values.put("end", end);
        return values;
    }

    HashMap<String, String> examSelects(String course, String classroom) {
        HashMap<String, String> values = new HashMap<>();
        values.put("course", course);
        values.put("classroom", classroom);
        return values;
    }
}
