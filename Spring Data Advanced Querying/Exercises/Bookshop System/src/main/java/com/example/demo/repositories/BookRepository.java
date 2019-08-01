package com.example.demo.repositories;

import com.example.demo.entities.AgeRestriction;
import com.example.demo.entities.Book;
import com.example.demo.entities.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByReleaseDateAfter(LocalDate date);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByCopiesIsLessThanAndEditionTypeIs(int copies, EditionType editionType);

    List<Book> findAllByPriceIsLessThanOrPriceIsGreaterThan(BigDecimal lessThan, BigDecimal greaterThan);

    @Query("SELECT b FROM Book b WHERE FUNCTION('YEAR', b.releaseDate) <> :year")
    List<Book> findAllBooksNotReleasedInYear(@Param("year") int year);

    List<Book> findAllByReleaseDateBefore(LocalDate beforeDate);

    List<Book> findAllByTitleContaining(String pattern);

    @Query("SELECT b FROM Book b WHERE b.author.lastName LIKE :start%")
    List<Book> findAllByAuthorLastNameStartsWith(@Param("start") String start);

    @Query("SELECT COUNT(b.id) FROM Book b WHERE LENGTH(b.title) > :number")
    long getBookCountWithTitleLongerThanNumber(int number);
}
