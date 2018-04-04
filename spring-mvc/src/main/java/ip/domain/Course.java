package ip.domain;

import javax.persistence.*;
import java.util.HashMap;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String code, name;
    private long teacher;
    private int exams = 0;

    @Transient
    private HashMap<String, Object> attributes = new HashMap<>();

    public Course() {

    }

    public Course(String code, String name, long teacher) {
        setCode(code);
        setName(name);
        setTeacher(teacher);
    }

    public Course(String code, String name, long teacher, int exams) {
        this(code, name, teacher);
        setExams(exams);
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

    public void setExams(int exams) {
        this.exams = exams;
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