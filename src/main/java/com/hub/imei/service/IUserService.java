package com.hub.imei.service;

import com.hub.imei.entity.AppUser;

import java.util.List;

public interface IUserService {
    AppUser saveUser(AppUser user);
    void addRoleToUser(String username, String role);
    AppUser getUser(String username);
    List<AppUser> getUsers();
    void deleteUser(String username);
    void forgotPassword(String email);
    boolean resetPass(String token, String newPass);
}
