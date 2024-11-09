package nl.jkspecs.dtos.user;

import nl.jkspecs.models.User;
import nl.jkspecs.models.Role;
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
    public List<String> roles = new ArrayList<>();
    public UserDto(){};
    public UserDto(String username, String firstName, String lastName, LocalDate birthday,
                   String email, String password, String mobileNumber, List<String> roles) {
        this.username           = username;
        this.firstName          = firstName;
        this.lastName           = lastName;
        this.birthday           = birthday;
        this.email              = email;
        this.password           = password;
        this.mobileNumber       = mobileNumber;
        this.roles              = roles;
    }
    public UserDto fromUser (User user){
        UserDto userDto         = new UserDto();
        userDto.username        = user.getUserName();
        userDto.firstName       = user.getFirstName();
        userDto.lastName        = user.getLastName();
        userDto.birthday        = user.getBirthday();
        userDto.email           = user.getEmail();
        userDto.mobileNumber    = user.getMobileNumber();
        for (Role r: user.getRoles()) {
            userDto.roles.add(r.getRoleName());
        }
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