package ip.domain;

import java.util.ArrayList;

public class Course {
    private String code;
    private String name;
    private Teacher teacher;
    private ArrayList<Exam> exams;
    private ArrayList<Student> students;

    public Course(String code, String name, Teacher teacher) {
        setCode(code);
        setName(name);
        setTeacher(teacher);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (code.isEmpty()){
            throw new DomainException("No code given");
        }
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isEmpty()){
            throw new DomainException("No name given");
        }
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        if (teacher == null){
            throw new DomainException("No teacher given");
        }
        this.teacher = teacher;
    }

}