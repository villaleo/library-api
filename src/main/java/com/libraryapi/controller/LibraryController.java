package com.libraryapi.controller;

import com.libraryapi.domain.BooksRepository;
import com.libraryapi.domain.PatronsRepository;
import com.libraryapi.dto.BooksDTO;
import com.libraryapi.dto.PatronsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
public class LibraryController {
	@Autowired
	BooksRepository booksRepository;
	@Autowired
	PatronsRepository patronsRepository;

	/**
	 * Represents the response values from methods in LibraryController
	 */
	enum Status {
		Ok(1),
		PatronHasFines(-3);

		private final int statusCode;

		Status(int code) {
			this.statusCode = code;
		}

		public int getStatusCode() {
			return this.statusCode;
		}
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
		// TODO: Implement business logic
		return Status.Ok.statusCode;
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
		// TODO: Implement business logic
		return Status.Ok.statusCode;
	}

	/**
	 * List a patron's checkouts
	 * @param patron_id The ID of the patron
	 * @return A list of <code>BookDTO</code>'s for each book checked out by the patron
	 * @throws org.springframework.web.client.HttpClientErrorException.NotFound if <code>patron_id</code>
	 * not found
	 */
	@GetMapping("/patron/{patron_id}/checkouts")
	List<BooksDTO> listPatronCheckouts(@PathVariable int patron_id) throws HttpClientErrorException.NotFound {
		// TODO: Implement business logic
		return null;
	}

	/**
	 * Check out if patron information
	 * @param patron_id The ID of the patron to list
	 * @return A <code>PatronDTO</code>, holding all the patron's data
	 * @throws org.springframework.web.client.HttpClientErrorException.NotFound if <code>patron_id</code>
	 * not found
	 */
	@GetMapping("/patron/{patron_id}")
	PatronsDTO fetchPatron(@PathVariable int patron_id) throws HttpClientErrorException.NotFound  {
		// TODO: Implement business logic
		return null;
	}
}
