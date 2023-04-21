package com.example.hostelAvailabilitySystem.repository;

import com.example.hostelAvailabilitySystem.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public interface RoomReqRepository extends JpaRepository<RoomReq, Long>  {

    @Query(
            value = "select * from room_req where hostel_id = ?1",
            nativeQuery = true
    )
    List<RoomReq> findRoomReqByHostelId(long hostelId);

    @Transactional
    @Query(value = "select count(*) from room_req where student_id=:m ", nativeQuery = true)
    public int checkSingleReq(@Param("m") long studentId);

    @Query(value = "select * from room_req where student_id=:m limit 1", nativeQuery = true)
    public long getReqId(@Param("m") long studentId);

    @Query(value = "select * from room_req where req_id=:m limit 1", nativeQuery = true)
    public RoomReq viewRoomReq(@Param("m") long reqId);

    @Transactional
    @Modifying
    @Query(value = "insert into room_req (room_status,room_type_id, hostel_id, student_id, date_created, date_modified) values" +
            "(:m, :n, :o, :p, :q, :r)", nativeQuery = true)
    public void roomReq(@Param("m") String roomStatus,
                        @Param("n") long roomTypeId,
                        @Param("o") long hostelId,
                        @Param("p") long studentId,
                        @Param("q") Timestamp dateCreated,
                        @Param("r") Timestamp dateModified);

    @Modifying
    @Query(value = "update room_req set room_status =:n, date_modified = :r where req_id = :m ; ", nativeQuery = true)
    public void updateRoomStatus(@Param("m") long roomReq,
                                 @Param("n") String roomStatus,
                                 @Param("r") Timestamp dateModified);

    @Query(value = "select email, student_name,room_name, room_fee, room_capacity, req_id from room_req join room_type on room_req.room_type_id = room_type.room_type_id join student on student.student_id=room_req.student_id where hostel_id=:m and room_status!=\"Alloted\" ", nativeQuery = true)
    public List<String> viewRoomReqs(@Param("m") long hostelId);

    @Query(
            value = "select count(*) from room_req where student_id = ?1",
            nativeQuery = true
    )
    int findRoomReqByStudentId(long studentId);

    @Modifying
    @Query(value ="delete from room where hostel_id=:m", nativeQuery = true)
    public void removeRoomReqs(@Param("m") long hostelId);


    @Modifying
    @Query(value ="delete from room_req where student_id=:m", nativeQuery = true)
    public void removeStudentRoomReqs(@Param("m") long student_id);
}
