package com.tuhuynh.daos;

import com.tuhuynh.entities.Book;
import com.tuhuynh.utils.MySQLUtils;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private static final Connection conn = MySQLUtils.getConnection();

    @SneakyThrows
    public static int createBook(final Book book) {
        @Cleanup val stmt = conn.prepareStatement("INSERT INTO book (NAME, AUTHOR, PRICE) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, book.getName());
        stmt.setString(2, book.getAuthor());
        stmt.setFloat(3, book.getPrice());
        stmt.execute();

        int generatedId = -1;
        @Cleanup val rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            generatedId = rs.getInt(1);
        }

        return generatedId;
    }

    @SneakyThrows
    public static List<Book> listBook() {
        @Cleanup val stmt = conn.prepareStatement("SELECT * FROM book");
        @Cleanup val rs = stmt.executeQuery();
        val books = new ArrayList<Book>();
        while (rs.next()) {
            val id = rs.getInt("id");
            val name = (String) rs.getObject("name");
            val author = (String) rs.getObject("author");
            val price = rs.getFloat("price");
            val book = new Book(id, name, author, price);
            books.add(book);
        }
        return books;
    }

    @SneakyThrows
    public static Book getBook(final int bookId) {
        @Cleanup val stmt = conn.prepareStatement("SELECT * FROM book WHERE id = ?");
        stmt.setInt(1, bookId);
        @Cleanup val rs = stmt.executeQuery();
        if (rs.next()) {
            val id = rs.getInt("id");
            val name = (String) rs.getObject("name");
            val author = (String) rs.getObject("author");
            val price = rs.getFloat("price");
            return new Book(id, name, author, price);
        }

        return null;
    }

    @SneakyThrows
    public static boolean updateBook(final int id, final Book book) {
        @Cleanup val stmt = conn.prepareStatement("UPDATE book SET name = ?, author = ?, price = ? WHERE id = ?");
        stmt.setString(1, book.getName());
        stmt.setString(2, book.getAuthor());
        stmt.setFloat(3, book.getPrice());
        stmt.setInt(4, id);
        val result = stmt.executeUpdate();
        return result >= 1;
    }

    @SneakyThrows
    public static boolean deleteBook(final int bookId) {
        @Cleanup val stmt = conn.prepareStatement("DELETE FROM book WHERE id = ?");
        stmt.setInt(1, bookId);
        val result = stmt.executeUpdate();
        return result >= 1;
    }
}
