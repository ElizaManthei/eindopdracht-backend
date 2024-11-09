package nl.jkspecs.controllers;

import nl.jkspecs.dtos.findInputDtos.InputIdDto;
import nl.jkspecs.dtos.findInputDtos.InputLastNameDateDto;
import nl.jkspecs.dtos.user.UserDto;
import nl.jkspecs.dtos.user.UserInputDto;
import nl.jkspecs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {

        this.userService        = userService;
    }
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username)) ;
    }

    @GetMapping("/lastname/{lastname}")
    public ResponseEntity<List<UserDto>> getUsersByLastName(@PathVariable String lastname) {
        return ResponseEntity.ok(userService.getUsersByLastName(lastname)) ;
    }

    @GetMapping("/mobile")
    public ResponseEntity<Object> getUserByMobileNumber(@RequestBody String mobileNumber) {
        return ResponseEntity.ok(userService.getUserByMobileNumber(mobileNumber));
    }

    @GetMapping("/b")
    public ResponseEntity<Object> getUserByBirthdayAndLastName(@RequestBody InputLastNameDateDto inputLastNameDateDto) {

        return ResponseEntity.ok(userService.getUserByBirthdayAndLastName(inputLastNameDateDto.inputDate, inputLastNameDateDto.lastName));
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody UserInputDto userInputDto, BindingResult br) {
        ResponseEntity<Object> sb = getObjectResponseEntity(br);
        if (sb != null) return sb;
        UserDto newUser = userService.addUser(userInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/" + newUser.username).toUriString());
        return ResponseEntity.created(uri).body(newUser);
    }

    @DeleteMapping
    public ResponseEntity<Object> removeUserById(@RequestBody InputIdDto id){
        userService.removeUser(id.username);
        return ResponseEntity.ok("User deleted");
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Object> updateUser (@PathVariable String id,
                                               @RequestBody UserInputDto userInputDto, BindingResult br) {
        ResponseEntity<Object> sb = getObjectResponseEntity(br);
        if (sb != null) return sb;
        UserDto updatedUser = userService.updateUser( id, userInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/" + id).toUriString());
        return ResponseEntity.created(uri).body(updatedUser);
    }

    public static ResponseEntity<Object> getObjectResponseEntity(BindingResult br) {
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage() + "\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
