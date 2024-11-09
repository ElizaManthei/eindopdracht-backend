package nl.jkspecs.dtos.user;

import nl.jkspecs.models.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDto {
    public String username;
    public String firstName;
    public String lastName;
    public LocalDate birthday;
    public String email;
    public String password;
    public String mobileNumber;
    public UserDto(){};
    public UserDto(String username, String firstName, String lastName, LocalDate birthday, String email, String password, String mobileNumber) {
        this.username           = username;
        this.firstName          = firstName;
        this.lastName           = lastName;
        this.birthday           = birthday;
        this.email              = email;
        this.password           = password;
        this.mobileNumber       = mobileNumber;
    }
    public UserDto fromUser (User user){
        UserDto userDto         = new UserDto();
        userDto.username        = user.getUserName();
        userDto.firstName       = user.getFirstName();
        userDto.lastName        = user.getLastName();
        userDto.birthday        = user.getBirthday();
        userDto.email           = user.getEmail();
        userDto.mobileNumber    = user.getMobileNumber();
        return userDto;
    }
    public List<UserDto> fromUsersList(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User u : userList){
            userDtoList.add(this.fromUser(u));
        }
        return userDtoList;
    }
}