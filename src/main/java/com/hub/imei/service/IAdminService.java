package com.hub.imei.service;

import com.hub.imei.entity.AppUser;

import java.util.List;

public interface IAdminService {
    AppUser saveAdmin(AppUser user);
    List<AppUser> getAllAdmins();
    Integer getCountOfUsers();

}
