package com.ryanmewhorter.repository;

import org.springframework.data.repository.CrudRepository;

import com.ryanmewhorter.model.Artist;

public interface ArtistRepository extends CrudRepository<Artist, Long> {

}
