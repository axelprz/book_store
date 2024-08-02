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
    private BookService bookService;
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
        btnAdd.addActionListener(e -> addBook(null));
        btnModify.addActionListener(e -> addBook(Integer.parseInt(txtIdBook.getText())));
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

    private void addBook(Integer id){
        if(txtTitle.getText().equals("")){
            showMessage("Provide the title of the book");
            txtTitle.requestFocusInWindow();
            return;
        }
        var title = txtTitle.getText();
        var author = txtAuthor.getText();
        var price = Double.parseDouble(txtPrice.getText());
        var stock = Integer.parseInt(txtStock.getText());

        var book = new Book(id, title, author, price, stock);
        this.bookService.addBook(book);
        if(id == null){
            showMessage("Book added");
        }else{
            showMessage("Book updated");
        }
        clearForm();
        getBooks();
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
