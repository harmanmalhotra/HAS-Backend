package com.example.hostelAvailabilitySystem.repository;

        import com.example.hostelAvailabilitySystem.model.Course;
        import com.example.hostelAvailabilitySystem.model.Hostel;
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
public interface CourseRepository extends JpaRepository<Course, Long> {

    // View Course
    @Query(value="select * from course", nativeQuery = true)
    public List<Course> viewCourse();

    // Edit Course
    @Modifying
    @Query(value="Update course " +
            "Set `course_name`=:n,course_duration=:o,date_modified = :p  where courseid=:m",nativeQuery = true)
    public void editCourse(@Param("m") long courseId,
                           @Param("n") String courseName,
                           @Param("o") int courseDuration,
                           @Param("p") Timestamp dateModified);



    // Check Course
    @Query(value="Select count(*) from student join course on course.courseid=student.course_id where course.courseid=:m", nativeQuery = true)
    public int checkCourse(@Param("m") long courseId);

    // Remove Course
    @Modifying
    @Query(value="delete from course where courseid=:m",nativeQuery = true)
    public void removeCourse(@Param("m") long courseId);

    void deleteByCourseName(String x);

    // find Course by Name
    Optional<Object> findByCourseName(String courseName);

}
