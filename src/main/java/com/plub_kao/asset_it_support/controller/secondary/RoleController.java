package com.plub_kao.asset_it_support.controller.secondary;


import com.plub_kao.asset_it_support.entity.Role;
import com.plub_kao.asset_it_support.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    public ResponseEntity<List<Role>> findAllRoles() {
        List<Role> roles = roleService.getAllRole();
        if (roles == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT); //กรณีค่า roloes เป็น null
        return ResponseEntity.ok(roles);
    }
}
