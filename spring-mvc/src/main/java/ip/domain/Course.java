package ip.domain;

import java.util.HashMap;

public class Course {
    private long id;
    private String code;
    private String name;
    private long teacher;
    private HashMap<String, Object> attributes = new HashMap<>();
    private int exams = 0;

    public Course() {

    }

    public Course(long id, String code, String name, long teacher, int exams) {
        setId(id);
        setCode(code);
        setName(name);
        setTeacher(teacher);
        this.exams = exams;
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
        if (teacher <= 0) {
            throw new DomainException("Please select a course teacher.");
        }
        this.teacher = teacher;
    }

    public int getExams() {
        return exams;
    }

    public void increaseExams() {
        this.exams += 1;
    }

    public void decreaseExams() {
        this.exams -= 1;
    }

    public HashMap<String, Object> getAttributes() {
        return this.attributes;
    }

    public void setAttribute(String key, Object object) {
        attributes.put(key, object);
    }

    public String getInfo() {
        return getCode() + " (" + getName() + ")";
    }

    @Override
    public boolean equals(Object object) {
        Course course = (Course) object;
        return course.code.equals(this.code);
    }
}