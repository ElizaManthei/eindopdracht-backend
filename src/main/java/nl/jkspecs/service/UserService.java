package nl.jkspecs.service;

import nl.jkspecs.dtos.user.UserDto;
import nl.jkspecs.dtos.user.UserInputDto;
import nl.jkspecs.exceptions.*;
import nl.jkspecs.models.User;
import nl.jkspecs.repository.UserRepository;
import nl.jkspecs.models.Role;
import nl.jkspecs.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }
    final String errorUserMsg = "Gebruiker(s) niet gevonden";
    final String errorRoleMsg = "Role(s) niet gevonden";
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
                new RecordNotFoundException(errorUserMsg));
        UserDto userDto = new UserDto();
        return userDto.fromUsersList(userFound);
    }
    public List<UserDto> getUsersByLastName(String lastname) {
        List<User> usersFound = userRepository.findByLastName(lastname).orElseThrow(() ->
                new RecordNotFoundException(errorUserMsg));
        UserDto userDto = new UserDto();
        if (usersFound.isEmpty()) {
            throw new RecordNotFoundException(errorUserMsg);
        } else {
            return userDto.fromUsersList(usersFound);
        }
    }
    public List<UserDto> getUserByBirthdayAndLastName(LocalDate birthdayDate, String lastName) {
        List<User> userFound = userRepository.findByBirthdayAndLastName(birthdayDate, lastName).orElseThrow(() ->
                new RecordNotFoundException(errorUserMsg));
        UserDto userDto = new UserDto();
        if (userFound.isEmpty()) {
            throw new RecordNotFoundException(errorUserMsg);
        }
        return userDto.fromUsersList(userFound);
    }
    public List<UserDto> getUsersByRole(String roleInput) {
        Role role = roleRepository.findById(roleInput).orElseThrow(() ->
                new RecordNotFoundException(errorRoleMsg));
        UserDto userDto = new UserDto();
        List<User> usersFound = new ArrayList<>(role.getUsers());
        if (!usersFound.isEmpty()) {
            return userDto.fromUsersList(usersFound);
        } else {
            throw new RecordNotFoundException(errorRoleMsg);
        }
    }
    public UserDto addUser(UserInputDto userInputDto) {
        UserDto userDto = new UserDto();
        User newUser = new User();
        newUser = userInputDto.toUser(newUser);
        List<Role> userRoles = new ArrayList<>();
        Role role = roleRepository.findById("CUSTOMER").orElseThrow(() ->
                new RecordNotFoundException(errorRoleMsg));
        userRoles.add(role);
        newUser.setRoles(userRoles);
        newUser.setPassword(encoder.encode(userInputDto.password));
        userRepository.save(newUser);
        return userDto.fromUser(newUser);
    }
    public void removeUser(String username) {
        User user = userRepository.findById(username).orElseThrow(() ->
                new RecordNotFoundException(errorUserMsg));
        userRepository.deleteById(username);
    }
    public UserDto updateUser(String userId, UserInputDto userInputDto) {
        UserDto userDto = new UserDto();
        if (userInputDto.username != null)
            throw new WrongDataException("De gebruikersnaam kan niet gewijzigd worden.");
        User user = userRepository.findById(userId).orElseThrow(() ->
                new RecordNotFoundException(errorUserMsg));
        userInputDto.toUser(user);
        userRepository.save(user);
        return userDto.fromUser(user);
    }

    public UserDto assignRoleToUser(String roleNameDto, String username) {
        UserDto userDto = new UserDto();
        User user = userRepository.findById(username).orElseThrow(() ->
                new RecordNotFoundException(errorUserMsg));
        Role role = roleRepository.findById(roleNameDto).orElseThrow(() ->
                new RecordNotFoundException(errorRoleMsg));
        List<Role> roleList = new ArrayList<>();

        if (!user.getRoles().isEmpty()) {
            roleList.addAll(user.getRoles());
        }

        for (Role r : user.getRoles()) {
            if (r.getRoleName().equals(roleNameDto)) {
                throw new RecordNotFoundException("Gebruiker heeft deze rol al");
            }
        }

        if (roleNameDto.equals("ADMIN")) {
            boolean ofEmployee = false;
            for (Role r : user.getRoles()) {
                if (r.getRoleName().equals("EMPLOYEE")) {
                    ofEmployee = true;
                }
            }
            if (!ofEmployee) {
                Role r = roleRepository.findById("EMPLOYEE").orElseThrow(() ->
                        new RecordNotFoundException(errorRoleMsg));
                roleList.add(r);
            }
        }
        roleList.add(role);
        user.setRoles(roleList);
        userRepository.save(user);
        return userDto.fromUser(user);
    }
}
