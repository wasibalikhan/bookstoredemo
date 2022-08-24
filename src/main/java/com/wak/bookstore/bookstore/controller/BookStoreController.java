package com.wak.bookstore.bookstore.controller;

import com.wak.bookstore.bookstore.entity.Book;
import com.wak.bookstore.bookstore.repository.BookRepository;
import com.wak.bookstore.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;


@RestController
public class BookStoreController {

    public static final Logger logger = Logger.getLogger(String.valueOf(BookStoreController.class));
    @Autowired
    BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/saveBook", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        bookRepository.save(book);
        logger.info("Book save successfully...");
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> completeBookList = bookService.getCompleteBookList();
        logger.info("List of available of books : " + completeBookList.size());
        if (CollectionUtils.isEmpty(completeBookList)) {
            logger.info("No Books Found...");
            return new ResponseEntity<>(completeBookList, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(completeBookList, HttpStatus.OK);
    }

    @GetMapping("/getBook/{bookName}")
    public ResponseEntity<Book> getBookByName(@PathVariable String bookName) {
        Book book = bookService.findBookByName(bookName);
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<Book> updateBookById(@RequestBody Book book,
                                               @PathVariable("id") Integer bookId) {
        logger.info("Update Book By Id " + bookId + "Update Request : " + book);
        Book updateBook = bookService.updateBook(book, bookId);
        logger.info("Updated Book Details " + updateBook);
        return new ResponseEntity<Book>(updateBook, HttpStatus.OK);
    }


    @DeleteMapping("/books/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable("id") long id) {
        try {
            logger.info("Delete Id : " + id);
            bookRepository.deleteById((int) id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/books")
    public ResponseEntity<HttpStatus> deleteAllBooks() {
        try {
            logger.info("Delete All");
            bookRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}