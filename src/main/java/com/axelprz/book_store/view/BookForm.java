package com.axelprz.book_store.view;

import com.axelprz.book_store.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@Component
public class BookForm extends JFrame {
    private BookService bookService;
    private JPanel panel;
    private JTable bookTable;
    private DefaultTableModel tableModel;

    @Autowired
    public BookForm(BookService bookService) {
        this.bookService = bookService;
        initForm();
    }

    private void initForm() {
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(900, 700);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }

    private void createUIComponents() {
        this.tableModel = new DefaultTableModel(0, 5);
        String[] columns = {"Id", "Title", "Author", "Price", "Stock"};
        tableModel.setColumnIdentifiers(columns);

        this.bookTable = new JTable(tableModel);
        getBooks();
    }

    private void getBooks() {
        tableModel.setRowCount(0);
        var books = bookService.getAllBooks();
        books.forEach(book -> {
            Object[] row = {
                    book.getIdBook(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPrice(),
                    book.getStock()
            };
            this.tableModel.addRow(row);
        });
    }
}
