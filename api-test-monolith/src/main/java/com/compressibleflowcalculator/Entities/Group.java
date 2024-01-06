package com.compressibleflowcalculator.Entities;

import java.util.List;
import java.util.UUID;

import com.compressibleflowcalculator.Jackson_Views.Group_View;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "api_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "groupid", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;

    @OneToMany(mappedBy = "group_id", targetEntity = Group_Users.class, orphanRemoval = true, cascade = CascadeType.ALL, fetch = jakarta.persistence.FetchType.LAZY)
    @JsonView({ Group_View.Group_Response_View.class, Group_View.Group_Internal_View.class })
    private List<Group_Users> group_users;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    // Constructor
    public Group() {
    }

    public Group(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
