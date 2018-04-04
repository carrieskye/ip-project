package ip.domain;

import javax.persistence.*;

@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String number;
    private String firstName;
    private String lastName;
    private int courses = 0;

    public Teacher() {
    }

    public Teacher(String number, String firstName, String lastName) {
        setNumber(number);
        setFirstName(firstName);
        setLastName(lastName);
    }

    public Teacher(String number, String firstName, String lastName, int courses) {
        this(number, firstName, lastName);
        setCourses(courses);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        if (number.isEmpty()) {
            throw new DomainException("No number given");
        }
        this.number = number;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName.isEmpty()) {
            throw new DomainException("No first name given");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName.isEmpty()) {
            throw new DomainException("No last name given");
        }
        this.lastName = lastName;
    }

    public int getCourses() {
        return this.courses;
    }

    public void setCourses(int courses) {
        this.courses = courses;
    }

    public void increaseCourses() {
        this.courses += 1;
    }

    public void decreaseCourses() {
        this.courses -= 1;
    }

    public String getInfo() {
        return getFirstName() + " " + getLastName() + " (" + getNumber() + ")";
    }

    @Override
    public boolean equals(Object object) {
        Teacher teacher = (Teacher) object;
        return teacher.number.equals(this.number);
    }
}