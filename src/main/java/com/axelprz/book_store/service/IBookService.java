package com.axelprz.book_store.service;

import com.axelprz.book_store.model.Book;

import java.util.List;

public interface IBookService {
    List<Book> getAllBooks();

    void addBook(Book book);
    void deleteBookById(Integer id);
}
