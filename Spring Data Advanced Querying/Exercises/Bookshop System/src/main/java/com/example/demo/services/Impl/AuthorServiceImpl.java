package com.example.demo.services.Impl;

import com.example.demo.entities.Author;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.services.AuthorService;
import com.example.demo.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final static String AUTHOR_FILE_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\Spring Data Advanced Querying\\Exercises\\Bookshop System\\src\\main\\resources\\files\\authors.txt";
    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() != 0) {
            return;
        }

        String[] authors = this.fileUtil.fileContent(AUTHOR_FILE_PATH);

        for (String fullName : authors) {
            Author author = new Author();
            String[] params = fullName.split("\\s+");
            author.setFirstName(params[0]);
            author.setLastName(params[1]);
            this.authorRepository.saveAndFlush(author);
        }
    }
}
