package com.ryanmewhorter.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "shows")
public class Show extends AuditModel {

	private static final long serialVersionUID = 1957822620317460810L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private Date date;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "shows_artists", joinColumns = { @JoinColumn(name = "show_id") }, inverseJoinColumns = {
			@JoinColumn(name = "artist_id") })
	@JsonIgnore
	private List<Artist> artists = new ArrayList<>();

	@NotNull
	private String venue;

	public List<Artist> getArtists() {
		return artists;
	}

	public Date getDate() {
		return date;
	}

	public Long getId() {
		return id;
	}

	public String getVenue() {
		return venue;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

}
