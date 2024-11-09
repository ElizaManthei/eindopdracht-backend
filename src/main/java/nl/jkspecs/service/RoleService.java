package nl.jkspecs.service;

import nl.jkspecs.dtos.role.RoleDto;
import nl.jkspecs.dtos.user.UserDto;
import nl.jkspecs.exceptions.RecordNotFoundException;
import nl.jkspecs.models.Role;
import nl.jkspecs.models.User;
import nl.jkspecs.repository.RoleRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public List<RoleDto> getRoles(){

        return fromRoleListToRoleDtoList(roleRepository.findAll());
    }

    public RoleDto getRoleByRoleName(String rolename) {

        Role role = roleRepository.findById(rolename).orElseThrow(() ->
                new RecordNotFoundException("Role not found"));
        return fromRoleToRoleDto(role);
    }
    public RoleDto fromRoleToRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        UserDto userDto = new UserDto();
        roleDto.roleName = role.getRoleName();
        if (!role.getUsers().isEmpty()) {
            List<User> userList = new ArrayList<>(role.getUsers());
            roleDto.userDtoList =  userDto.fromUsersList(userList);
        }
        return roleDto;
    }
    public List<RoleDto> fromRoleListToRoleDtoList (List<Role> roleList) {
        List<RoleDto> roleDtoList = new ArrayList<>();
        for (Role role: roleList ) {
            roleDtoList.add(fromRoleToRoleDto(role));
        }
        return roleDtoList;
    }
}
