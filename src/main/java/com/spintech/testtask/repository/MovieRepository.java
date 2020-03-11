package com.spintech.testtask.repository;

import com.spintech.testtask.entity.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, String> {
}
