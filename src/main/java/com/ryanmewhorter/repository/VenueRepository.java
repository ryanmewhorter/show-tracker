package com.ryanmewhorter.repository;

import org.springframework.data.repository.CrudRepository;

import com.ryanmewhorter.model.Venue;

public interface VenueRepository extends CrudRepository<Venue, Long> {

}
