package ip.web.config;

import ip.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    private final String dbType = "JPA";

    @Bean
    public ClassroomService classroomService() {
        return new ClassroomService(dbType);
    }

    @Bean
    public CourseService courseService() {
        return new CourseService(dbType);
    }

    @Bean
    public ExamService examService() {
        return new ExamService(dbType);
    }

    @Bean
    public StudentService studentService() {
        return new StudentService(dbType);
    }

    @Bean
    public TeacherService teacherService() {
        return new TeacherService(dbType);
    }

}
