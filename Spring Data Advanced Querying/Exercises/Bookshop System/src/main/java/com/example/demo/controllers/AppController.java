package com.example.demo.controllers;

import com.example.demo.entities.AgeRestriction;
import com.example.demo.entities.EditionType;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.repositories.BookRepository;
import com.example.demo.services.AuthorService;
import com.example.demo.services.BookService;
import com.example.demo.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class AppController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public AppController(AuthorService authorService, CategoryService categoryService, BookService bookService, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //for first initialization

        //this.authorService.seedAuthors();
        //this.categoryService.seedCategories();
        //this.bookService.seedBooks();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        //Ex.1 Books Title By Age Restriction
        //booksTitleByAgeRestriction(bufferedReader);

        //Ex.2 Golden Books
        // findGoldenBooksWithCopiesLessThan(bufferedReader);

        //Ex.3 Books By Price
        //booksByPrice();

        //Ex.4 Not Released Books
        //findAllBooksNotReleasedInYear(bufferedReader);

        //Ex. 5 Books Released Before Date
        // booksReleasedBeforeDate(bufferedReader);

        //Ex. 6 Authors Search
        //authorsSearch(bufferedReader);

        //Ex. 7 Books Search
        // booksSearch(bufferedReader);

        //Ex. 8 Book Titles Search
        // bookTitlesSearch(bufferedReader);

        //Ex. 9 Count Books
        // countBooks(bufferedReader);

        //Ex. 10 Total Book Copies
        //totalBookCopies();
    }

    private void totalBookCopies() {
        this.authorRepository.getAuthorsByBooksCopies().forEach(a -> System.out.println(a));
    }

    private void countBooks(BufferedReader bufferedReader) throws IOException {
        int titleLength = Integer.parseInt(bufferedReader.readLine());
        System.out.println(this.bookRepository.getBookCountWithTitleLongerThanNumber(titleLength));
    }

    private void bookTitlesSearch(BufferedReader bufferedReader) throws IOException {
        String start = bufferedReader.readLine();
        this.bookRepository.findAllByAuthorLastNameStartsWith(start)
                .forEach(b -> System.out.println(b.getTitle()));
    }

    private void booksSearch(BufferedReader bufferedReader) throws IOException {
        String pattern = bufferedReader.readLine();
        this.bookRepository.findAllByTitleContaining(pattern)
                .forEach(b -> System.out.println(b.getTitle()));
    }

    private void authorsSearch(BufferedReader bufferedReader) throws IOException {
        String pattern = bufferedReader.readLine();
        this.authorRepository.findAllByFirstNameEndingWith(pattern)
                .forEach(a -> System.out.printf("%s %s%n", a.getFirstName(), a.getLastName()));
    }

    private void booksReleasedBeforeDate(BufferedReader bufferedReader) throws IOException {
        String beforeDate = bufferedReader.readLine();
        this.bookRepository.findAllByReleaseDateBefore(LocalDate.parse(beforeDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .forEach(b -> System.out.printf("%s %s %.2f%n", b.getTitle(), b.getEditionType(), b.getPrice()));
    }

    private void findAllBooksNotReleasedInYear(BufferedReader bufferedReader) throws IOException {
        int year = Integer.parseInt(bufferedReader.readLine());
        this.bookRepository.findAllBooksNotReleasedInYear(year)
                .forEach(b -> System.out.println(b.getTitle()));
    }

    private void booksByPrice() {
        String lessThanPrice = "5";
        BigDecimal lessThan = new BigDecimal(lessThanPrice);
        String greaterThanPrice = "40";
        BigDecimal greaterThan = new BigDecimal(greaterThanPrice);
        this.bookRepository.findAllByPriceIsLessThanOrPriceIsGreaterThan(lessThan, greaterThan)
                .forEach(b -> System.out.printf("%s - $%.2f%n", b.getTitle(), b.getPrice()));
    }

    private void findGoldenBooksWithCopiesLessThan(BufferedReader bufferedReader) throws IOException {
        int copies = Integer.parseInt(bufferedReader.readLine());
        EditionType editionType = EditionType.GOLD;

        this.bookRepository.findAllByCopiesIsLessThanAndEditionTypeIs(copies, editionType)
                .forEach(b -> System.out.println(b.getTitle()));
    }

    private void booksTitleByAgeRestriction(BufferedReader bufferedReader) throws IOException {
        String ageRestriction = bufferedReader.readLine().toUpperCase();

        this.bookRepository.findAllByAgeRestriction(AgeRestriction.valueOf(ageRestriction))
                .forEach(b -> System.out.println(b.getTitle()));
    }
}
