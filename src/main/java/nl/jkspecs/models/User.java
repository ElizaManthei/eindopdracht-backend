package nl.jkspecs.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(nullable = false, unique = true)
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private String password;
    private String mobileNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;
    public User() {}

    public User(String username, String firstName, String lastName, LocalDate birthday,
                String email, String password, String mobileNumber) {
        this.username       = username;
        this.firstName      = firstName;
        this.lastName       = lastName;
        this.birthday       = birthday;
        this.email          = email;
        this.password       = password;
        this.mobileNumber   = mobileNumber;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

}
