package com.example.hostelAvailabilitySystem.controller;

import com.example.hostelAvailabilitySystem.model.*;
import com.example.hostelAvailabilitySystem.service.serviceImpl.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RestController
@RequestMapping(path = "student")
@CrossOrigin
public class StudentController {
    @Autowired
    private StudentService studentService;

//    @GetMapping("/viewCourse")
//    public List<Course> viewCourse(){return (List<Course>) studentService.viewCourse();}

    @PostMapping("/registration")
    public String register(@RequestBody StudentRegistrationRequest request) {
        return studentService.register(request);
    }

    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping("/viewFee")
    public int viewFee(@RequestParam("roomTypeId") long roomTypeId){
        return (int) studentService.viewFee(roomTypeId);
    };

    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping("/payFee")
    public String payFee(){
        return "Fees Paid Successfully!";
    };

    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @PostMapping("/roomReq/{roomTypeId}")
    public String roomReq(@PathVariable long roomTypeId,
                          @RequestParam("studentEmail") String studentEmail,
                          @RequestParam("hostelId") long hostelId){
        return (String) studentService.roomReq(studentEmail, roomTypeId, hostelId);
    };

    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping("/viewProfile/{email}")
    public Student viewStudentProfile(@PathVariable String email){
        return (Student) studentService.viewStudentProfile(email);
    };

    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping("/getHostelInfo/{hostelId}")
    public Hostel getHostelInfo(@PathVariable long hostelId){
        return (Hostel) studentService.getHostelInfo(hostelId);};


    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping("/getRoomInfo/{roomId}")
    public Room getRoomInfo(@PathVariable long roomId){
        return (Room) studentService.getRoomInfo(roomId);};

    @PreAuthorize("hasAnyAuthority('ROLE_STUDENT','ROLE_ADMIN')")
    @PutMapping("/editProfile/{email}")
    public String editStudentProfile(@PathVariable String email,
                                     @RequestParam String newStudentName,
                                     @RequestParam long newPhoneNo){
        return (String) studentService.editStudentProfile(email,newStudentName,newPhoneNo);};

    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping("/genderHostelView/{genderHostelType}")
    public List<Hostel> genderHostelView(@PathVariable String genderHostelType){
        return (List<Hostel>) studentService.genderHostelView(genderHostelType);
    };

    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping("/viewRoomType/{hostelId}")
    public List<RoomType> viewRoomType(@PathVariable long hostelId){
        return (List<RoomType>) studentService.viewRoomType(hostelId);
    };

    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @PutMapping("/vacateRequest/{studentCurrentStatusId}")
    public String vacateRequest(@PathVariable long studentCurrentStatusId){
        return (String) studentService.vacateRequest(studentCurrentStatusId);
    };

    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping("/assignedInfo/{studentId}")
    public StudentCurrentStatus viewStudentCurrentStatus(@PathVariable long studentId){
        return (StudentCurrentStatus) studentService.viewStudentCurrentStatus(studentId);
    };

    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping("/viewHostels")
    public List<Hostel> viewHostels(){
        return  studentService.viewHostels();
    };

    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    @GetMapping("/viewRoomReq/{studentId}")
    public boolean viewRoomReq(@PathVariable long studentId) {
        return  studentService.viewRoomReq(studentId);
    };

    @GetMapping("/viewCourses")
    public List<Course> viewCourses() {
        return  studentService.viewCourses();
    };

}
