package com.ryanmewhorter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "venues")
public class Venue extends AuditModel {

	private static final long serialVersionUID = -6862072473069400808L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String streetAddress;

	private String unit;

	@NotNull
	private String city;

	@NotNull
	@Size(min = 2, max = 2)
	private String state;

	@NotNull
	@Size(min = 5, max = 9)
	private String zipCode;

	public String getCity() {
		return city;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getState() {
		return state;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public String getUnit() {
		return unit;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}
