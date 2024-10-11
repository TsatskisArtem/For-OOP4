package lab4;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OOPlab4 {

    private JFrame bookList;
    private DefaultTableModel model;   
    private JButton save, add, edit, delete;
    private JScrollPane scroll;
    private JTable books;

    public void show() {
        bookList = new JFrame("Информация о книгах");
        bookList.setSize(600, 400);
        bookList.setLocation(100, 100);
        bookList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columns = {"Название книги", "Автор", "Шрифт", "Закреплена?"};
        String[][] data = {
            {"Война и мир", "Лев Толстой", "Arial", "Нет"},
            {"1984", "Джордж Оруэлл", "Calibri", "Да"},
            {"Прощай оружие!", "Эрнест Хемингуэй", "Garamond", "Нет"},
            {"Убить пересмешника", "Харпер Ли", "Fraktur", "Да"},
            {"На дороге", "Джек Керуак", "Papyrus", "Нет"}
        };
        model = new DefaultTableModel(data, columns);
        books = new JTable(model);
        books.setAutoCreateRowSorter(true); 
        scroll = new JScrollPane(books);
        bookList.getContentPane().add(scroll, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel(new GridLayout(1, 4));
        bookList.getContentPane().add(filterPanel, BorderLayout.NORTH);

        save = new JButton("Сохранить");
        add = new JButton("Добавить");
        edit = new JButton("Редактировать");
        delete = new JButton("Удалить");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(save);
        buttonPanel.add(add);
        buttonPanel.add(edit);
        buttonPanel.add(delete);

        bookList.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(bookList, "Данные сохранены.");
            }
        });

        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(bookList, "Добавлена новая книга.");
            }
        });

        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = books.getSelectedRow();
                    editBook(selectedRow, "Новое название", "Новый автор", "Новый шрифт", "Нет");
                    JOptionPane.showMessageDialog(bookList, "Книга отредактирована.");
                } catch (InvalidBookOperationException ex) {
                    JOptionPane.showMessageDialog(bookList, ex.getMessage(), "Ошибка редактирования", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = books.getSelectedRow();
                    deleteBook(selectedRow);
                    JOptionPane.showMessageDialog(bookList, "Книга удалена.");
                } catch (BookDeletionException ex) {
                    JOptionPane.showMessageDialog(bookList, ex.getMessage(), "Ошибка удаления", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        bookList.setVisible(true);
    }

    public void editBook(int rowIndex, String title, String author, String font, String pinned) throws InvalidBookOperationException {
        if (rowIndex < 0 || rowIndex >= model.getRowCount()) {
            throw new InvalidBookOperationException("Не выбрана книга");
        }
        model.setValueAt(title, rowIndex, 0);
        model.setValueAt(author, rowIndex, 1);
        model.setValueAt(font, rowIndex, 2);
        model.setValueAt(pinned, rowIndex, 3);
    }

    public void deleteBook(int rowIndex) throws BookDeletionException {
        if (rowIndex < 0 || rowIndex >= model.getRowCount()) {
            throw new BookDeletionException("Не выбрана книга");
        }
        model.removeRow(rowIndex);
    }

    public static void main(String[] args) {
        new OOPlab4().show();
    }
}


class InvalidBookOperationException extends Exception {
    public InvalidBookOperationException(String message) {
        super(message);
    }
}


class BookDeletionException extends Exception {
    public BookDeletionException(String message) {
        super(message);
    }
}
