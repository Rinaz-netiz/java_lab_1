package services;

import models.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryManager {
    private List<Book> books;
    private int nextId;

    public LibraryManager() {
        this.books = new ArrayList<>();
        this.nextId = 1;
    }

    public void addBook(String title, String author, String isbn, int year, String genre) {
        Book book = new Book(nextId++, title, author, isbn, year, genre);
        books.add(book);
        System.out.println("Книга успешно добавлена");
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public Book findBookById(int id) {
        return books.stream()
            .filter(book -> book.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public boolean editBook(int id, String title, String author, String isbn, int year, String genre, boolean available) {
        Book book = findBookById(id);
        if (book != null) {
            if (title != null && !title.isEmpty()) {
                book.setTitle(title);
            }
            if (author != null && !author.isEmpty()) {
                book.setAuthor(author);
            }
            if (isbn != null && !isbn.isEmpty()) {
                book.setIsbn(isbn);
            }
            if (year > 0) {
                book.setYear(year);
            }
            if (genre != null && !genre.isEmpty()) {
                book.setGenre(genre);
            }

            book.setAvailable(available);

            return true;
        }
        return false;
    }

    public boolean deleteBook(int id) {
        return books.removeIf(book -> book.getId() == id);
    }

    public List<Book> searchBooks(String searchTerm, String searchBy) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return new ArrayList<>(books);
        }
        
        String term = searchTerm.toLowerCase();
        
        return books.stream()
                .filter(book -> {
                    switch (searchBy.toLowerCase()) {
                        case "title":
                        case "название":
                            return book.getTitle().toLowerCase().contains(term);
                        case "author":
                        case "автор":
                            return book.getAuthor().toLowerCase().contains(term);
                        case "isbn":
                            return book.getIsbn().toLowerCase().contains(term);
                        case "genre":
                        case "жанр":
                            return book.getGenre().toLowerCase().contains(term);
                        case "year":
                        case "год":
                            try {
                                return book.getYear() == Integer.parseInt(searchTerm);
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        default:
                            return book.getTitle().toLowerCase().contains(term) ||
                                   book.getAuthor().toLowerCase().contains(term) ||
                                   book.getIsbn().toLowerCase().contains(term) ||
                                   book.getGenre().toLowerCase().contains(term);
                    }
                })
                .collect(Collectors.toList());
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        this.nextId = books.stream()
                .mapToInt(Book::getId)
                .max()
                .orElse(0) + 1;
    }

    public int getNextId() {
        return nextId;
    }
}
