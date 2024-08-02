package com.axelprz.book_store.service;

import com.axelprz.book_store.model.Book;

import java.util.List;

public interface IBookService {
    public List<Book> getAllBooks();
    public Book getBookById(Integer id);
    public void addBook(Book book);
    public void deleteBook(Book book);
}
