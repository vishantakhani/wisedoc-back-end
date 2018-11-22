package com.wisedoc.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("WiseDocAPI/User") // http:/localhost:8080/
@RestController

public class generalController {
	DAOConnection con = new DAOConnection();
//	private JdbcTemplate jdbcTemplate;
// real work started
//API 1  /user/login

	@PostMapping(path = "login", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserData> createUser(@Valid @RequestBody UserData userDetails) {

		ResultSet rs = null;
		UserData udata = new UserData();
		System.out.println("Got Request" + userDetails.getEmail() + " pass : " + userDetails.getPassword());
		if (userDetails.isType()) {
			System.out.println("is doctor");
			String str = "select ID,FirstName,LastName from tbldoctor where Email='" + userDetails.getEmail()
					+ "' AND Password='" + userDetails.getPassword() + "'";
			rs = con.selectQuery(str);
			try {
				if (rs.next() == true) {
					System.out.println("got data");
					udata.setUserId(rs.getInt("ID"));
					udata.setUserName(rs.getString("FirstName") + " " + rs.getString("LastName"));
					udata.setEmail(userDetails.getEmail());
					udata.setType(userDetails.isType());
					return new ResponseEntity<UserData>(udata, HttpStatus.OK);
				} else {
					System.out.println("did not get data");
					return new ResponseEntity<UserData>(udata, HttpStatus.NOT_FOUND);
				}
			} catch (Exception e) {
				System.out.println(e);
			}

		} else {
			System.out.println("is hospital");
			String str = "select ID,Name from tblhospital where Email='" + userDetails.getEmail() + "' AND Password='"
					+ userDetails.getPassword() + "'";
			rs = con.selectQuery(str);
			try {
				if (rs.next() == true) {
					udata.setUserId(rs.getInt("ID"));
					udata.setUserName(rs.getString("Name"));
					udata.setEmail(userDetails.getEmail());
					udata.setType(userDetails.isType());
					return new ResponseEntity<UserData>(udata, HttpStatus.OK);
				} else {
					return new ResponseEntity<UserData>(udata, HttpStatus.NOT_FOUND);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return new ResponseEntity<UserData>(udata, HttpStatus.NOT_FOUND);
	}

	// API 1 /user/login complited

	// API 2 /User/appointment
	@GetMapping(path = "/appointment")
	public ResponseEntity<AppointmentData> SeeAppointment(@RequestParam(value = "id") int id) {

		AppointmentData adata = new AppointmentData();
		String query = "select * from tblappointment where HospitalID=" + id + "";
		try {
			ResultSet rs = con.selectQuery(query);
			if (rs.next() == true) {
				System.out.println("Data Got");
				adata.setHospitalId(id);
				adata.setDate("" + rs.getDate("Date"));
				adata.setTime("" + rs.getTime("Time"));
				return new ResponseEntity<AppointmentData>(adata, HttpStatus.OK);
			}
		} catch (Exception e) {
			System.out.println("Error " + e);
			return new ResponseEntity<AppointmentData>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<AppointmentData>(HttpStatus.NOT_FOUND);
	}

}