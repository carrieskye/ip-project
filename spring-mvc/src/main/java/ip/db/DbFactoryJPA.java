package ip.db;

public class DbFactoryJPA {

    public static Db createDb(String type) {
        switch (type) {
            case "Classroom":
                return new ClassroomDbJPA();
            case "Course":
                return new CourseDbJPA();
            case "Exam":
                return new ExamDbJPA();
            case "Student":
                return new StudentDbJPA();
            case "Teacher":
                return new TeacherDbJPA();
        }
        return null;
    }
}
