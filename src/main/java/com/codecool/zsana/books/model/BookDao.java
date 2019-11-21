package com.codecool.zsana.books.model;

import java.util.List;

public interface BookDao {
    /**
     * Add a new object to database, and set the new ID
     *
     * @param book a new object, with ID not set yet (null)
     */
    void add(Book book);

    /**
     * Update existing object's data in the database
     *
     * @param book an object from the database, with ID already set
     */
    void update(Book book);

    /**
     * Get object by ID
     *
     * @param id ID to search by
     * @return Object with a given ID, or null if not found
     */
    Book get(int id);

    /**
     * Get all objects
     *
     * @return List of all objects of this type in the database
     */
    List<Book> getAll();

}
