package com.libraryapi.domain;

import org.springframework.data.repository.CrudRepository;

public interface PatronsRepository extends CrudRepository<Patrons, Integer> {
    Patrons findByPatronId(int patronId);
}
