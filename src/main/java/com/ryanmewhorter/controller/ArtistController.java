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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ryanmewhorter.model.Artist;
import com.ryanmewhorter.repository.ArtistRepository;

@RestController
@RequestMapping(path = "/artists")
public class ArtistController {

	@Autowired
	private ArtistRepository artistRepository;

	@PostMapping
	public ResponseEntity<Artist> create(@RequestBody Artist artist) {
		return ResponseEntity.ok(artistRepository.save(artist));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		artistRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<Iterable<Artist>> getAll(@RequestParam(name = "name", required = false) String name) {
		System.out.println("name = " + name);
		if (name != null && !name.equals("")) {
			return ResponseEntity.ok(artistRepository.findByNameContainsIgnoreCase(name));
		} else {
			return ResponseEntity.ok(artistRepository.findAll());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Artist> getOne(@PathVariable Long id) {
		Optional<Artist> artist = artistRepository.findById(id);
		if (artist.isPresent()) {
			return ResponseEntity.ok(artist.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Artist> update(@RequestBody Artist updatedArtist, @PathVariable Long id) {
		return artistRepository.findById(id).map(artist -> {
			artist.setName(updatedArtist.getName());
			artist.setShows(updatedArtist.getShows());
			return ResponseEntity.ok(artistRepository.save(artist));
		}).orElseGet(() -> {
			updatedArtist.setId(id);
			return ResponseEntity.ok(artistRepository.save(updatedArtist));
		});
	}
}
