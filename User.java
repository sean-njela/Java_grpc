import java.io.Serializable;

public class User implements Serializable {
    private String firstname;
    private String lastname;
    private String birthdate;
    private double salary;
    private Gender gender;
    private String division;
    private String position;

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public User(String firstname, String lastname, String birthdate, double salary, Gender gender, String division, String position) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.salary = salary;
        this.gender = gender;
        this.division = division;
        this.position = position;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public double getSalary() {
        return salary;
    }

    public Gender getGender() {
        return gender;
    }

    public String getDivision() {
        return division;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "User [firstname=" + firstname + ", lastname=" + lastname + ", birthdate=" + birthdate + ", salary=" + salary
                + ", gender=" + gender + ", division=" + division + ", position=" + position + "]";
    }
}
