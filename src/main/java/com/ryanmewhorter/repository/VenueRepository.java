package com.ryanmewhorter.repository;

import org.springframework.data.repository.CrudRepository;

import com.ryanmewhorter.model.Venue;

import java.util.List;

public interface VenueRepository extends CrudRepository<Venue, Long> {

    Venue findOneByNameIgnoreCase(String name);

}
