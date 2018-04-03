package ip.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String number;
    private String firstName;
    private String lastName;

    public Student() {
    }

    public Student(String number, String firstName, String lastName) {
        setNumber(number);
        setFirstName(firstName);
        setLastName(lastName);
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

    public String getInfo() {
        return getFirstName() + " " + getLastName() + " (" + getNumber() + ")";
    }

    @Override
    public boolean equals(Object object) {
        Student student = (Student) object;
        return student.number.equals(this.number);
    }

}
