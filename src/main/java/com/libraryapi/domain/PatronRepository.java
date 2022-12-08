package com.libraryapi.domain;

import org.springframework.data.repository.CrudRepository;

public interface PatronRepository extends CrudRepository<Patron, Integer> {
    Patron findByPatronId(int patronId);
}
