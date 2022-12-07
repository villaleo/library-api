package com.libraryapi.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface PatronsRepository extends CrudRepository<Patrons, Integer> {
    Patrons findByPatronId(int patronId);
}
