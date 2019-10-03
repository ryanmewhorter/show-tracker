package com.ryanmewhorter.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ryanmewhorter.model.Venue;
import com.ryanmewhorter.repository.VenueRepository;

@RestController
@RequestMapping("/venues")
public class VenueController {

	@Autowired
	private VenueRepository venueRepository;

	@PostMapping
	public ResponseEntity<Venue> createVenue(@RequestBody Venue venue) {
		return ResponseEntity.ok(venueRepository.save(venue));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteVenue(@PathVariable Long id) {
		venueRepository.deleteById(id);
		return ResponseEntity.ok("Venue has been deleted successfully.");
	}

	@GetMapping
	public ResponseEntity<Iterable<Venue>> getAllVenues() {
		return ResponseEntity.ok(venueRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getVenue(@PathVariable Long id) {
		Optional<Venue> venue = venueRepository.findById(id);
		return venue.isPresent() ? ResponseEntity.ok(venue.get()) : ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Venue> update(@RequestBody Venue updatedVenue, @PathVariable Long id) {
		return venueRepository.findById(id).map(venue -> {
			venue.setName(updatedVenue.getName());
			venue.setStreetAddress(updatedVenue.getStreetAddress());
			venue.setUnit(updatedVenue.getUnit());
			venue.setCity(updatedVenue.getCity());
			venue.setState(updatedVenue.getState());
			venue.setZipCode(updatedVenue.getZipCode());
			return ResponseEntity.ok(venueRepository.save(venue));
		}).orElseGet(() -> {
			updatedVenue.setId(id);
			return ResponseEntity.ok(venueRepository.save(updatedVenue));
		});
	}

}
