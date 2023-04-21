package com.example.hostelAvailabilitySystem.repository;

import com.example.hostelAvailabilitySystem.model.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
@Transactional
public interface HostelRepository extends JpaRepository<Hostel,Long> {

    // View Hostel

    @Query(value="select * from hostel where hostel_id=:m", nativeQuery = true)
    public Hostel viewHostelById(@Param("m") long hostelId);

    // Add Hostel
    @Modifying
    @Query(value = "INSERT INTO hostel (hostel_name, hostel_rooms, hostel_status, hostel_type) VALUES(:m, :n,:o, :p); ", nativeQuery = true)
    public void addHostel(@Param("m") String hostelName,
                          @Param("n") int hostelRooms,
                          @Param("o") String hostelStatus,
                          @Param("p") String hostelType);

    @Query(value = "select hostel_id from hostel where hostel_name=:m and hostel_rooms=:n and hostel_status=:o and hostel_type=:p ", nativeQuery = true)
    public long getHostelId(@Param("m") String hostelName,
                          @Param("n") int hostelRooms,
                          @Param("o") String hostelStatus,
                          @Param("p") String hostelType);

    // Edit Hostel Name
    @Modifying
    @Query(value = "Update hostel Set hostel_name=:m, date_modified = :r where hostel_id=:q",nativeQuery = true)
    public void editHostelName(@Param("m") String hostelName,
                               @Param("q") long hostelId,
                               @Param("r") Timestamp dateModified);

    // Block Hostel
    @Modifying
    @Query(value = "Update hostel Set hostel_status=:m, date_modified = :q where hostel_id=:n",nativeQuery = true)
    public void blockHostel(@Param("m") String newHostelStatus,
                            @Param("n") long hostelId,
                            @Param("q") Timestamp dateModified);

    // Release Hostel
    @Modifying
    @Query(value = "Update hostel Set hostel_status=:m, date_modified = :q where hostel_id=:n",nativeQuery = true)
    public void releaseHostel(@Param("m") String newHostelStatus,
                            @Param("n") long hostelId,
                              @Param("q") Timestamp dateModified);

    // Remove Hostel
    @Modifying
    @Query(value ="delete from hostel where hostel_id=:m ;",nativeQuery = true)
    public void removeHostel(@Param("m") long hostelId);

    // Get Hostel ID
    @Query(value = "select hostel_id from hostel where hostel_name=:m",nativeQuery = true)
    public long getHostelID(@Param("m") String hostelName);

    // Get Hostel Rooms
    @Query(value = "select hostel_rooms from hostel where hostel_name=:m",nativeQuery = true)
    public long getHostelRoom(@Param("m") String hostelName);

    // Add Rooms in a Hostel
    @Modifying
    @Query(value ="Update hostel set hostel_rooms=hostel_rooms+:m, date_modified = :q where hostel_id=:n" ,nativeQuery = true)
    public void addRooms(@Param("m") int HostelRooms,
                         @Param("n") long hostelId,
                         @Param("q") Timestamp dateModified);

    // Remove Rooms in a Hostel
    @Modifying
    @Query(value ="Update hostel set hostel_rooms=hostel_rooms-:m, date_modified = :q where hostel_id=:n" ,nativeQuery = true)
    public void removeRooms(@Param("m") int HostelRooms,
                            @Param("n") long hostelId,
                            @Param("q") Timestamp dateModified);


    @Query(value="select * from hostel where hostel_type=:m", nativeQuery = true)
    public List<Hostel> genderHostelView(@Param("m") String genderHostelType);


    @Modifying
    @Query(value ="Update hostel set warden_id=:n, date_modified = :q where hostel_id=:m", nativeQuery = true)
    public void assignWarden(@Param("m") long hostelId,
                             @Param("n") long wardenId,
                             @Param("q") Timestamp dateModified);

    @Query(value = "select * from hostel where hostel_id=:m",nativeQuery = true)
    public Hostel getHostelInfo(@Param("m") long hostelId);

    @Query(value="select * from hostel where hostel_id not in (select hostel_id from warden where hostel_id is not null);", nativeQuery = true)
    public List<Hostel> viewHostel1();

    @Query(value="select * from hostel", nativeQuery = true)
    public List<Hostel> viewHostel();

    @Transactional
    @Modifying
    @Query(
            value = "update hostel set hostel_rooms = ?1, date_modified = ?3 where hostel_id = ?2",
            nativeQuery = true
    )
    int updateHostelRoomsByHostelId(int hostelRooms, long hostelId, Timestamp dateModified);
}
