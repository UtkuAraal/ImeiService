package com.hub.imei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ImeiNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotNull
    @Pattern(regexp = "^[0-9]{15}$")
    private String imei;

    @NotNull
    @Pattern(regexp = "^[0-9]{10}$")
    private String phone;

    private boolean banned;

    private Integer searchCount;

    @ManyToOne
    private AppUser user;
}
