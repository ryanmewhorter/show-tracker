package com.ryanmewhorter.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ryanmewhorter.model.Artist;
import com.ryanmewhorter.repository.ArtistRepository;

@RestController
@RequestMapping(path = "/artists")
public class ArtistController {

	@Autowired
	private ArtistRepository repository;

	@PostMapping
	public Artist create(@RequestBody Artist artist) {
		return repository.save(artist);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@GetMapping
	public Iterable<Artist> getAll() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Artist> getOne(@PathVariable Long id) {
		return repository.findById(id);
	}

	@PutMapping("/{id}")
	public Artist update(@RequestBody Artist updatedArtist, @PathVariable Long id) {
		return repository.findById(id).map(artist -> {
			artist.setName(updatedArtist.getName());
			artist.setShows(updatedArtist.getShows());
			return repository.save(artist);
		}).orElseGet(() -> {
			updatedArtist.setId(id);
			return repository.save(updatedArtist);
		});
	}
}
