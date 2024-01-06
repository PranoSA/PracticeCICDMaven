package com.compressibleflowcalculator.Entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Group_Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "groupid", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;

    @Column(name = "group_id")
    private UUID group_id;

}
