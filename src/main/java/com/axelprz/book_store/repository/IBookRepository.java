package com.axelprz.book_store.repository;

import com.axelprz.book_store.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepository extends JpaRepository<Book, Integer> {
}
