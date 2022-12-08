package com.libraryapi.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BooksRepository extends CrudRepository<Books, Integer> {
    @Query("select book from books book where book.checkoutPatronId = :patronId")
    List<Books> findByCheckoutPatronId(@Param(value = "patronId") int patronId);

    Books findByBookId(int bookId);
}
