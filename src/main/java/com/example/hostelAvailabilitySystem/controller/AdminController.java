package com.example.hostelAvailabilitySystem.controller;

import com.example.hostelAvailabilitySystem.model.*;
import com.example.hostelAvailabilitySystem.service.serviceImpl.AdminService;
import com.example.hostelAvailabilitySystem.service.serviceImpl.WardenService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Transactional
@RestController
@RequestMapping(path = "admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private WardenService wardenService;


    //
    // Admin Course Functionalities
//    @GetMapping("/viewCourse")
//    public List<Course> viewCourse(){return (List<Course>) adminService.viewCourse();}

    @PostMapping("/addCourse")
    public String addCourse(@RequestBody Course course){
        return (String) adminService.addCourse(course);
    };

    @PutMapping("/editCourse")
    public String editCourse(@RequestBody Course course ){
        return (String) adminService.editCourse(course);
    };

    @DeleteMapping("/deleteCourse/{courseId}")
    public String deleteCourse(@PathVariable long courseId){
        return (String) adminService.deleteCourse(courseId);
    };

    //
    // Admin Hostel Functionalities.

    @GetMapping("/viewHostels")
    public List<Hostel> viewHostel(){
        return  adminService.viewHostel();
    }

    @GetMapping("/viewHostels1")
    public List<Hostel> viewHostel1(){
        return  adminService.viewHostel1();
    }

    @GetMapping("/viewHostels2")
    public List<Warden> viewHostel2(){
        return  adminService.viewHostel2();
    }


    @PostMapping("/addHostel")
    public String addHostel(@RequestBody Hostel hostel) {
        return adminService.addHostel(new Hostel(hostel.getHostelName(),
                hostel.getHostelType(),
                hostel.getHostelStatus(),
                hostel.getHostelRooms())); }

    @GetMapping("/getHostelId")
    public long getHostelId(@RequestParam String hostelName,
                            @RequestParam int hostelRooms,
                            @RequestParam String hostelStatus,
                            @RequestParam String hostelType
                            ) {
        return adminService.getHostelId(hostelName,hostelRooms,hostelStatus,hostelType);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/wardenRegistration")
    public String register(@RequestBody WardenRegistrationRequest request) {
        System.out.println(request);
        return wardenService.register(request);
    }

    @PutMapping("/editHostelName/{hostelId}")
    public String editHostel(@PathVariable long hostelId,
                             @RequestParam String hostelName){
        return (String) adminService.editHostelName(hostelName,hostelId);}

    @PutMapping("/blockHostel/{hostelId},{check}")
    public String blockHostel(@PathVariable long hostelId,@PathVariable String check){
        String newHostelStatus="Blocked";
        return (String) adminService.blockHostel(hostelId, check, newHostelStatus);}

    @PutMapping("/releaseHostel/{hostelId},{check}")
    public String releaseHostel(@PathVariable long hostelId,@PathVariable String check){
        String newHostelStatus="UnBlocked";
        return (String) adminService.releaseHostel(hostelId,check, newHostelStatus);}

    @DeleteMapping("/removeHostel/{hostelId}")
    public String removeHostel(@PathVariable long hostelId) {
        return adminService.removeHostel(hostelId);}

    @DeleteMapping("/unmapWarden/{hostelId}")
    public String unmapWarden(@PathVariable long hostelId) {
        return adminService.unmapWarden(hostelId);}

    //
    // Admin Student Functionalities
    @GetMapping("/viewStudents")
    public List<Student> viewStudents() {return (List<Student>) adminService.viewStudents();}

    @GetMapping("/viewWardens")
    public List<Warden> viewWardens() {return adminService.viewWardens();}

    @GetMapping("/viewWardens1")
    public List<Warden> viewWardens1() {return adminService.viewWardens1();}

    @GetMapping("/viewWardens2")
    public List<Warden> viewWardens2() {return adminService.viewWardens2();}

    @PutMapping("/mapWarden/{hostelId}")
    public String mapWarden(@PathVariable long hostelId,
                            @RequestParam String email){
        return adminService.mapWarden(hostelId,email);
    }

    @DeleteMapping("/removeWarden/{email}")
    public String removeWarden(@PathVariable String email) {
        return adminService.removeWarden(email);}

    @PutMapping("/mapCourse/{courseID}")
    public String mapCourse(@PathVariable long courseID,
                            @RequestParam String email){
        return adminService.mapCourse(courseID,email);
    }

//    @DeleteMapping("/removeWarden/{email}")
//    public String removeStudent(@PathVariable String email) {
//        return adminService.removeWarden(email);}

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PutMapping("/editStudent/{email}")
    public String editStudent(@PathVariable String email,
                              @RequestParam String studentName,
                              @RequestParam long studentPhoneNo,
                              @RequestParam String rollNo,
                              @RequestParam String gender,
                              @RequestParam long courseID){
        return (String) adminService.editStudent(email,studentName,studentPhoneNo,rollNo,
                gender,courseID);};


    @DeleteMapping("/removeStudent/{studentId},{email}")
    public String removeStudent(@PathVariable long studentId, @PathVariable String email) {
        return adminService.removeStudent(studentId,email);}

}

