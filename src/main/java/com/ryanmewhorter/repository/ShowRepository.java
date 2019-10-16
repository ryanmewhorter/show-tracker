package com.ryanmewhorter.repository;

import com.ryanmewhorter.model.Show;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShowRepository extends CrudRepository<Show, Long> {
}
