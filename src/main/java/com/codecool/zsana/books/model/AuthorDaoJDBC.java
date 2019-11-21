package com.codecool.zsana.books.model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoJDBC implements AuthorDao {
    private DataSource dataSource;

    public AuthorDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Author author) {
        String firstName = author.getFirstName();
        String lastName = author.getLastName();
        Date birthDate = author.getBirthDate();
        String query = "INSERT INTO author (first_name, last_name, birth_date) VALUES (?, ?, ?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setDate(3, birthDate);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println("SQL exception occured in AuthorJDBC add method.");
        }
    }

    @Override
    public void update(Author author) {
        int id = author.getId();
        String firstName = author.getFirstName();
        String lastName = author.getLastName();
        Date birthDate = author.getBirthDate();
        String query = "UPDATE author SET first_name = ?, last_name = ?, birth_date = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setDate(3, birthDate);
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println("SQL exception occured AuthorJDBC update method.");
        }
    }

    @Override
    public Author get(int id) {
        String query = "SELECT * FROM author WHERE id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Author author = new Author(rs.getString("first_name"), rs.getString("last_name"), rs.getDate("birth_date"));
                author.setId(id);
                return author;
            }

        } catch (SQLException sqle) {
            System.out.println("SQL exception occured AuthorJDBC get method.");
        }
        return null;
    }

    @Override
    public List<Author> getAll() {
        List<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM author;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Author author = new Author(rs.getString("first_name"), rs.getString("last_name"), rs.getDate("birth_date"));
                author.setId(rs.getInt("id"));
                authors.add(author);
            }
        } catch (SQLException sqle) {
            System.out.println("SQL exception occured AuthorJDBC getAll method.");
        }
        return authors;
    }
}
