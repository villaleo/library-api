package com.libraryapi.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Books {
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

    public Books() {
        super();
    }

    public Books(Integer bookId, String title, String author, Integer checkoutPatronId, Date checkoutDate) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.checkoutPatronId = checkoutPatronId;
        this.checkoutDate = checkoutDate;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer book_id) {
        this.bookId = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
