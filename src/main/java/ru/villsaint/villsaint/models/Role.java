package ru.villsaint.villsaint.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Role")
public class Role implements GrantedAuthority{
    @Id
    private Long id;
    @Column
    private String role;
    @ManyToMany(mappedBy = "roles")
    List<User> users;

    public Role() {
    }

    public Role(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public boolean equals(Object obj) {
        Role role = (Role) obj;
        return this.role.equals(role.getRole());
    }

    @Override
    public String toString() {
        return role;
    }
}
