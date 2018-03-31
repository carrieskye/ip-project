package ip.domain;

public class Teacher extends Person {
    private int courses = 0;

    public Teacher() {

    }

    public Teacher(long id, String number, String firstName, String lastName, int courses) {
        super(id, number, firstName, lastName);
        this.courses = courses;
    }

    public int getCourses() {
        return this.courses;
    }

    public void increaseCourses(){this.courses += 1;}

    public void decreaseCourses(){this.courses -= 1;}
}
