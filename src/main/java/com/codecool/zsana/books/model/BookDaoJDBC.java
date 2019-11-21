package com.codecool.zsana.books.model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoJDBC implements BookDao {

    private DataSource dataSource;
    private AuthorDao authorDao;

    public BookDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
        authorDao = new AuthorDaoJDBC(dataSource);
    }

    @Override
    public void add(Book book) {
        String authorFirstName = book.getAuthorFirstName();
        String authorLastName = book.getAuthorLastName();
        int authorId = getAuthorId(authorFirstName, authorLastName);
        String title = book.getTitle();
        String queryBook = "INSERT INTO book (author_id, title) VALUES (?, ?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(queryBook)) {
            ps.setInt(1, authorId);
            ps.setString(2, title);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println("SQL exception occured in BookDaoJDBC add method.");
        }
    }

    @Override
    public void update(Book book) {
        String authorFirstName = book.getAuthorFirstName();
        String authorLastName = book.getAuthorLastName();
        int authorId = getAuthorId(authorFirstName, authorLastName);
        int id = book.getId();
        String title = book.getTitle();
        String query = "UPDATE book SET author_id = ?, title = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, authorId);
            ps.setString(2, title);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println("SQL exception occured in BookDaoJDBC update method.");
        }
    }

    @Override
    public Book get(int id) {
        String query = "SELECT * FROM book WHERE id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Author author = authorDao.get(rs.getInt("author_id"));
                Book book = new Book(author.getFirstName(), author.getLastName(), rs.getString("title"));
                book.setId(id);
                return book;
            }

        } catch (SQLException sqle) {
            System.out.println("SQL exception occured BookDaoJDBC get method.");
        }
        return null;
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM book;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Author author = authorDao.get(rs.getInt("author_id"));
                Book book = new Book(author.getFirstName(), author.getLastName(), rs.getString("title"));
                book.setId(rs.getInt("id"));
                books.add(book);
            }
        } catch (SQLException sqle) {
            System.out.println("SQL exception occured BookDaoJDBC getAll method.");
        }
        return books;
    }

    private int getAuthorId(String firstName, String lastName) {
        String query = "SELECT id FROM author WHERE first_name = ? AND last_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                authorDao.add(new Author(firstName, lastName, new Date(System.currentTimeMillis())));
                return getAuthorId(firstName, lastName);
            }
        } catch (SQLException sqle) {
            System.out.println("SQL exception occured in BookDaoJDBC getAuthorId method.");
        }
        return 0;
    }
}
