package com.hub.imei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 2)
    @NotNull
    private String name;

    @Size(min = 2)
    @NotNull
    private String surname;

    @Column(unique = true)
    @NotNull
    @Pattern(regexp = "^[0-9]{10}$")
    private String phone;

    @Column(unique = true)
    @Email
    private String username;

    @NotNull
    @Size(min = 8)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    private boolean isAdmin;

    private boolean banned;

    private String passwordResetToken;


}
