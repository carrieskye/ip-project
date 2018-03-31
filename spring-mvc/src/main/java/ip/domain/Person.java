package ip.domain;

public abstract class Person {
    private long id;
    private String number;
    private String firstName;
    private String lastName;

    public Person() {

    }

    public Person(long id, String number, String firstName, String lastName) {
        setId(id);
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
        Person person = (Person) object;
        return person.number.equals(this.number);
    }

}
