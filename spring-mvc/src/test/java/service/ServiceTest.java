package service;

import ip.domain.*;
import ip.service.*;
import java.time.LocalDate;
import java.time.LocalTime;

class ServiceTest {
    static String dbType = "JPA";
    static ClassroomService classroomService;
    static CourseService courseService;
    static ExamService examService;
    static StudentService studentService;
    static TeacherService teacherService;

    static Classroom classroomOld, classroomNew;
    static Course courseOld = new Course(), courseNew = new Course();
    static Exam examOld, examNew;
    static Student studentOld, studentNew;
    static Teacher teacherOld, teacherNew;

    static void initialize() {
        initializeClassrooms();
        initializeCourses();
        initializeExams();
        initializeStudents();
        initializeTeachers();
    }

    private static void initializeClassrooms() {
        classroomService = new ClassroomService(dbType);
        classroomOld = new Classroom("Jamaica", 40, "PC");
        classroomNew = new Classroom("New York", 100, "Aula");
    }

    private static void initializeCourses() {
        courseService = new CourseService(dbType);
        courseOld.setCode("MBX00X");
        courseOld.setName("English VII");
        courseNew.setCode("MBX20X");
        courseNew.setName("French XI");
    }

    private static void initializeExams() {
        examService = new ExamService(dbType);
        examOld = new Exam();
        examOld.setDate(LocalDate.of(2120, 6, 14));
        examOld.setBeginTime(LocalTime.of(9, 0));
        examOld.setEndTime(examOld.getBeginTime().plusHours(3));

        examNew = new Exam();
        examNew.setDate(LocalDate.of(2120, 6, 20));
        examNew.setBeginTime(LocalTime.of(9, 0));
        examNew.setEndTime(examNew.getBeginTime().plusHours(3));
    }

    private static void initializeStudents() {
        studentService = new StudentService(dbType);
        studentOld = new Student("r1111111", "Mark", "Goossens");
        studentNew = new Student("r3333333", "Jan", "Holemans");
    }

    private static void initializeTeachers() {
        teacherService = new TeacherService(dbType);
        teacherOld = new Teacher("u1111111", "Laura", "Peeters");
        teacherNew = new Teacher("u3333333", "Jonas", "Verschueren");
    }

}
