package com.libraryapi.domain;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer bookId;

    private String title;
    private String author;
    @Column(name = "checkout_patron_id")
    private Integer checkoutPatronId;
    @Column(name = "checkout_date")
    private Date checkoutDate;

    public Book() {
        super();
    }

    public Integer getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getCheckoutPatronId() {
        return checkoutPatronId;
    }

    public void setCheckoutPatronId(Integer checkout_patron_id) {
        this.checkoutPatronId = checkout_patron_id;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkout_date) {
        this.checkoutDate = checkout_date;
    }
}
