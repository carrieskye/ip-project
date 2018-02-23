package ip.domain;

public abstract class Person {
    private String id;
    private String firstName;
    private String lastName;

    public Person(String id, String firstName, String lastName){
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id.isEmpty()){
            throw new DomainException("No id given");
        }
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName.isEmpty()){
            throw new DomainException("No first name given");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName.isEmpty()){
            throw new DomainException("No last name given");
        }
        this.lastName = lastName;
    }

}
