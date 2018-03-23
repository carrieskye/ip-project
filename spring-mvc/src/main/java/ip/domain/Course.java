package ip.domain;

import java.util.ArrayList;

public class Course {
    private long id;
    private String code;
    private String name;
    private Teacher teacher;
    private ArrayList<Exam> exams;
    private ArrayList<Student> students;

    public Course(){

    }

    public Course(long id, String code, String name, Teacher teacher) {
        setId(id);
        setCode(code);
        setName(name);
        setTeacher(teacher);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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