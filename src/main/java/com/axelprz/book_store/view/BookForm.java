package com.axelprz.book_store.view;

import com.axelprz.book_store.model.Book;
import com.axelprz.book_store.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class BookForm extends JFrame {
    private final BookService bookService;
    private JPanel panel;
    private JTable bookTable;
    private JTextField txtTitle;
    private JTextField txtAuthor;
    private JTextField txtPrice;
    private JTextField txtStock;
    private JTextField txtIdBook;
    private JButton btnAdd;
    private JButton btnModify;
    private JButton btnDelete;
    private DefaultTableModel tableModel;

    @Autowired
    public BookForm(BookService bookService) {
        this.bookService = bookService;
        initForm();
        btnAdd.addActionListener(e -> addBook());
        btnModify.addActionListener(e -> modifyBook(txtIdBook.getText()));
        btnDelete.addActionListener(e -> deleteBook(txtIdBook.getText()));
        bookTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                loadSelectedBook();
            }
        });
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

    private void addBook(){
        if(checkFields()){
            var title = txtTitle.getText();
            var author = txtAuthor.getText();
            var price = Double.parseDouble(txtPrice.getText());
            var stock = Integer.parseInt(txtStock.getText());

            var book = new Book(null, title, author, price, stock);
            this.bookService.addBook(book);
            showMessage("Book added");
            clearForm();
            getBooks();
        }else{
            showMessage("You must fill out all fields");
        }
    }

    private void modifyBook(String id){
        if(!(id.isEmpty())){
            if(checkFields()){
                var title = txtTitle.getText();
                var author = txtAuthor.getText();
                var price = Double.parseDouble(txtPrice.getText());
                var stock = Integer.parseInt(txtStock.getText());

                var book = new Book(Integer.parseInt(id), title, author, price, stock);
                this.bookService.addBook(book);
                showMessage("Book updated");
                clearForm();
                getBooks();
            }else{
                showMessage("You must fill out all fields");
            }
        }else{
            showMessage("Choose the book you want to modify from the table");
        }
    }

    private void deleteBook(String id){
        if(!(id.isEmpty())){
            this.bookService.deleteBookById(Integer.parseInt(id));
            showMessage("Book deleted");
            clearForm();
            getBooks();
        }else{
            showMessage("Choose the book you want to delete from the table");
        }
    }

    private boolean checkFields(){
        return !txtTitle.getText().isEmpty() &&
                !txtAuthor.getText().isEmpty() &&
                !txtPrice.getText().isEmpty() &&
                !txtStock.getText().isEmpty();
    }

    private void loadSelectedBook(){
        var row = bookTable.getSelectedRow();
        if(row != -1){
            String idBook =
                    bookTable.getModel().getValueAt(row, 0).toString();
            txtIdBook.setText(idBook);
            String title =
                    bookTable.getModel().getValueAt(row, 1).toString();
            txtTitle.setText(title);
            String author =
                    bookTable.getModel().getValueAt(row, 2).toString();
            txtAuthor.setText(author);
            String price =
                    bookTable.getModel().getValueAt(row, 3).toString();
            txtPrice.setText(price);
            String stock =
                    bookTable.getModel().getValueAt(row, 4).toString();
            txtStock.setText(stock);
        }
    }

    private void clearForm() {
        txtTitle.setText("");
        txtAuthor.setText("");
        txtPrice.setText("");
        txtStock.setText("");
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void createUIComponents() {
        txtIdBook = new JTextField("");
        txtIdBook.setVisible(false);

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
