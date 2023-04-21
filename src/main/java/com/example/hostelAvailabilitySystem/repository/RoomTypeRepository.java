package com.example.hostelAvailabilitySystem.repository;

import com.example.hostelAvailabilitySystem.model.RoomType;
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
public interface RoomTypeRepository extends JpaRepository<RoomType,Long> {

    Optional<RoomType> findByRoomName(String roomName);

    @Query(
            value = "select * from room_type where room_type_id in (select distinct room.room_type_id from room join room_type on room.room_type_id=room_type.room_type_id where hostel_id = ?1 AND beds_occupied <= room_capacity AND room_status = \"UnBlocked\")",
            nativeQuery = true
    )
    public List<RoomType> viewRoomType(long hostelId);

    @Query(value = "select distinct room_type.room_type_id, room_capacity, room_fee, room_name from room_type", nativeQuery = true)
    public List<String> viewRoomTypes();

    @Modifying
    @Query(
            value = "update room_type set room_name=:roomName, room_fee=:roomFee, room_capacity=:roomCapacity, date_modified = :dateModified where room_type_id=:roomTypeId ;",
            nativeQuery = true
    )
    public void editRoomType(@Param("roomTypeId") long roomTypeId, @Param("roomName") String roomName, @Param("roomFee") int roomFee, @Param("roomCapacity") int roomCapacity, @Param("dateModified") Timestamp dateModified);

    @Modifying
    @Query(value = "delete from room_type where room_type_id=:roomTypeId ;", nativeQuery = true)
    public void removeRoomType(@Param("roomTypeId") long roomTypeId);
}
