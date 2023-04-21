package com.example.hostelAvailabilitySystem.service.serviceImpl;

import com.example.hostelAvailabilitySystem.model.*;
import com.example.hostelAvailabilitySystem.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomReqRepository roomReqRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HostelRepository hostelRepository;

    @Autowired
    private StudentCurrentStatusRepository studentCurrentStatusRepository;

    public String saveStudent(Student student) {
        boolean studentExits = studentRepository.findByEmail(student.getEmail()).isPresent();

        if (studentExits) {
            throw new IllegalStateException("email already taken");
        }

        studentRepository.save(student);

        return "it works";
    }

    public String roomReq(String studentEmail, long roomTypeId, long hostelId) {
        String roomStatus = "Req";
        long studentId = studentRepository.getStudentID(studentEmail);
//        Long studentID=Long.parseLong(studentId);
        int checkSingleReq = roomReqRepository.checkSingleReq(studentId);
        int availableRooms = roomRepository.roomCount(hostelId, roomTypeId) - roomRepository.allotedRooms(hostelId, roomTypeId);

        if (checkSingleReq > 0) {
            return "Already Raised Room Request";
        }

        Timestamp dateCreated = new Timestamp(Calendar.getInstance().getTimeInMillis());

        Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());

        roomReqRepository.roomReq(roomStatus, roomTypeId, hostelId, studentId, dateCreated, dateModified);
        return "Room Requested Successfully";
    }

    public int viewFee(long roomTypeId) {
        return roomRepository.viewFee(roomTypeId);
    }

    public Student viewStudentProfile(String email) throws UsernameNotFoundException {
        return studentRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("Student Not Found"));}

    public String editStudentProfile(String email, String newStudentName, long newPhoneNo) {
        Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
        studentRepository.editStudentProfile(email, newStudentName, newPhoneNo, dateModified);
        return "Profile Updated Successfully, Please Reload the Page!";
    }

    public List<Hostel> genderHostelView(String genderHostelType) {
        return hostelRepository.genderHostelView(genderHostelType);}

    @Autowired
    private final AuthoritiesRepository authoritiesRepository;
    @Autowired
    private final CourseRepository courseRepository;
    private final EmailValidator emailValidator;
    private final AppUserService appUserService;

    public String register(StudentRegistrationRequest request) {
        boolean isValidEmail = emailValidator.
                test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        Authority authority = authoritiesRepository.findByAuthority("ROLE_STUDENT").orElseThrow(() ->
                new UsernameNotFoundException("Role Not Found"));

        appUserService.signUpUser(
                new AppUser(
                        request.getEmail(),
                        request.getPassword(),
                        authority
                )
        );

        Course course = (Course) courseRepository.findByCourseName(request.getCourseName()).orElseThrow(() ->
                new UsernameNotFoundException("Course Not Found"));

        saveStudent(
                new Student(
                        request.getName(),
                        request.getEmail(),
                        request.getRollNo(),
                        request.getPhoneNo(),
                        request.getGender(),
                        course
                )
        );

        return "Student Registered";

    }

    public List<RoomType> viewRoomType(long hostelId) {
        return roomTypeRepository.viewRoomType(hostelId);
    }

    public String vacateRequest(long studentCurrentStatusId) {
        Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
        studentCurrentStatusRepository.vacateRequest(studentCurrentStatusId, dateModified);
        return "Vacate Request raised";
    }

    public Hostel getHostelInfo(long hostelId) {
        return hostelRepository.getHostelInfo(hostelId);
    }

    public Room getRoomInfo(long roomId) {
        return (Room) roomRepository.getRoomInfo(roomId);
    }

    public StudentCurrentStatus viewStudentCurrentStatus(long studentId){
        return studentCurrentStatusRepository.viewStudentCurrentStatus(studentId);}

    public List<Hostel> viewHostels() {
        return hostelRepository.findAll();
    }

    public boolean viewRoomReq(long studentId) {
        if(roomReqRepository.findRoomReqByStudentId(studentId) > 0) return true;
        else return false;
    }


    public List<Course> viewCourses() {
        return courseRepository.findAll();
    }

    public List<Course> viewCourse(){return (List<Course>) courseRepository.viewCourse();}

}

