package com.libraryapi.dto;

import com.libraryapi.domain.Book;

import java.util.Date;
import java.util.Objects;

public class BookDTO {
    public Integer book_id;
    public String title;
    public String author;
    public Integer checkout_patron_id;
    public Date checkout_date;

    public BookDTO(Book entity) {
        book_id = entity.getBookId();
        title = entity.getTitle();
        author = entity.getAuthor();
        checkout_patron_id = entity.getCheckoutPatronId();
        checkout_date = entity.getCheckoutDate();
    }

    @Override
    public String toString() {
        var result = "\"%s\" by %s with ID %d".formatted(title, author, book_id);
        if (checkout_date != null) {
            result += " checked out on %s by patron #%d".formatted(checkout_date, checkout_patron_id);
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof BookDTO dto) {
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
