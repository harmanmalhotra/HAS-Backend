package com.example.hostelAvailabilitySystem.service.serviceImpl;
//import com.example.hostelAvailabilitySystem.Controller;
//
import com.example.hostelAvailabilitySystem.model.*;
import com.example.hostelAvailabilitySystem.repository.*;
import lombok.AllArgsConstructor;
import com.example.hostelAvailabilitySystem.model.Room;
import com.example.hostelAvailabilitySystem.model.Warden;
import com.example.hostelAvailabilitySystem.repository.HostelRepository;
import com.example.hostelAvailabilitySystem.repository.RoomRepository;
import com.example.hostelAvailabilitySystem.repository.WardenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.support.NullValue;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import com.example.hostelAvailabilitySystem.model.SmsRequest;
import com.twilio.twiml.voice.Sms;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;


@Service
@AllArgsConstructor
@Slf4j
public class WardenService{
//    @Autowired
//    private SmsRequest smsRequest;

    @Autowired
    private RoomReqRepository roomReqRepository;
//
//    @Autowired
//    private NotificationService notificationService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCurrentStatusRepository studentCurrentStatusRepository;

    @Autowired
    private HostelRepository hostelRepository;
    @Autowired
    private WardenRepository wardenRepository;
    @Autowired
    private final AuthoritiesRepository authoritiesRepository;
    private final EmailValidator emailValidator;
    private final AppUserService appUserService;
    private StudentService studentService;

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    public String register(WardenRegistrationRequest request) {

        boolean isValidEmail = emailValidator.
                test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        Authority authority = authoritiesRepository.findByAuthority("ROLE_WARDEN").orElseThrow(() ->
                new UsernameNotFoundException("Role Not Found"));

        appUserService.signUpUser(
                new AppUser(
                        request.getEmail(),
                        request.getPassword(),
                        authority
                )
        );

//        Hostel hostel = hostelRepository.findById(request.getHostelId()).orElseThrow(() ->
//                new UsernameNotFoundException("Hostel Not Found"));

        wardenRepository.save(
                new Warden(
                        request.getName(),
                        request.getEmail(),
                        request.getPhoneNo()
//                        hostel
        ));

        return "Warden Registered";
    }

    public Warden viewWarden(String email_id){
        return wardenRepository.findByEmail(email_id).orElseThrow(() ->
                new UsernameNotFoundException("Warden not found"));
    }

    public String editWarden (String wardenName, long wardenPhoneNo,String email) {
        Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
        wardenRepository.editWarden(wardenName,wardenPhoneNo,email, dateModified);
        return "Details Updated Successfully";}

    public List<Room> viewRooms(long hostelId) {
        return roomRepository.findByHostelId(hostelId).orElseThrow(() ->
                new UsernameNotFoundException("No Rooms in Given Hostel"));
    }

    public ResponseEntity<?> addRoom(int roomNo, long roomTypeId, long hostelId) throws UsernameNotFoundException{

        if(roomRepository.findByRoomNoAndHostelId(roomNo, hostelId) > 0) {
            throw new IllegalStateException("Room No Already Present");
        }

        Hostel hostel = hostelRepository.findById(hostelId).orElseThrow(() ->
                new UsernameNotFoundException("Hostel Not Found"));

        RoomType roomType = roomTypeRepository.findById(roomTypeId).orElseThrow(() ->
                new UsernameNotFoundException("Hostel Not Found"));

        Room room = new Room(roomNo, roomType, hostel);
        roomRepository.save(room);

        Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
        hostelRepository.updateHostelRoomsByHostelId(hostel.getHostelRooms() + 1, hostel.getHostelId(), dateModified);

        return ResponseEntity.ok("Room Successfully Added");
    }

    public String editRoom(long roomId, long roomTypeId, String roomStatus) {
        if (studentCurrentStatusRepository.checkRoomIdExists(roomId) > 0) {
            return "Room is occupied";
        } else {
            Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
            roomRepository.editRoom(roomId,roomTypeId, roomStatus, dateModified);
            return "Room Updated";
        }
    }

    public List<RoomType> viewRoomTypes() {
        return roomTypeRepository.findAll();
    }

    public List<RoomType> viewRoomTypesByHostelId(long hostelId) {
        return roomTypeRepository.viewRoomType(hostelId);
    }

    public String addRoomType(String roomName, int roomFee, int roomCapacity) {

        boolean roomTypeExits = roomTypeRepository.findByRoomName(roomName).isPresent();

        if (!roomTypeExits) {
            roomTypeRepository.save(
                    new RoomType(
                            roomName,
                            roomFee,
                            roomCapacity
                    )
            );
            return "Room Type Added Successfully";
        } else {
            return "Room Type Already Present";
        }
    }

    public boolean checkIsRoomTypeFree(long roomTypeId) {
        if (studentCurrentStatusRepository.checkRoomTypeExists(roomTypeId) > 0) return false;
        else return true;
    }

    public String editRoomType(long roomTypeId, String roomName, int roomFee, int roomCapacity) {
        Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
        roomTypeRepository.editRoomType(roomTypeId, roomName, roomFee, roomCapacity, dateModified);
        return "Room Type Edited Successfully";
    }

    public String removeRoomType(long roomTypeId) {
        if(studentCurrentStatusRepository.checkRoomTypeExists(roomTypeId) > 0) {
            return "Cannot Remove RoomType, Since students are assigned";
        }
        if(roomRepository.checkRoomTypeExists(roomTypeId) > 0) {
            return "Cannot Remove RoomType, Since Rooms are assigned";
        }
        roomTypeRepository.removeRoomType(roomTypeId);
        return "Room Type Removed Successfully";
    }

    public List<Student> viewHostelStudents(long hostelId) {
        return studentRepository.viewHostelStudents(hostelId);
    }

    public List<Student> viewRoomStudents(long roomId) {
        return studentRepository.findStudentsByRoomId(roomId);
    }

    public List<Student> viewVacateHostelStudents(long hostelId) {
        return studentRepository.viewVacateHostelStudents(hostelId);
    }

    public List<Student> viewNonHostelStudents() {
        return studentRepository.viewNonHostelStudents();
    }

    public List<RoomReq> viewAllotRoomReq(long hostelId) {
        return roomReqRepository.findRoomReqByHostelId(hostelId);
    }

    @Transactional
    public StudentCurrentStatus allotRoom(long studentId) throws UsernameNotFoundException {
        RoomReq roomReq = roomReqRepository.viewRoomReq(roomReqRepository.getReqId(studentId));

        Room room = roomRepository.allotRoom(roomReq.getHostel().getHostelId(), roomReq.getRoomType().getRoomTypeId(), "UnBlocked");

        Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
        roomRepository.occupyBed(room.getRoomId(), dateModified);

        StudentCurrentStatus studentCurrentStatus = new StudentCurrentStatus(roomReq.getStudent(),
                                                roomReq.getHostel(), room, "Paid", "I");

        studentCurrentStatusRepository.save(studentCurrentStatus);

        roomReqRepository.deleteById(roomReq.getReqId());

        StudentCurrentStatus studentCurrentStatus1=studentCurrentStatusRepository.studentInfo(studentId);
//        studentCurrentStatus1.getStudent().getStudentPhoneNo()

//        smsRequest = new SmsRequest("+918121182160","Alert: your room "+ room.getRoomNo() +" has been alloted.");
//        notificationService.sendSms(smsRequest);

        return studentCurrentStatus1;
    }

    @Transactional
    public StudentCurrentStatus allotRoomDirectly(long hostelId, long roomTypeId, long studentId)
            throws UsernameNotFoundException{

        Room room = roomRepository.allotRoom(hostelId, roomTypeId, "UnBlocked");
        Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
        roomRepository.occupyBed(room.getRoomId(), dateModified);
        Student student = studentRepository.findById(studentId).orElseThrow(() ->
                new UsernameNotFoundException("Student Not Found"));
        Hostel hostel = hostelRepository.findById(hostelId).orElseThrow(() ->
                new UsernameNotFoundException("Hostel Not Found"));
        StudentCurrentStatus studentCurrentStatus = new StudentCurrentStatus(student,
                hostel, room, "Paid", "False");
        studentCurrentStatusRepository.save(studentCurrentStatus);
        StudentCurrentStatus studentCurrentStatus1=studentCurrentStatusRepository.studentInfo(studentId);
        return studentCurrentStatus1;
    }

    public List<StudentCurrentStatus> viewVacateRoomReq(long hostelId) {
        return studentCurrentStatusRepository.viewVacateRoomReq(hostelId);
    }

    @Transactional
    public StudentCurrentStatus vacateRoom(long studentId) {

        long roomId = studentCurrentStatusRepository.getRoomIdByStudentId(studentId);
        Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());
        roomRepository.makeBedEmpty(roomId, dateModified);
        StudentCurrentStatus studentCurrentStatus1=studentCurrentStatusRepository.studentInfo(studentId);
        studentCurrentStatusRepository.vacateRoom(studentId);
        return studentCurrentStatus1;
    }


    public String removeRoomReq(long roomReqID) {
        roomReqRepository.deleteById(roomReqID);
        return "Room Req Deleted Successfully";
    }
}
