package com.deduplication.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="customer")
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="primarykeycolumn")
	private Long primaryKeyColumn;
	@Column(name="id")
	private Long id;
	@Column(name="firstname")
	private String firstName;
	@Column(name="lastname")
	private String lastName;
	@Column(name="company")
	private String company;
	@Column(name="email")
	private String email;
	@Column(name="address1")
	private String address1;
	@Column(name="address2")
	private String address2;
	@Column(name="zip")
	private String zip;
	@Column(name="city")
	private String city;
	@Column(name="statelong")
	private String stateLong;
	@Column(name="state")
	private String state;
	@Column(name="phone")
	private String phone;
	@Column(name="groupid")
	private Long groupId;
	
	public Long getPrimaryKeyColumn() {
		return primaryKeyColumn;
	}
	public void setPrimaryKeyColumn(Long primaryKeyColumn) {
		this.primaryKeyColumn = primaryKeyColumn;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStateLong() {
		return stateLong;
	}
	public void setStateLong(String stateLong) {
		this.stateLong = stateLong;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

}
