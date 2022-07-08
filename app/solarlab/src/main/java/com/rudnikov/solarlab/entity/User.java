package com.rudnikov.solarlab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rudnikov.solarlab.entity.enumerated.UserRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    boolean isAccountNonExpired;
    boolean isAccountNonLocked;
    boolean isCredentialsNonExpired;
    boolean isEnabled;

    @OneToMany(mappedBy = "author", orphanRemoval = true)
    @JsonManagedReference(value = "advert_author")
    private List<Advert> adverts;

    @OneToMany(mappedBy = "author", orphanRemoval = true)
    @JsonManagedReference(value = "comment_author")
    private List<Comment> comments;

    public User(String username, String email, String phoneNumber, String password) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(String username, String email, String phoneNumber, String password, UserRole role) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
    }

    public User(String username,
                String email,
                String phoneNumber,
                String password,
                UserRole role,
                Boolean isAccountNonExpired,
                Boolean isAccountNonLocked,
                Boolean isCredentialsNonExpired,
                Boolean isEnabled
    ) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getAuthority()));
    }

    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

}