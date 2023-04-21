package com.example.hostelAvailabilitySystem.repository;

import com.example.hostelAvailabilitySystem.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AuthoritiesRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByAuthority(String authority);
}
