package com.libraryapi.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {
    @Query("select book from books book where book.checkoutPatronId = :patronId")
    List<Book> findByCheckoutPatronId(@Param(value = "patronId") int patronId);

    Book findByBookId(int bookId);
}
