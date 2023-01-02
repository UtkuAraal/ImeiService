package com.hub.imei.repo;

import com.hub.imei.entity.AppUser;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);

    @Modifying
    @Query("SELECT u FROM AppUser u WHERE u.isAdmin = true")
    List<AppUser> getAllAdmins();


    List<AppUser> findAll();

    @Modifying
    @Query("UPDATE AppUser SET passwordResetToken = ?2 WHERE username = ?1")
    void savePasswordResetToken(String email, String token);

    @Modifying
    @Query("SELECT username FROM AppUser WHERE passwordResetToken = ?1")
    String findEmailByToken(String token);

    @Modifying
    @Query("UPDATE AppUser SET password = ?2 WHERE username = ?1")
    void updatePassword(String email, String newPass);
}
