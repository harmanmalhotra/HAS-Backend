package com.example.hostelAvailabilitySystem.repository;

import com.example.hostelAvailabilitySystem.model.Hostel;
import com.example.hostelAvailabilitySystem.model.Warden;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface WardenRepository extends JpaRepository<Warden, Long> {

    Optional<Warden> findByEmail(String email);

    @Query(value = "select hostel_id from warden join hostel on warden.warden_id=hostel.warden_id where email=:m ",nativeQuery = true)
    public long getHostelId(@Param("m") String wardenEmail);

    @Transactional
    @Modifying
    @Query(value = "update warden set warden_name=:m , warden_phone_no=:n, date_modified = :p where email=:o ;", nativeQuery = true)
    public void editWarden(@Param("m") String wardenName,
                           @Param("n") long wardenPhoneNo,
                           @Param("o") String email,
                           @Param("p") Timestamp dateModified);

    @Query(value = "select * from warden", nativeQuery = true)
    public List<Warden> viewWardens();

    @Query(value = "select * from warden where hostel_id is not null", nativeQuery = true)
    public List<Warden> viewWardens1();
    @Query(value = "select * from warden where hostel_id is null", nativeQuery = true)
    public List<Warden> viewWardens2();

    @Query(value = "select hostel_name, hostel_id from warden join hostel on warden.warden_id=hostel.warden_id where warden.email=:m", nativeQuery = true)
    public List<String> getAllotedHostels(@Param("m") String email);

    @Query(value="select * from warden where hostel_id is not null;", nativeQuery = true)
    public List<Warden> viewHostel2();

    @Modifying
    @Query(value ="delete from warden where email=:m ;",nativeQuery = true)
    public void removeWarden(@Param("m") String email);

    @Modifying
    @Query(value ="update warden set hostel_id=:m, date_modified = :o where email=:n ;",nativeQuery = true)
    public void mapWarden(@Param("m") long hostelId,
                          @Param("n") String email,
                          @Param("o") Timestamp dateModified);

    @Modifying
    @Query(value ="update warden set hostel_id=NULL, date_modified = :o where hostel_id=:n ;",nativeQuery = true)
    public void unMapWarden(@Param("n") long hostelId,
                            @Param("o") Timestamp dateModified);

    @Modifying
    @Query(value ="update warden set hostel_id=NULL, date_modified = :o where email=:n ;",nativeQuery = true)
    public void unMapWarden2(@Param("n") String email,
                             @Param("o") Timestamp dateModified);

    @Modifying
    @Query(value ="delete from warden where warden_id=:m ;",nativeQuery = true)
    public void removeHostel(@Param("m") long wardenId);

}
