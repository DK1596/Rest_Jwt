package com.example.rest_jwt.models;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Role extends BaseEntity{

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<User> users;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }
}
