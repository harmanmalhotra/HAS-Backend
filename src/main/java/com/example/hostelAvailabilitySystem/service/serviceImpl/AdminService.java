package com.example.hostelAvailabilitySystem.service.serviceImpl;

import com.example.hostelAvailabilitySystem.model.*;
import com.example.hostelAvailabilitySystem.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminService{

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private HostelRepository hostelRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoomReqRepository roomReqRepository;

    @Autowired
    private WardenRepository wardenRepository;

    @Autowired
    private StudentCurrentStatusRepository studentCurrentStatusRepository;

    @Autowired RoomRepository roomRepository;

    @Autowired
    private AppUserRepository appUserRepository;



    //
    // Admin Course Functionalities
    public List<Course> viewCourse(){return (List<Course>) courseRepository.viewCourse();}

    public String addCourse(Course course){courseRepository.save(course);
        return "Course Added Successfully";}

    public String editCourse(Course course){
        Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
        courseRepository.editCourse(course.getCourseID(),course.getCourseName(), course.getCourseDuration(), dateModified);
        return "Course Edited Successfully";
    }

    public String deleteCourse(long courseId){
        int count= courseRepository.checkCourse(courseId);
        System.out.println(count);
        if (count==0){
//            adminRepository.deleteByCourseName(x);
            courseRepository.removeCourse(courseId);
            return "Course Deleted Successfully";
        } else {
            return "Cannot Remove Course Since Students are Present";
        }
    }

    //
    // Admin Hostel Functionalities

    public List<Hostel> viewHostel(){
        return hostelRepository.viewHostel();
    }
    public List<Hostel> viewHostel1(){
        return hostelRepository.viewHostel1();
    }

    public List<Warden> viewHostel2(){
        return wardenRepository.viewHostel2();
    }

    public String addHostel(Hostel hostel){
        hostelRepository.save(hostel);
        return "Hostel Added Successfully";}

    public long getHostelId(String hostelName,int hostelRooms, String hostelStatus, String hostelType){
        return hostelRepository.getHostelId(hostelName,hostelRooms,hostelStatus,hostelType);
        }

    public String editHostelName(String hostelName, long hostelId){
        Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
        hostelRepository.editHostelName(hostelName,hostelId, dateModified);
    return "Hostel Name Changed Successfully";}

    public String blockHostel(long hostelId, String check, String newHostelStatus){
        if (check.equals(newHostelStatus)){return "Already in Block State";}
        int studentChecker=studentCurrentStatusRepository.checkHostelStudents(hostelId);
        if (studentChecker==0){
            Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
        hostelRepository.blockHostel(newHostelStatus,hostelId, dateModified);
    return "Hostel Blocked Successfully";} else {
        return "Cannot Block Hostel Since Students are Present";
    }}

    public String releaseHostel(long hostelId, String check, String newHostelStatus){
        if (check.equals(newHostelStatus)){
            return "Already in Released State";
        }
        Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
        hostelRepository.releaseHostel(newHostelStatus,hostelId, dateModified);
        return "Hostel Released Successfully";}

    public String removeHostel(long hostelId){
        int studentChecker=studentCurrentStatusRepository.checkHostelStudents(hostelId);
        if (studentChecker==0){
            Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
            wardenRepository.unMapWarden(hostelId, dateModified);
            roomReqRepository.removeRoomReqs(hostelId);
            roomRepository.removeRooms(hostelId);
            hostelRepository.removeHostel(hostelId);
            return "Hostel Removed Successfully";}
        else {
            return "Cannot Remove Hostel Since Students are Present";
        }
    }

    public String removeWarden(String email){
        int studentChecker=studentCurrentStatusRepository.checkHostelStudentsWarden(email);
        if (studentChecker==0){
            Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
            wardenRepository.unMapWarden2(email, dateModified);
            wardenRepository.removeWarden(email);
            appUserRepository.removeUser(email);
            return "Warden Removed Successfully";}
        else {
            return "Cannot Remove Warden Since Students are Present in assigned Hostel";
        }
    }


    public String removeStudent(long studentId,String email){
        int studentChecker=studentCurrentStatusRepository.checkCurrentStudents(studentId);
        if (studentChecker==0){
            roomReqRepository.removeStudentRoomReqs(studentId);
            studentRepository.removeStudent(studentId);
            appUserRepository.removeUser(email);
            return "Student Removed Successfully";}
        else {
            return "Cannot Remove Student Since Hostel is Alloted";
        }
    }

    public String unmapWarden(long hostelId){
        Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
        wardenRepository.unMapWarden(hostelId, dateModified);
        return "Sucessfully";
    }



    //
    // Admin Student Functionalities
    public List<Student> viewStudents() {
        return (List<Student>) studentRepository.viewStudents();
    }

    public List<Warden> viewWardens(){
        return (List<Warden>) wardenRepository.viewWardens();}

    public List<Warden> viewWardens1(){
        return (List<Warden>) wardenRepository.viewWardens1();}

    public List<Warden> viewWardens2(){
        return (List<Warden>) wardenRepository.viewWardens2();}

    public String mapWarden(long hostelId,String email){
        Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
        wardenRepository.unMapWarden(hostelId, dateModified);
        wardenRepository.mapWarden(hostelId,email, dateModified);
        return "Warden Mapped Successfully!";
    }

    public String mapCourse(long courseID,String email){
        Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
        studentRepository.mapCourse(courseID, email, dateModified);
        return "Warden Mapped Successfully!";
    }


    public String removeStudent(String email){
        int studentChecker=studentCurrentStatusRepository.checkHostelStudentsWarden(email);
        if (studentChecker==0){
            Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
            wardenRepository.unMapWarden2(email, dateModified);
            wardenRepository.removeWarden(email);
            return "Student Removed Successfully";}
        else {
            return "Cannot Remove Warden Since Students are Present in assigned Hostel";
        }
    }

    public String editStudent(String email,
                              String studentName,
                              long studentPhoneNo,
                              String rollNo,
                              String gender,
                              long courseID){
        Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
        studentRepository.editStudent(email, studentName, studentPhoneNo, rollNo, gender,courseID, dateModified);
        return "Student Edited Successfully";}
}

