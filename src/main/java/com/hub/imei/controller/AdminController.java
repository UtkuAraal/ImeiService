package com.hub.imei.controller;

import com.hub.imei.entity.AppUser;
import com.hub.imei.service.AdminService;
import com.hub.imei.service.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final IAdminService adminService;

    @PostMapping("/save")
    public ResponseEntity<AppUser> saveAdmin(@Valid @RequestBody AppUser user, BindingResult theBindingResult){
        if(theBindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(null);
        }else{
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/admin/save").toUriString());
            return ResponseEntity.created(uri).body(adminService.saveAdmin(user));
        }
    }

    @GetMapping("/getAllAdmin")
    public ResponseEntity<List<AppUser>> getAllAdmins(){
        return ResponseEntity.ok().body(adminService.getAllAdmins());
    }

    @GetMapping("/getCountOfUsers")
    public ResponseEntity<Integer> getCountOfUser(){
        return ResponseEntity.ok().body(adminService.getCountOfUsers());
    }
}
