package com.example.hostelAvailabilitySystem.repository;

import com.example.hostelAvailabilitySystem.model.RoomReq;
import com.example.hostelAvailabilitySystem.model.Student;
import com.example.hostelAvailabilitySystem.model.StudentCurrentStatus;
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
public interface StudentCurrentStatusRepository extends JpaRepository<StudentCurrentStatus, Long> {

//    @Query(value ="" ,nativeQuery = true)
//    public int bookRoom(@Param("m") long hostelId,
//                       @Param("n") String roomType);


    @Transactional
    @Modifying
    @Query(value="update student_current_status set vacate_status=\"R\", date_modified = :n where current_status_id=:m ", nativeQuery = true)
    public void vacateRequest(@Param("m") long currentStatusId, @Param("n") Timestamp dateModified);

    @Query(value = "select * from student_current_status where student_id=:m limit 1", nativeQuery = true)
    public StudentCurrentStatus viewStudentCurrentStatus(@Param("m") long studentId);

    @Query(value = "select * from student_current_status where current_status_id=:m limit 1", nativeQuery = true)
    public StudentCurrentStatus viewStudentCurrentStatusById(@Param("m") long currentStatusId);


    @Query(value = "select * from student_current_status where hostel_id=:m and vacate_status=\"R\"", nativeQuery = true)
    public List<StudentCurrentStatus> viewVacateRoomReq(@Param("m") long hostelId);

    @Modifying
    @Query(value = "delete from student_current_status where student_id=:m ", nativeQuery = true)
    public void vacateRoom(@Param("m") long studentId);

    @Query(value = "select count(*) from room join room_type on room.room_type_id=room_type.room_type_id join \n" +
            "student_current_status on room.room_id=student_current_status.room_id where room_type.room_type_id=:m",nativeQuery = true)
    public int checkRoomTypeExists(@Param("m") long roomTypeId);

    @Query(value = "select count(*) from student_current_status where room_id=:roomId", nativeQuery = true)
    public int checkRoomIdExists(@Param("roomId") long roomId);

    @Query(value = "select count(*) from student_current_status where hostel_id=:hostelId", nativeQuery = true)
    public int checkHostelStudents(@Param("hostelId") long hostelId);

    @Query(value = "select count(*) from warden join \n" +
            "student_current_status on student_current_status.hostel_id=warden.hostel_id where email=:email", nativeQuery = true)
    public int checkHostelStudentsWarden(@Param("email") String email);

    @Query(value = "select count(*) from student_current_status where student_id=:studentId", nativeQuery = true)
    public int checkCurrentStudents(@Param("studentId") long studentId);

    @Query(value = "select * from student_current_status where student_id=:studentId", nativeQuery = true)
    public StudentCurrentStatus studentInfo(@Param("studentId") long studentId);

    @Query(
            value = "select room_id from student_current_status where student_id = ?1 limit 1",
            nativeQuery = true
    )
    long getRoomIdByStudentId(long studentId);
}
