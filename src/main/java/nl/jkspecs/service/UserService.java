package nl.jkspecs.service;

import nl.jkspecs.dtos.user.UserDto;
import nl.jkspecs.dtos.user.UserInputDto;
import nl.jkspecs.exceptions.*;
import nl.jkspecs.models.User;
import nl.jkspecs.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }
    final String ErrorMsg = "Users not found";
    public List<UserDto> getUsers() {
        UserDto userDto = new UserDto();
        return userDto.fromUsersList(userRepository.findAll());
    }
    public UserDto getUserByUsername(String username) {
        UserDto userDto = new UserDto();
        return userDto.fromUser(userRepository.findById(username).orElseThrow(() ->
                new RecordNotFoundException("Gebruiker " + username + " niet gevonden")));
    }
    public List<UserDto> getUserByMobileNumber(String mobileNumber) {
        List<User> userFound = userRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new RecordNotFoundException(ErrorMsg));
        UserDto userDto = new UserDto();
        return userDto.fromUsersList(userFound);
    }
    public List<UserDto> getUsersByLastName(String lastname) {
        List<User> usersFound = userRepository.findByLastName(lastname).orElseThrow(() ->
                new RecordNotFoundException(ErrorMsg));
        UserDto userDto = new UserDto();
        if (usersFound.isEmpty()) {
            throw new RecordNotFoundException(ErrorMsg);
        } else {
            return userDto.fromUsersList(usersFound);
        }
    }
    public List<UserDto> getUserByBirthdayAndLastName(LocalDate birthdayDate, String lastName) {
        List<User> userFound = userRepository.findByBirthdayAndLastName(birthdayDate, lastName).orElseThrow(() ->
                new RecordNotFoundException(ErrorMsg));
        UserDto userDto = new UserDto();
        if (userFound.isEmpty()) {
            throw new RecordNotFoundException(ErrorMsg);
        }
        return userDto.fromUsersList(userFound);
    }
    public UserDto addUser(UserInputDto userInputDto) {
        UserDto userDto = new UserDto();
        User newUser = new User();
        newUser = userInputDto.toUser(newUser);
        newUser.setPassword(encoder.encode(userInputDto.password));
        userRepository.save(newUser);
        return userDto.fromUser(newUser);
    }
    public void removeUser(String username) {
        User user = userRepository.findById(username).orElseThrow(() ->
                new RecordNotFoundException(ErrorMsg));
        userRepository.deleteById(username);
    }
    public UserDto updateUser(String userId, UserInputDto userInputDto) {
        UserDto userDto = new UserDto();
        if (userInputDto.username != null)
            throw new WrongDataException("Username cannot be changed");
        User user = userRepository.findById(userId).orElseThrow(() ->
                new RecordNotFoundException(ErrorMsg));
        userInputDto.toUser(user);
        userRepository.save(user);
        return userDto.fromUser(user);
    }
}
