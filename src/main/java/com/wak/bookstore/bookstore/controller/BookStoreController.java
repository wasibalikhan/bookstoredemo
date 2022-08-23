package com.wak.bookstore.bookstore.controller;

import com.wak.bookstore.bookstore.entity.Book;
import com.wak.bookstore.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@RestController
public class BookStoreController {

    public static final Logger logger = Logger.getLogger(String.valueOf(BookStoreController.class));
    @Autowired
    BookRepository bookRepository;

    @Autowired
    private Environment env;

    @RequestMapping(value = "/saveBook", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        bookRepository.save(book);
        logger.info("Book save successfully...");
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks() {

        String bookTypeFictionDiscount = env.getProperty("BOOK.TYPE.FICTION.DISCOUNT");
        String bookTypeComicDiscount = env.getProperty("BOOK.TYPE.COMIC.DISCOUNT");

        List<Book> completeBookList = bookRepository.findAll();
        logger.info("List of available of books : " + completeBookList.size());
        if (CollectionUtils.isEmpty(completeBookList)) {
            return new ResponseEntity<>(completeBookList, HttpStatus.NOT_FOUND);
        }
        List<Book> list = new ArrayList<>();
        completeBookList.stream().forEach(book -> {
            if (book.getBookType().equals("fiction")) {
                logger.info("Book Type is fiction" + book.getBookType());
                Book book1 = new Book();
                double payableAmount, discount;
                discount = (book.getPrice() * Integer.valueOf(bookTypeFictionDiscount)) / 100;
                payableAmount = book.getPrice() - discount;
                book1.setId(book.getId());
                book1.setName(book.getName());
                book1.setPrice(book.getPrice());
                book1.setPayableAmount(payableAmount);
                list.add(book1);
            } else if (!book.getBookType().equals("fiction")) {
                logger.info("Book Type is comic" + book.getBookType());
                Book book2 = new Book();
                double payableAmount, discount;
                discount = (book.getPrice() * Integer.valueOf(bookTypeComicDiscount)) / 100;
                payableAmount = book.getPrice() - discount;
                book2.setId(book.getId());
                book2.setName(book.getName());
                book2.setPrice(book.getPrice());
                book2.setPayableAmount(payableAmount);
                list.add(book2);
            } else {
                logger.info("Book Type is other than fiction and comic " + book.getBookType());
                Book book3 = new Book();
                book3.setId(book.getId());
                book3.setName(book.getName());
                book3.setPrice(book.getPrice());
                book3.setPayableAmount(book.getPrice());
                list.add(book3);
            }

        });
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/getBook/{bookName}")
    public ResponseEntity<Book> getBookByName(@PathVariable String bookName) {

        String bookTypeFictionDiscount = env.getProperty("BOOK.TYPE.FICTION.DISCOUNT");
        String bookTypeComicDiscount = env.getProperty("BOOK.TYPE.COMIC.DISCOUNT");

        logger.info("Recived Book Name : {}" + bookName);
        Book book = new Book();
        List<Book> bookList = bookRepository.findByName(bookName);
        logger.info("Get Book From DB" + bookList.size());
        if (CollectionUtils.isEmpty(bookList)) {
            return new ResponseEntity<>(book, HttpStatus.NOT_FOUND);
        }
        Double bookPrice = bookList.get(0).getPrice();
        logger.info("Booked Price : {}" + bookPrice);
        double payableAmount, discount;
        if (bookList.get(0).getBookType().equals("fiction")) {
            logger.info("Book Type is fiction" + bookList.get(0).getBookType());
            discount = (bookPrice * Integer.valueOf(bookTypeFictionDiscount)) / 100;
            payableAmount = bookPrice - discount;
            book.setId(book.getId());
            book.setName(bookName);
            book.setPrice(bookPrice);
            book.setPayableAmount(payableAmount);
        } else if (bookList.get(0).getBookType().equals("comic")) {
            logger.info("Book Type is comic " + bookList.get(0).getBookType());
            discount = (bookPrice * Integer.valueOf(bookTypeComicDiscount)) / 100;
            payableAmount = bookPrice - discount;
            book.setId(book.getId());
            book.setName(bookName);
            book.setPrice(bookPrice);
            book.setPayableAmount(payableAmount);
        } else {
            logger.info("Book Type is other than fiction and comic " + bookList.get(0).getBookType());
            book.setId(book.getId());
            book.setName(bookName);
            book.setPrice(bookPrice);
            book.setPayableAmount(bookPrice);
        }

        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }


}