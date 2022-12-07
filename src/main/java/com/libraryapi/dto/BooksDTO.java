package com.libraryapi.dto;

import com.libraryapi.domain.Books;

import java.util.Date;
import java.util.Objects;

public class BooksDTO {
    public Integer book_id;
    public String title;
    public String author;
    public Integer checkout_patron_id;
    public Date checkout_date;

    public BooksDTO() {}

    public BooksDTO(Books entity) {
        book_id = entity.getBookId();
        title = entity.getTitle();
        author = entity.getAuthor();
        checkout_patron_id = entity.getCheckoutPatronId();
        checkout_date = entity.getCheckoutDate();
    }

    @Override
    public String toString() {
        return "BooksDTO{%d, %s, %s, %d, %s}"
            .formatted(book_id, title, author, checkout_patron_id, checkout_date);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof BooksDTO dto) {
            if (dto.title == null || dto.author == null) {
                return false;
            }

            return (
                Objects.equals(book_id, dto.book_id) &&
                title.equals(dto.title) &&
                author.equals(dto.author) &&
                    Objects.equals(checkout_patron_id, dto.checkout_patron_id) &&
                checkout_date.equals(dto.checkout_date)
            );
        }

        return false;
    }
}