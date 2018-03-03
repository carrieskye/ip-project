package ip.db;

public class SqlDbFactory{

    public static Db createDb(String type) {
        switch (type) {
            case "Classroom":
                return new ClassroomDbSql();
            case "Course":
                //TODO
            case "Exam":
                //TODO
            case "Student":
                //TODO
            case "Teacher":
                //TODO
        }
        return null;
    }
}
