package com.example.hostelAvailabilitySystem.controller;

import com.example.hostelAvailabilitySystem.model.*;
import com.example.hostelAvailabilitySystem.service.serviceImpl.WardenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "warden")
@PreAuthorize("hasAnyAuthority('ROLE_WARDEN','ROLE_ADMIN')")
@AllArgsConstructor
@CrossOrigin
public class WardenController {
    private SmsRequest smsRequest;

    @Autowired
    private NotificationController notificationController;

    @Autowired
    private WardenService wardenService;

//    @Autowired
//    private StudentCurrentStatus


    @GetMapping("/viewWarden/{email_id}")
    public Warden viewWardenString(@PathVariable String email_id){
        return wardenService.viewWarden(email_id);
    };

    @PutMapping("/editWarden/{wardenName}, {wardenPhoneNo}, {wardenEmail}")
    public String editWarden (@PathVariable String wardenName,
                             @PathVariable long wardenPhoneNo,
                             @PathVariable String wardenEmail) {
        return wardenService.editWarden(wardenName, wardenPhoneNo, wardenEmail);
    }

    @GetMapping("/viewRooms/{hostelId}")
    public List<Room> viewRooms(@PathVariable long hostelId) {
        return wardenService.viewRooms(hostelId);
    }

    @PostMapping("/addRoom/{roomNo}, {roomTypeId}, {hostelId}")
    public ResponseEntity<?> addRoom(@PathVariable int roomNo,
                                  @PathVariable long roomTypeId,
                                  @PathVariable long hostelId){
        return wardenService.addRoom(roomNo, roomTypeId,hostelId);
    }

    @PutMapping("/editRoom/{roomId}, {roomTypeId}, {roomStatus}")
    public String editRoom(@PathVariable long roomId,
                           @PathVariable long roomTypeId,
                           @PathVariable String roomStatus){
        return wardenService.editRoom(roomId, roomTypeId, roomStatus);
    }

    @GetMapping("/viewRoomTypes")
    public List<RoomType> viewRoomTypes() {
        return wardenService.viewRoomTypes();
    }

    @GetMapping("/viewRoomTypesByHostelId/{hostelId}")
    public List<RoomType> viewRoomTypesByHostelId(@PathVariable long hostelId){
        return  wardenService.viewRoomTypesByHostelId(hostelId);
    };

    @PostMapping("/addRoomType/{roomName}, {roomFee}, {roomCapacity}")
    public String addRoomType(@PathVariable String roomName, @PathVariable int roomFee,
                              @PathVariable int roomCapacity) {
        return wardenService.addRoomType(roomName,roomFee, roomCapacity);
    };

    @GetMapping("/checkRoomType/{roomTypeId}")
    public ResponseEntity<Boolean> isRoomTypeFree(@PathVariable long roomTypeId) {
        return ResponseEntity.ok().body(wardenService.checkIsRoomTypeFree(roomTypeId));
    }

    @PutMapping("/editRoomType/{roomTypeId}, {roomName}, {roomFee}, {roomCapacity}")
    public String editRoomType(@PathVariable long roomTypeId,
                               @PathVariable String roomName,
                               @PathVariable int roomFee,
                               @PathVariable int roomCapacity){
        return wardenService.editRoomType(roomTypeId, roomName, roomFee, roomCapacity);
    }

    @DeleteMapping("/removeRoomType/{roomTypeId}")
    public String removeRoomType(@PathVariable long roomTypeId) {
        return wardenService.removeRoomType(roomTypeId);
    }

    @GetMapping("/viewHostelStudents/{hostelId}")
    public List<Student> viewHostelStudents(@PathVariable long hostelId){
        return wardenService.viewHostelStudents(hostelId);
    }

    @GetMapping("/viewRoomStudents/{roomId}")
    public List<Student> viewRoomStudents(@PathVariable long roomId){
        return wardenService.viewRoomStudents(roomId);
    }

    @GetMapping("/viewVacateHostelStudents/{hostelId}")
    public List<Student> viewVacateHostelStudents(@PathVariable long hostelId){
        return wardenService.viewVacateHostelStudents(hostelId);
    }

    @GetMapping("/viewNonHostelStudents")
    public List<Student> viewNonHostelStudents(){
        return wardenService.viewNonHostelStudents();
    }

//    @PostMapping("/registerNewStudent")
//    public String registerNewStudent(@RequestBody StudentRegistrationRequest request) {
//        return wardenService.registerStudent(request);
//    }

    @GetMapping("/viewAllotRoomReq/{hostelId}")
    public List<RoomReq> viewAllotRoomReq(@PathVariable long hostelId){
        return wardenService.viewAllotRoomReq(hostelId);
    }

    @PostMapping("/allotRoom/{studentId}")
    public String allotRoom(@PathVariable long studentId){
        StudentCurrentStatus studentCurrentStatus1 = wardenService.allotRoom(studentId);
        String Messege="Dear "+studentCurrentStatus1.getStudent().getStudentName()+", Your Alloted room details are: Hostel Name-"+studentCurrentStatus1.getHostel().getHostelName()+" Room No-"+studentCurrentStatus1.getRoom().getRoomNo();

        smsRequest = new SmsRequest("+91"+studentCurrentStatus1.getStudent().getStudentPhoneNo(),"Alert:"+Messege);
        notificationController.sendSms(smsRequest);
        return "Room Allotted";
    }

    @PostMapping("/allotRoomDirectly/{hostelId}, {roomTypeId}, {studentId}")
    public String allotRoomDirectly(@PathVariable long hostelId,
                                    @PathVariable long roomTypeId,
                                    @PathVariable long studentId){
        StudentCurrentStatus studentCurrentStatus1=wardenService.allotRoomDirectly(hostelId, roomTypeId, studentId);
        String Messege="Dear "+studentCurrentStatus1.getStudent().getStudentName()+", Your Alloted room details are: Hostel Name-"+studentCurrentStatus1.getHostel().getHostelName()+" Room No-"+studentCurrentStatus1.getRoom().getRoomNo();
        smsRequest = new SmsRequest("+91"+studentCurrentStatus1.getStudent().getStudentPhoneNo(),"Alert:"+Messege);
        notificationController.sendSms(smsRequest);
        return "Room Allotted";
    }

    @GetMapping("/viewVacateRoomReq/{hostelId}")
    public List<StudentCurrentStatus> viewVacateRoomReq(@PathVariable long hostelId){
        return wardenService.viewVacateRoomReq(hostelId);
    }

    @DeleteMapping("/vacateRoom/{studentId}")
    public String vacateRoom(@PathVariable long studentId){
        StudentCurrentStatus studentCurrentStatus1=wardenService.vacateRoom(studentId);
        String Messege="Dear"+studentCurrentStatus1.getStudent().getStudentName()+",Your room has been vacated with details: Hostel Name-"+studentCurrentStatus1.getHostel().getHostelName()+" Room No-"+studentCurrentStatus1.getRoom().getRoomNo();
        smsRequest = new SmsRequest("+91"+studentCurrentStatus1.getStudent().getStudentPhoneNo(),"Alert:"+Messege);
        notificationController.sendSms(smsRequest);
        return "Room Vaccated";
    }

    @DeleteMapping("/removeRoomReq/{roomReqID},{studentName},{studentPhoneNo}")
    public String removeRoomReq(@PathVariable long roomReqID,
                                @PathVariable String studentName,
                                @PathVariable long studentPhoneNo){

        wardenService.removeRoomReq(roomReqID);
        String Messege="Dear"+studentName+",Your roomReq has been cancelled due to unavailability. Please raise the request again!";
        smsRequest = new SmsRequest("+91"+studentPhoneNo,"Alert:"+Messege);
        notificationController.sendSms(smsRequest);
        return "Room Requested Deleted, Due to Unavailability";
    }

}
