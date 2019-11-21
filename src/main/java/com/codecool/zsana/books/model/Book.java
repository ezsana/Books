package com.codecool.zsana.books.model;

public class Book {

    private Integer id;
    private String authorFirstName;
    private String authorLastName;
    private String title;

    public Book(String authorFirstName, String authorLastName, String title) {
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAuthorFirstName(String id) {
        this.authorFirstName = id;
    }

    public String getAuthorFirstName() {
        return this.authorFirstName;
    }

    public void setAuthorLastName(String id) {
        this.authorLastName = id;
    }

    public String getAuthorLastName() {
        return this.authorLastName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public String toString() {
        return String.format("%d: %s, Author: %s %s ",
                id, title, authorFirstName, authorLastName);
    }

}
