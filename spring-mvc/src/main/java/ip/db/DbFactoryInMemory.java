package ip.db;

public class DbFactoryInMemory {

    public static Db createDb(String type) {
        switch (type) {
            case "Classroom":
                return new ClassroomDbInMemory();
            case "Course":
                return new CourseDbInMemory();
            case "Exam":
                return new ExamDbInMemory();
            case "Student":
                return new StudentDbInMemory();
            case "Teacher":
                return new TeacherDbInMemory();
        }
        return null;
    }
}
