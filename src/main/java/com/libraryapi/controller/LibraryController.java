package com.libraryapi.controller;

import com.libraryapi.domain.BookRepository;
import com.libraryapi.domain.PatronRepository;
import com.libraryapi.dto.BookDTO;
import com.libraryapi.dto.PatronDTO;

import com.libraryapi.log.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the Library API, having the following endpoints:
 * <ul>
 *     <li><code>/book/{book_id}/checkout/{patron_id}</code>: Patron checks out a book</li>
 *     <li><code>/book/{book_id}/return</code>: Return a book</li>
 *     <li><code>/patron/{patron_id}/checkouts</code>: List patron checkouts</li>
 *     <li><code>/patron/{patron_id}</code>: View patron information</li>
 * </ul>
 * @author villaleobos
 */
@RestController
public class LibraryController {
	protected final BookRepository books;
	protected final PatronRepository patrons;

	public static final int StatusOk = 0;
	public static final int StatusOwesFines = -3;

	public LibraryController(BookRepository books, PatronRepository patrons) {
		this.books = books;
		this.patrons = patrons;
	}

	/**
	 * A patron checks out a book
	 * @param book_id The ID of the book to check out
	 * @param patron_id The ID of the patron who wishes to check out the book
	 * @return <code>0</code> if operation was Ok, <code>-3</code> if patron owes fines
	 * @throws ResponseStatusException if either <code>book_id</code>
	 * or <code>patron_id</code> not found
	 */
	@PutMapping("/book/{book_id}/checkout/{patron_id}")
	@Transactional
	public int checkoutBook(@PathVariable int book_id, @PathVariable int patron_id) throws ResponseStatusException {
		Logger.info("Endpoint /book/%d/checkout/%d hit!",book_id, patron_id);

		var bookToCheckout = books.findByBookId(book_id);
		if (bookToCheckout == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with ID %d does not exist!".formatted(book_id));
		}
		Logger.info("Found book: %s", new BookDTO(bookToCheckout));

		var patron = patrons.findByPatronId(patron_id);
		if (patron == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patron with ID %d does not exist!".formatted(patron_id));
		}
		Logger.info("Found patron: %s", new PatronDTO(patron));

		if (patron.getFines() != 0.0) {
			Logger.warn("Could not checkout: patron owes fines of $%.2f", patron.getFines());
			return StatusOwesFines;
		}

		var currentTime = new Date(Instant.now().toEpochMilli());
		bookToCheckout.setCheckoutPatronId(patron_id);
		bookToCheckout.setCheckoutDate(currentTime);
		books.save(bookToCheckout);

		Logger.info("Updated book: %s", new BookDTO(bookToCheckout));
		return StatusOk;
	}

	/**
	 * Returns a book that a patron had checked out
	 * @param book_id The ID of the book to return
	 * @return <code>0</code> if operation was Ok
	 * @throws ResponseStatusException if <code>book_id</code> not found
	 */
	@PutMapping("/book/{book_id}/return")
	@Transactional
	public int returnBook(@PathVariable int book_id) throws ResponseStatusException {
		Logger.info("Endpoint /book/%d/return hit!", book_id);

		var bookToReturn = books.findByBookId(book_id);
		if (bookToReturn == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with ID %d does not exist!".formatted(book_id));
		}
		Logger.info("Found book: %s", new BookDTO(bookToReturn));

		bookToReturn.setCheckoutDate(null);
		bookToReturn.setCheckoutPatronId(null);
		books.save(bookToReturn);

		Logger.info("Returned book: %s", new BookDTO(bookToReturn));
		return StatusOk;
	}

	/**
	 * List a patron's checkouts
	 * @param patron_id The ID of the patron
	 * @return A list of <code>BookDTO</code>'s for each book checked out by the patron
	 * @throws ResponseStatusException if <code>patron_id</code>
	 * not found
	 */
	@GetMapping("/patron/{patron_id}/checkouts")
	public List<BookDTO> listPatronCheckouts(@PathVariable int patron_id) throws ResponseStatusException {
		Logger.info("Endpoint /patron/%d/checkouts hit!", patron_id);

		var patron = patrons.findByPatronId(patron_id);
		if (patron == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patron with ID %d does not exist!".formatted(patron_id));
		}
		Logger.info("Found patron: %s", new PatronDTO(patron));

		List<BookDTO> result = new ArrayList<>();
		var booksCheckedOut = books.findByCheckoutPatronId(patron_id);
		if (booksCheckedOut.isEmpty()) {
			Logger.info("Patron with ID %d has no books checked out", patron_id);
			return result;
		}

		booksCheckedOut.forEach(book -> result.add(new BookDTO(book)));
		Logger.info("Found books:");
		result.forEach(entry -> Logger.info("\t * %s", entry));
		return result;
	}

	/**
	 * Check out if patron information
	 * @param patron_id The ID of the patron to list
	 * @return A <code>PatronDTO</code>, holding all the patron's data
	 * @throws ResponseStatusException if <code>patron_id</code>
	 * not found
	 */
	@GetMapping("/patron/{patron_id}")
	public PatronDTO fetchPatron(@PathVariable int patron_id) throws ResponseStatusException  {
		Logger.info("Endpoint /patron/%d hit!", patron_id);

		var patron = patrons.findByPatronId(patron_id);
		if (patron == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patron with ID %d does not exist!".formatted(patron_id));
		}
		Logger.info("Found patron: %s", new PatronDTO(patron));
		return new PatronDTO(patron);
	}
}
