package com.axelprz.book_store.service;

import com.axelprz.book_store.model.Book;
import com.axelprz.book_store.repository.IBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService {
    private final IBookRepository bookRepository;

    public BookService(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void deleteBookById(Integer id) {
        bookRepository.deleteById(id);
    }
}
