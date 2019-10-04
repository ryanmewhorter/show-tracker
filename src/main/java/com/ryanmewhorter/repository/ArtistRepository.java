package com.ryanmewhorter.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.ryanmewhorter.model.Artist;

public interface ArtistRepository extends CrudRepository<Artist, Long> {

	List<Artist> findByNameContains(String name);
	
	List<Artist> findByNameContains(String name, Sort sort);
	
}
