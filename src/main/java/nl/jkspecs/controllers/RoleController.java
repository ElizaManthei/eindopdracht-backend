package nl.jkspecs.controllers;

import nl.jkspecs.dtos.role.RoleDto;
import nl.jkspecs.dtos.role.RoleInputDto;
import nl.jkspecs.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }
    @GetMapping("/rolename")
    public ResponseEntity<Object> getRoleByRoleName(@RequestBody RoleInputDto roleNameInDto) {
        return ResponseEntity.ok(roleService.getRoleByRoleName(roleNameInDto.roleName));
    }
}
