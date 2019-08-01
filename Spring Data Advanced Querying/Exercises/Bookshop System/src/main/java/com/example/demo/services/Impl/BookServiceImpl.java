package com.example.demo.services.Impl;

import com.example.demo.entities.*;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.repositories.BookRepository;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.services.BookService;
import com.example.demo.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\Spring Data Advanced Querying\\Exercises\\Bookshop System\\src\\main\\resources\\files\\books.txt";
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() != 0) {
            return;
        }

        String[] books = fileUtil.fileContent(BOOKS_FILE_PATH);

        for (String bookInfo : books) {
            String[] params = bookInfo.split("\\s+");

            Book book = new Book();
            book.setAuthor(randomAuthor());

            EditionType editionType = EditionType.values()[Integer.parseInt(params[0])];
            book.setEditionType(editionType);

            LocalDate releaseDate = LocalDate.parse(params[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setReleaseDate(releaseDate);

            book.setCopies(Integer.parseInt(params[2]));

            BigDecimal price = new BigDecimal(params[3]);
            book.setPrice(price);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(params[4])];
            book.setAgeRestriction(ageRestriction);

            StringBuilder title = new StringBuilder();

            for (int i = 5; i <= params.length - 1; i++) {
                title.append(params[i]).append(" ");
            }

            book.setTitle(title.toString().trim());
            book.setCategories(randomCategories());

            this.bookRepository.saveAndFlush(book);
        }

    }

    @Override
    public List<String> findAllTitles() {
        return this.bookRepository.findAllByReleaseDateAfter(LocalDate.parse("31/12/2000", DateTimeFormatter.ofPattern("d/M/yyyy")))
                .stream()
                .map(b -> b.getTitle())
                .collect(Collectors.toList());
    }


    private Author randomAuthor() {
        Random random = new Random();
        int index = random.nextInt((int) this.authorRepository.count()) + 1;
        return this.authorRepository.getOne(index);
    }

    private Category randomCategory() {
        Random random = new Random();
        int index = random.nextInt((int) this.categoryRepository.count()) + 1;
        return this.categoryRepository.getOne(index);
    }

    private Set<Category> randomCategories() {
        Set<Category> categories = new HashSet<>();

        Random random = new Random();
        int index = random.nextInt((int) this.categoryRepository.count()) + 1;

        for (int i = 0; i < index; i++) {
            categories.add(randomCategory());
        }

        return categories;

    }
}
