package com.compressibleflowcalculator.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compressibleflowcalculator.Entities.Group;

import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    // @Cacheable(value = "groups", key = "#id")
    Optional<Group> findById(UUID id);
}
