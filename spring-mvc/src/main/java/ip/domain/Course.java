package ip.domain;

import java.util.ArrayList;
import java.util.HashMap;

public class Course {
    private long id;
    private String code;
    private String name;
    private long teacher;
    private ArrayList<Exam> exams;
    private ArrayList<Student> students;
    private HashMap<String, Object> attributes = new HashMap<>();

    public Course() {

    }

    public Course(long id, String code, String name, long teacher) {
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
        if (code.isEmpty()) {
            throw new DomainException("No code given");
        }
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isEmpty()) {
            throw new DomainException("No name given");
        }
        this.name = name;
    }

    public long getTeacher() {
        return teacher;
    }

    public void setTeacher(long teacher) {
        this.teacher = teacher;
    }

    public HashMap<String, Object> getAttributes() {
        return this.attributes;
    }

    public void setAttribute(String key, Object object) {
        attributes.put(key, object);
    }

    public String getInfo() {
        return getCode() + " - " + getName();
    }


}