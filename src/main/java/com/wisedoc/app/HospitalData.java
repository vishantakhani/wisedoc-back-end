package com.wisedoc.app;

public class HospitalData {
	private int hospitalId;

	public int gethospitalId() {
		return hospitalId;
	}

	public void sethospitalId(int id) {
		this.hospitalId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getcontactNo() {
		return contactNo;
	}

	public void setcontactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getPersonToContact() {
		return personToContact;
	}

	public void setPersonToContact(String personToContact) {
		this.personToContact = personToContact;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private String name;
	private String email;
	private String contactNo;
	private String personToContact;
	private String password;
	private String address;
}
