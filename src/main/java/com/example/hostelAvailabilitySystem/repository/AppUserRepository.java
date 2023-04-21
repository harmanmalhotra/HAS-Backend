package com.example.hostelAvailabilitySystem.repository;

import com.example.hostelAvailabilitySystem.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(
            value = "update app_user set password = ?1, date_modified = ?3 where email = ?2",
            nativeQuery = true
    )
    int updatePasswordByEmail(String password, String email, Timestamp dateModified);

    @Modifying
    @Query(value ="delete from app_user where email=:m ;",nativeQuery = true)
    public void removeUser(@Param("m") String email);
}
