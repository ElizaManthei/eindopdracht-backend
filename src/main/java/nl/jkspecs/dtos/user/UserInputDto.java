package nl.jkspecs.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import nl.jkspecs.models.User;

import java.time.LocalDate;

public class UserInputDto {
    @NotBlank
    public String username;
    public String firstName;
    public String lastName;
    public LocalDate birthday;
    @Email
    public String email;
    public String password;
    public String mobileNumber;
    public UserInputDto(){}
    public UserInputDto( String username, String firstName, String lastName, LocalDate birthday,
                         String email, String password, String mobileNumber) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        this.mobileNumber = mobileNumber;
    }

    public User toUser(User user) {
        if( this.username!= null && !this.username.equals("")) {
            user.setUserName(this.username);
        }
        if(this.firstName != null && !this.firstName.equals("")) {
            user.setFirstName(this.firstName);
        }
        if(this.lastName != null && !this.lastName.equals("")) {
            user.setLastName(this.lastName);
        }
        if(this.birthday!=null) {
            user.setBirthday(this.birthday);
        }
        if(this.email != null && !this.email.equals("")) {
            user.setEmail(this.email);
        }
        if(this.password != null && !this.password.equals("")) {
            user.setPassword(this.password);
        }
        if(this.mobileNumber != null && !this.mobileNumber.equals("")) {
            user.setMobileNumber(this.mobileNumber);
        }
        return user;
    }

}

