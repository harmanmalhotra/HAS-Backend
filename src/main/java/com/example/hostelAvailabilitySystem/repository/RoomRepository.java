package com.example.hostelAvailabilitySystem.repository;

import com.example.hostelAvailabilitySystem.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByRoomNo(int roomNo);

    @Query(
            value ="select * from room where hostel_id = ?1" ,
            nativeQuery = true
    )
    Optional<List<Room>> findByHostelId(long hostelId);

    @Query(value ="select count(*) from room where hostel_id=:m and room_type_id=:n and room_status!=\"Blocked\"" ,nativeQuery = true)
    public int roomCount(@Param("m") long hostelId, @Param("n") long roomTypeId);

    @Query(value ="select count(*) from room where hostel_id=:m " ,nativeQuery = true)
    public int roomTotal(@Param("m") long hostelId);

    @Query(value ="select count(*) from room where room_type_id=:m " ,nativeQuery = true)
    public int checkRoomTypeExists(@Param("m") long roomTypeId);

    @Query(value ="select count(*) from room join student_current_status on room.room_id = student_current_status.room_id where room.hostel_id=:m and room.room_type_id=:n " ,nativeQuery = true)
    public int allotedRooms(@Param("m") long hostelId, @Param("n") long roomTypeId);

    @Query(value ="select count(*) from room where room_type=:m and hostel_id=:n" ,nativeQuery = true)
    public int availableRooms(@Param("m") long roomTypeId,
                              @Param("n")long hostelId);

    @Query(value ="select room_fee from room_type where room_type_id=:n limit 1" ,nativeQuery = true)
    public int viewFee(@Param("n") long roomTypeId);

    @Transactional
    @Modifying
    @Query(
            value = "update room set room_type_id = ?2, room_status = ?3, date_modified = ?4  where room_id = ?1",
            nativeQuery = true
    )
    public void editRoom(long roomId, long roomTypeId, String roomStatus, Timestamp dateModified);

    @Modifying
    @Query(value = "delete from student_current_status where room_id=:roomId", nativeQuery = true)
    public void removeHostelRoom(@Param("roomId") long roomId);

    @Query(
            value = "select room_id, room.date_created, room.date_modified, beds_occupied, room_no, room_status, hostel_id, room.room_type_id from room join room_type on room.room_type_id=room_type.room_type_id where hostel_id = ?1 AND beds_occupied < room_capacity AND room.room_type_id = ?2 AND room_status = ?3 limit 1",
            nativeQuery = true
    )
    public Room allotRoom(long hostelId, long roomTypeId, String roomStatus);

    @Modifying
    @Transactional
    @Query(
            value = "update room set beds_occupied = beds_occupied + 1, date_modified = ?2 where room_id = ?1",
            nativeQuery = true
    )
    int occupyBed(long roomId, Timestamp dateModified);

    @Modifying
    @Transactional
    @Query(
            value = "update room set beds_occupied = beds_occupied - 1, date_modified = ?2 where room_id = ?1",
            nativeQuery = true
    )
    int makeBedEmpty(long roomId, Timestamp dateModified);

    @Query(value ="select room_id from room where room_status!=\"Blocked\" and room_type_id=:roomTypeId and room_id not in (select room_id from student_current_status where hostel_id=:hostelId ) limit 1" ,nativeQuery = true)
    public int availableRoomId(@Param("hostelId") long hostelId,
                               @Param("roomTypeId") long roomTypeId);

    @Query(value ="select * from room where room_id=:m " ,nativeQuery = true)
    public Room getRoomInfo(@Param("m") long roomId);

    @Query(
            value ="select count(*) from room where hostel_id = ?2 AND room_no = ?1",
            nativeQuery = true
    )
    int findByRoomNoAndHostelId(int roomNo, long hostelId);

    @Modifying
    @Query(value ="delete from room where hostel_id=:m", nativeQuery = true)
    public void removeRooms(@Param("m") long hostelId);
}