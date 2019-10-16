package com.ryanmewhorter.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ryanmewhorter.model.Artist;

public interface ArtistRepository extends CrudRepository<Artist, Long> {

	Collection<Artist> findByNameContainsIgnoreCase(String name);

	@Query("SELECT artist FROM Artist artist WHERE artist.name IN (:names)")
	Collection<Artist> findAllByNamesIgnoreCase(List<String> names);
	

}
