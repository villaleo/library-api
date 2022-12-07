package com.libraryapi.controller;

import com.libraryapi.domain.BooksRepository;
import com.libraryapi.domain.PatronsRepository;
import com.libraryapi.dto.BooksDTO;
import com.libraryapi.dto.PatronsDTO;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LibraryController {
	protected final BooksRepository booksRepository;
	protected final PatronsRepository patronsRepository;

	public static final int StatusOk        = 0;
	public static final int StatusOwesFines = -3;

	public LibraryController(BooksRepository booksRepository, PatronsRepository patronsRepository) {
		this.booksRepository = booksRepository;
		this.patronsRepository = patronsRepository;
	}

	/**
	 * A patron checks out a book
	 * @param book_id The ID of the book to check out
	 * @param patron_id The ID of the patron who wishes to check out the book
	 * @return <code>0</code> if operation was Ok, <code>-3</code> if patron owes fines
	 * @throws org.springframework.web.client.HttpClientErrorException.NotFound if either <code>book_id</code>
	 * or <code>patron_id</code> not found
	 */
	@PutMapping("/book/{book_id}/checkout/{patron_id}")
	@Transactional
	public int checkoutBook(@PathVariable int book_id, @PathVariable int patron_id) throws HttpClientErrorException.NotFound {
		System.out.printf("\t[INFO] Endpoint /book/%d/checkout/%d hit!\n", book_id, patron_id);

		var bookToCheckout = booksRepository.findByBookId(book_id);
		if (bookToCheckout == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with ID %d does not exist!".formatted(book_id));
		}
		System.out.printf("\t[INFO] Found book: %s\n", new BooksDTO(bookToCheckout));

		var patron = patronsRepository.findByPatronId(patron_id);
		if (patron == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patron with ID %d does not exist!".formatted(patron_id));
		}
		System.out.printf("\t[INFO] Found patron: %s\n", new PatronsDTO(patron));

		if (patron.getFines() != 0.0) {
			System.out.printf("\t[WARN] Could not checkout: patron owes fines of $%.2f\n", patron.getFines());
			return StatusOwesFines;
		}

		var currentTime = new Date(Instant.now().toEpochMilli());
		bookToCheckout.setCheckoutPatronId(patron_id);
		bookToCheckout.setCheckoutDate(currentTime);
		booksRepository.save(bookToCheckout);

		System.out.printf("\t[INFO] Updated book: %s\n", new BooksDTO(bookToCheckout));
		return StatusOk;
	}

	/**
	 * Returns a book that a patron had checked out
	 * @param book_id The ID of the book to return
	 * @return <code>0</code> if operation was Ok
	 * @throws org.springframework.web.client.HttpClientErrorException.NotFound if <code>book_id</code> not found
	 */
	@PutMapping("/book/{book_id}/return")
	@Transactional
	public int returnBook(@PathVariable int book_id) throws HttpClientErrorException.NotFound {
		System.out.printf("\t[INFO] Endpoint /book/%d/return hit!\n", book_id);

		var bookToReturn = booksRepository.findByBookId(book_id);
		if (bookToReturn == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with ID %d does not exist!".formatted(book_id));
		}
		System.out.printf("\t[NOTE] Found book: %s\n", new BooksDTO(bookToReturn));

		bookToReturn.setCheckoutDate(null);
		bookToReturn.setCheckoutPatronId(null);
		booksRepository.save(bookToReturn);

		System.out.printf("\t[NOTE] Returned book: %s\n", new BooksDTO(bookToReturn));
		return StatusOk;
	}

	/**
	 * List a patron's checkouts
	 * @param patron_id The ID of the patron
	 * @return A list of <code>BookDTO</code>'s for each book checked out by the patron
	 * @throws org.springframework.web.client.HttpClientErrorException.NotFound if <code>patron_id</code>
	 * not found
	 */
	@GetMapping("/patron/{patron_id}/checkouts")
	public List<BooksDTO> listPatronCheckouts(@PathVariable int patron_id) throws HttpClientErrorException.NotFound {
		System.out.printf("\t[INFO] Endpoint /patron/%d/checkouts hit!\n", patron_id);

		var patron = patronsRepository.findByPatronId(patron_id);
		if (patron == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patron with ID %d does not exist!".formatted(patron_id));
		}
		System.out.printf("\t[INFO] Found patron: %s\n", new PatronsDTO(patron));

		List<BooksDTO> result = new ArrayList<>();
		var booksCheckedOut = booksRepository.findByCheckoutPatronId(patron_id);
		if (booksCheckedOut.isEmpty()) {
			System.out.printf("\t[INFO] Patron with ID %d has no books checked out\n", patron_id);
			return result;
		}

		booksCheckedOut.forEach(book -> result.add(new BooksDTO(book)));
		System.out.println("\t[INFO] Found books:");
		result.forEach(entry -> System.out.printf("\t\t%s\n", entry));
		return result;
	}

	/**
	 * Check out if patron information
	 * @param patron_id The ID of the patron to list
	 * @return A <code>PatronDTO</code>, holding all the patron's data
	 * @throws org.springframework.web.client.HttpClientErrorException.NotFound if <code>patron_id</code>
	 * not found
	 */
	@GetMapping("/patron/{patron_id}")
	public PatronsDTO fetchPatron(@PathVariable int patron_id) throws HttpClientErrorException.NotFound  {
		System.out.printf("\t[INFO] Endpoint /patron/%d hit!\n", patron_id);

		var patron = patronsRepository.findByPatronId(patron_id);
		if (patron == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patron with ID %d does not exist!".formatted(patron_id));
		}
		System.out.printf("\t[INFO] Found patron: %s\n", new PatronsDTO(patron));
		return new PatronsDTO(patron);
	}
}
