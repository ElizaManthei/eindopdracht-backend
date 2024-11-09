package nl.jkspecs.dtos.role;

import nl.jkspecs.dtos.user.UserDto;

import java.util.ArrayList;
import java.util.List;

public class RoleDto {
    public String roleName;
    public List<UserDto> userDtoList = new ArrayList<>();
}
