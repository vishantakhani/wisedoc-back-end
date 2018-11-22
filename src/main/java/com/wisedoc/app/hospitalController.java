package com.wisedoc.app;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wisedoc.app.*;

@RequestMapping("WiseDocAPI/Hospital") // http:/localhost:8080/hospital
@RestController
public class hospitalController {
DAOConnection con = new DAOConnection();
	// API 1 /Hospital/appointment
	@GetMapping(path = "viewAppointment", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List> viewAppointment(@RequestParam(value = "id") String id) {

		UserData udata = new UserData();
		
		// Array Of Objects Of Patient Appointment

		return new ResponseEntity<List>( HttpStatus.OK);
	}

	// API 1 /user/login complited

	// API 2 /Hospital/addPatient Started

	@PostMapping(path = "registerPatient", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<AddPatient> AddPatient(@Valid @RequestBody AddPatient patientDetails) {
     
      String query = "INSERT INTO tblpatient(Name,DOB,Contact,Gender,Address) values('"+patientDetails.getName()+"','"+patientDetails.getDob()+"','"+patientDetails.getCno()+"','"+patientDetails.getGender()+"','"+patientDetails.getAddress()+"')";	
		con.exQuery(query);
		
      return new ResponseEntity<AddPatient>(new AddPatient(),HttpStatus.OK);
	}
	
	
	// API 2 Complited
	// API 3 Started /Hospital/addAppointment

	@PostMapping(path = "addAppointment", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<String> AddAppointment(@Valid @RequestBody AddAppointment appointmentDetails) {

		
		String query = "INSERT INTO tblappointment(ScheduleID,HospitalID,patientID,Description) values('"+appointmentDetails.getScheduleId()+"','"+appointmentDetails.getHospitalId()+"','"+appointmentDetails.getPatientId()+"','"+appointmentDetails.getDescription()+"')";
		con.exQuery(query);
		return new ResponseEntity<String>("Done",HttpStatus.OK);
	}
	
	// Completed
	
	// Started
	@GetMapping(path = "/allPatients")
	public ResponseEntity<List> AllPatient() {
		List lst = new ArrayList<AddPatient>();
		String query = "select * from tblpatient";
		ResultSet rs = con.selectQuery(query);
		try {
			while(rs.next())
			{		System.out.println("data");
				AddPatient patient = new AddPatient();
				patient.setPatientId(rs.getInt("ID"));
				patient.setName(rs.getString("Name"));
				patient.setDob(rs.getString("DOB"));
				patient.setCno(rs.getString("Contact"));
				patient.setGender(rs.getString("Gender"));
				patient.setAddress(rs.getString("Address"));
				lst.add(patient);
			}
		}catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("Going to send");
		return new ResponseEntity<List>(lst, HttpStatus.OK);
	}
	// Completed
	
	
	// Update Patient Started
	
	@PostMapping(path = "updatePatient", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<String> updatePatient(@Valid @RequestBody AddPatient patientDetails) {
       String query = "UPDATE tblpatient set Name='"+patientDetails.getName()+"',Address='"+patientDetails.getAddress()+"',Contact='"+patientDetails.getCno()+"',Gender='"+patientDetails.getGender()+"',DOB='"+patientDetails.getDob()+"' WHERE ID = "+patientDetails.getPatientId();
		con.exQuery(query);
		System.out.println("Updated With Query "+query);
       return new ResponseEntity<String>("Done",HttpStatus.OK);
	}

	
	// Update patient Completed
	
	
	// Delete Patirnt Started
	@DeleteMapping(path = "/deletePatient")
	public ResponseEntity<String> deletePatient(@RequestParam(value = "id") String id) {
		String query = "DELETE FROM tblpatient WHERE ID = "+Integer.parseInt(id);
		con.exQuery(query);
		
		return new ResponseEntity<String>("Done",HttpStatus.OK);
	}

	// Delete Patient End
	
	//APi Get All Schedule For Hospital
//	
//	@GetMapping(path = "/allSchedule")
//	public ResponseEntity<List> getSchedule(@RequestParam(value = "id") String id) {
//		System.out.println("Schedule Id "+id);
//		List lst = new ArrayList<AddSchedule>();
//		String query = "SELECT * FROM tblschedule WHERE ID = "+Integer.parseInt(id);
//		
//		ResultSet res = con.selectQuery(query);
//		
//		
//		try {
//			while(res.next())
//			{
//				System.out.println("Schedule Data");
//				AddSchedule sch = new  AddSchedule();
//				sch.setHospitalId(res.getInt("HospitalID"));
//				sch.setDate(res.getString("Date"));
//				sch.setTime(res.getString("Time"));
//				sch.setScheduleId(res.getInt("ID"));
//				lst.add(sch);
//			}
//		}catch(Exception e)
//		{
//			System.out.println(e);
//			return new ResponseEntity<List>(lst,HttpStatus.BAD_REQUEST);
//		}
//		return new ResponseEntity<List>(lst,HttpStatus.OK);
//	}
	// End For Get All


	
	@GetMapping(path = "/allSchedule")
	public ResponseEntity<List> allSchedule(@RequestParam(value = "id") String id) {
		List lst = new ArrayList<AddSchedule>();
		String query = "select * from tblschedule WHERE HospitalID = "+Integer.parseInt(id);
		ResultSet rs = con.selectQuery(query);
		try {
			while(rs.next())
			{		System.out.println("data");
				AddSchedule sch = new AddSchedule();
				sch.setHospitalId(rs.getInt("HospitalID"));
				sch.setDate(rs.getString("Date"));
				sch.setTime(rs.getString("Time"));
				sch.setScheduleId(rs.getInt("ID"));
				lst.add(sch);
				
			}
		}catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("Going to send");
		return new ResponseEntity<List>(lst, HttpStatus.OK);
	}
	
	
}
