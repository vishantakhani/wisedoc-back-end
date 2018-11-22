package com.wisedoc.app;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.tomcat.util.digester.SetPropertiesRule;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("WiseDocAPI/Doctor") // http:/localhost:8080/admin
@RestController

public class adminController {
	DAOConnection con = new DAOConnection();
// API 1 /doctor/registerHospital

	@PostMapping(path = "registerHospital", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<String> registerHospital(@Valid @RequestBody HospitalData hospitalDetails) {
//		DAOConnection con = new DAOConnection();

		System.out.println("request got");
		try {

			String query = "INSERT into tblhospital (Name, Address, Email, Contact, ContactPerson, Password, IsActive) VALUES "
					+ "('" + hospitalDetails.getName() + "','" + hospitalDetails.getAddress() + "','"
					+ hospitalDetails.getEmail() + "','" + hospitalDetails.getcontactNo() + "','"
					+ hospitalDetails.getPersonToContact() + "','" + hospitalDetails.getPassword() + "',true)";
			con.exQuery(query);
			System.out.println("inserted");
			return new ResponseEntity<String>("Done", HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<String>("Done", HttpStatus.NOT_FOUND);
		}

	}

	// API 1 Complited

	// API 2 Started /doctor/editHospital

	@PostMapping(path = "updateHospital", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<String> editHospital(@Valid @RequestBody HospitalData hospitalDetails) {
		String query = "UPDATE tblhospital set Name='" + hospitalDetails.getName() + "',Address='"
				+ hospitalDetails.getAddress() + "',Email='" + hospitalDetails.getEmail() + "',Contact='"
				+ hospitalDetails.getcontactNo() + "',ContactPerson='" + hospitalDetails.getPersonToContact()
				+ "' WHERE ID = " + hospitalDetails.gethospitalId();
		con.exQuery(query);
		System.out.println("Updated With Query " + query);
		return new ResponseEntity<String>("Done", HttpStatus.OK);
	}

	// API 2 complited

	// API 3 Started /doctor/appointment

	@GetMapping(path = "allAppointments")
	public ResponseEntity<List> allAppointment() {
		System.out.println("Get Appointment Called");

		String query = "SELECT a.id AppointmentId,a.Description Description,h.id HospitalId, p.ID PatientId, p.Name PatientName, s.ID ScheduleId, s.date Date,s.Time Time FROM tblappointment a, tblpatient p, tblhospital h, tblschedule s WHERE s.ID = a.ScheduleID and a.PatientID=p.ID and a.HospitalID=h.ID and s.Date = curdate()";
		ResultSet rs = con.selectQuery(query);
		List lst = new ArrayList<AppointmentData>();
		try {
			while (rs.next()) {
					AppointmentData adata = new AppointmentData();
					adata.setHospitalId(rs.getInt("HospitalId"));
					adata.setPatientId(rs.getInt("PatientId"));
					adata.setPatientName(rs.getString("PatientName"));
					adata.setScheduleId(rs.getInt("ScheduleId"));
					adata.setDate(rs.getString("Date"));
					adata.setTime(rs.getString("Time"));
					adata.setDescription(rs.getString("Description"));
					lst.add(adata);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return new ResponseEntity<List>(lst,HttpStatus.OK);
	}

	// API 3 complited

	// API 4 Started /Doctor/addSchedule

	@PostMapping(path = "addSchedule", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<String> addSchedule(@Valid @RequestBody AddSchedule scheduleDetails) {

		System.out.println("Got Request To ADd Schedule");
		String query = "INSERT INTO tblschedule(Date,Time,IsCancel,HospitalID) VALUES('" + scheduleDetails.getDate()
				+ "','" + scheduleDetails.getTime() + "',false,'" + scheduleDetails.getHospitalId() + "')";
		con.exQuery(query);
		System.out.println(query);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	// API 4 Completed

	// API 5 Started /Doctor/addHistory

	@PostMapping(path = "addHistory", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<String> AddHistory(@Valid @RequestBody AddAppointment historyDetails) {

		return new ResponseEntity<>(HttpStatus.OK);
	}
	// API 5 completed

	// API 6 Started
	@GetMapping(path = "/allhospitals")
	public ResponseEntity<List> SeeHospitals() {
		List lst = new ArrayList<HospitalData>();
		String query = "SELECT * FROM tblhospital";
		System.out.println("Request Come ALl Hospitals");
		ResultSet rs = con.selectQuery(query);
		try {
			while (rs.next()) {
				System.out.println("Request Come ALl Hospitals ADding in list");
				HospitalData hdata = new HospitalData();
				hdata.sethospitalId(rs.getInt("ID"));
				hdata.setName(rs.getString("Name"));
				hdata.setEmail(rs.getString("Email"));
				hdata.setAddress(rs.getString("Address"));
				hdata.setcontactNo(rs.getString("Contact"));
				hdata.setPersonToContact(rs.getString("ContactPerson"));
				lst.add(hdata);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Request Come ALl Hospitals Going To Send Back");
		return new ResponseEntity<List>(lst, HttpStatus.OK);
	}

	// API 6 Completed

// API 7 Started
	@DeleteMapping(path = "/deleteHospital")
	public ResponseEntity<String> deleteHospitals(@RequestParam(value = "id") String id) {
		String query = "DELETE FROM tblhospital WHERE ID=" + Integer.parseInt(id);
		con.exQuery(query);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	// API 7 Completed
// Get Patient Hoistory Started
	@GetMapping(path = "/patientHistory")
	public ResponseEntity<List> getPatientHistory() {
				return new ResponseEntity<List>( HttpStatus.OK);
	}


	//Get Patirnt History End
}
