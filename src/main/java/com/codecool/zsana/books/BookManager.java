package com.codecool.zsana.books;

import com.codecool.zsana.books.model.Book;
import com.codecool.zsana.books.model.BookDao;
import com.codecool.zsana.books.view.UserInterface;

public class BookManager extends Manager {
    private BookDao bookDao;

    public BookManager(UserInterface ui, BookDao bookDao) {
        super(ui);
        this.bookDao = bookDao;
    }

    @Override
    protected void add() {
        String authorFirstName = ui.readString("Author's first name: ", "None");
        String authorLastName = ui.readString("Author's last name: ", "None");
        String title = ui.readString("Title: ", "None");
        bookDao.add(new Book(authorFirstName, authorLastName, title));
    }

    @Override
    protected String getName() {
        return "Book Manager";
    }

    @Override
    protected void list() {
        for (Book book: bookDao.getAll()) {
            ui.println(book);
        }
    }

    @Override
    protected void edit() {
        int id = ui.readInt("Book ID", 0);
        Book book = bookDao.get(id);
        if (book == null) {
            ui.println("Book not found!");
            return;
        }
        ui.println(book);

        String authorFirstName = ui.readString("Author's first name: ", "None");
        String authorLastName = ui.readString("Author's last name: ", "None");
        String title = ui.readString("Title", "None");
        book.setAuthorFirstName(authorFirstName);
        book.setAuthorLastName(authorLastName);
        book.setTitle(title);
        bookDao.update(book);
    }

}
