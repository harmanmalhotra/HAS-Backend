package com.example.hostelAvailabilitySystem.repository;

import com.example.hostelAvailabilitySystem.model.Student;
//import com.example.hostelAvailabilitySystem.model.ViewStudents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
    @Query(value = "select *  from student;", nativeQuery = true)
    public List<Student> viewStudents();

//    @Query(value =  "select email, student_gender, student_name, student_phone_no, student_roll_no, course_name, course_duration, hostel_id,room_id" +
//            " from student join course on student.course_courseid=course.courseid left join " +
//            "student_current_status on student.student_id=student_current_status.student_id where email=:m ", nativeQuery = true)
//    public String viewStudentProfile(@Param("m") String email);

    @Transactional
    @Modifying
    @Query(value = "update student set student_name=:m , student_phone_no=:n, date_modified = :q where email=:o ;", nativeQuery = true)
    public void editStudentProfile(@Param("o") String email,
                                   @Param("m") String newStudentName,
                                   @Param("n") long newPhoneNo,
                                   @Param("q")Timestamp dateModified);

    @Modifying
    @Query(value ="update student set course_id=:m, date_modified = :q where email=:n ;",nativeQuery = true)
    public void mapCourse(@Param("m") long courseID,
                          @Param("n") String email,
                          @Param("q")Timestamp dateModified);


    @Transactional
    @Query(value = "select student_id from student where email=:m",nativeQuery = true)
    public long getStudentID(@Param("m") String studentEmail);



    Optional<Student> findByEmail(String email);

//    @Modifying
//    @Query(value = "Update student Set student_name=:m," +
//            "student_phone_no=:n where student_name=:q",nativeQuery = true)
//    public void editStudent(@Param("m") String studentName,
//                           @Param("n") long studentPhoneNo,
//                           @Param("o") String hostelStatus,
//                           @Param("p") String hostelType,
//                           @Param("q") String oldHostelName);
    @Query(
            value = "select student_current_status.student_id, student.date_created, student.date_modified, email, student_gender, student_name, student_phone_no, student_roll_no, course_id from student_current_status join student on student_current_status.student_id=student.student_id where hostel_id=?1",
            nativeQuery = true
    )
    public List<Student> viewHostelStudents(long hostelId);

    @Query(
            value = "select student_current_status.student_id, student.date_created, student.date_modified, email, student_gender, student_name, student_phone_no, student_roll_no, course_id from student_current_status join student on student_current_status.student_id=student.student_id where room_id = ?1",
            nativeQuery = true
    )
    List<Student> findStudentsByRoomId(long roomId);

    @Query(
            value = "select student_current_status.student_id, student.date_created, student.date_modified, email, student_gender, student_name, student_phone_no, student_roll_no, course_id from student_current_status join student on student_current_status.student_id=student.student_id where hostel_id=?1 AND vacate_status!=\"R\"",
            nativeQuery = true
    )
    List<Student> viewVacateHostelStudents(long hostelId);

    @Query(value = "select * from student where email like concat(\"%\",:m,\"%\") ", nativeQuery = true)
    public List<Student> searchStudent(@Param("m") String search);

    @Query(
            value = "select * from student where student_id NOT IN (select student_id from student_current_status union select student_id from room_req)",
            nativeQuery = true
    )
    List<Student> viewNonHostelStudents();

    @Modifying
    @Query(value ="update student set student_name=:n, student_phone_no=:o,student_roll_no=:p,student_gender=:q,course_id=:r, date_modified = :s where email=:m ;",nativeQuery = true)
    public void editStudent(@Param("m") String email,
                            @Param("n") String studentName,
                            @Param("o") long studentPhoneNo,
                            @Param("p") String rollNo,
                            @Param("q") String gender,
                            @Param("r") long courseID,
                            @Param("s") Timestamp dateModified);


    @Modifying
    @Query(value ="delete from student where student_id=:m ;",nativeQuery = true)
    public void removeStudent(@Param("m") long studentId);

    @Query(value ="select student_phone_No from student where student_id=:m ;",nativeQuery = true)
    public int phoneStudent(@Param("m") long studentId);
}
