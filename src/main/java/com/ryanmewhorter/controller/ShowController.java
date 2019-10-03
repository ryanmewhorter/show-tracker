package com.ryanmewhorter.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.ryanmewhorter.model.Show;
import com.ryanmewhorter.model.Venue;
import com.ryanmewhorter.repository.ArtistRepository;
import com.ryanmewhorter.repository.ShowRepository;
import com.ryanmewhorter.repository.VenueRepository;

@RestController
@RequestMapping(path = "/shows")
public class ShowController {

	@Autowired
	private ShowRepository showRepository;

	@Autowired
	private ArtistRepository artistRepository;

	@Autowired
	private VenueRepository venueRepository;

	// Links an artist to a show
	// Should this use Post?
	@PostMapping("/{showId}/artists/{artistId}")
	public ResponseEntity<Show> addArtistToShow(@PathVariable Long showId, @PathVariable Long artistId) {
		Optional<Show> show = showRepository.findById(showId);
		Optional<Artist> artist = artistRepository.findById(artistId);
		if (show.isPresent() && artist.isPresent()) {
			Show existingShow = show.get();
			List<Artist> existingArtists = existingShow.getArtists();
			existingArtists.add(artist.get());
			existingShow.setArtists(existingArtists);
			return ResponseEntity.ok(showRepository.save(existingShow));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<Show> createShow(
			@RequestParam(required = true) @DateTimeFormat(pattern = "MM/dd/yyyy") Date date,
			@RequestParam(required = true) Long venueId, @RequestParam(defaultValue = "") List<Long> artistIds) {
		Optional<Venue> venue = venueRepository.findById(venueId);
		Iterable<Artist> artists = artistRepository.findAllById(artistIds);
		if (date != null && venue.isPresent()) {
			Show show = new Show();
			show.setDate(date);
			show.setVenue(venue.get());
			List<Artist> artistList = new ArrayList<>();
			artists.forEach(artistList::add);
			show.setArtists(artistList);
			return ResponseEntity.ok(showRepository.save(show));
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/{showId}/artists/{artistId}")
	public ResponseEntity<Show> deleteArtistFromShow(@PathVariable Long showId, @PathVariable Long artistId) {
		Optional<Show> show = showRepository.findById(showId);
		if (show.isPresent()) {
			List<Artist> existingArtists = show.get().getArtists();
			boolean artistFoundAndRemoved = existingArtists.removeIf(artist -> {
				return artist.getId().equals(artistId);
			});
			if (artistFoundAndRemoved) {
				return ResponseEntity.ok(showRepository.save(show.get()));
			} else {
				return ResponseEntity.notFound().build();
			}
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteShow(@PathVariable Long id) {
		showRepository.deleteById(id);
		return ResponseEntity.ok("Show has been deleted successfully.");
	}

	@GetMapping("/{id}/artists")
	public ResponseEntity<List<Artist>> getAllArtistsOnShow(@PathVariable Long id) {
		Optional<Show> show = showRepository.findById(id);
		return show.isPresent() ? ResponseEntity.ok(show.get().getArtists()) : ResponseEntity.notFound().build();
	}

	@GetMapping
	public ResponseEntity<Iterable<Show>> getAllShows() {
		return ResponseEntity.ok(showRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getShow(@PathVariable Long id) {
		Optional<Show> show = showRepository.findById(id);
		return show.isPresent() ? ResponseEntity.ok(show.get()) : ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Show> update(@RequestBody Show updatedShow, @PathVariable Long id) {
		return showRepository.findById(id).map(show -> {
			show.setDate(updatedShow.getDate());
			show.setArtists(updatedShow.getArtists());
			return ResponseEntity.ok(showRepository.save(show));
		}).orElseGet(() -> {
			updatedShow.setId(id);
			return ResponseEntity.ok(showRepository.save(updatedShow));
		});
	}
}
