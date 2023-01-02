package com.hub.imei.service;

import com.hub.imei.core.EmailService;
import com.hub.imei.core.SendGridEmailService;
import com.hub.imei.entity.AppUser;
import com.hub.imei.entity.Role;
import com.hub.imei.repo.AppUserRepo;
import com.hub.imei.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements IUserService, UserDetailsService {

    private final AppUserRepo userRepo;

    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found in the database");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public AppUser saveUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setBanned(false);
        user.setAdmin(false);

        return userRepo.save(user);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser user = userRepo.findByUsername(username);
        Role Addrole = roleRepo.findByName(role);
        user.getRoles().add(Addrole);
    }

    @Override
    public AppUser getUser(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public void deleteUser(String username) {
        AppUser user = userRepo.findByUsername(username);
        userRepo.delete(user);
    }
    @Override
    public void forgotPassword(String email){
        // Generate a unique password reset token
        String token = UUID.randomUUID().toString();

        // Save the token to the database along with the user's email address
        userRepo.savePasswordResetToken(email, token);

        // Send the password reset email with a link that includes the token
        String resetPasswordUrl = "http://127.0.0.1:5500/newpassword.html?token=" + token;
        emailService.sendPasswordResetEmail(email, resetPasswordUrl);
    }

    @Override
    public boolean resetPass(String token, String newPass){
        String email = userRepo.findEmailByToken(token);
        if (email == null) {
            // Token not found or has expired
            return false;
        } else {
            newPass = passwordEncoder.encode(newPass);
            // Token found, update the user's password
            userRepo.updatePassword(email, newPass);
            return true;
        }
    }






}
