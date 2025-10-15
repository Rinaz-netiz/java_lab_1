package models;

import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String author; 
    private String isbn;
    private int year;
    private String genre;
    private boolean available;

    public Book(int id, String title, String author, String isbn, int year, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.year = year;
        this.genre = genre;
        this.available = true;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public int getYear() { return year; }
    public String getGenre() { return genre; }
    public boolean isAvailable() { return available; }
    
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setYear(int year) { this.year = year; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return String.format("ID: %d | Название: %s | Автор: %s | ISBN: %s | Год: %d | Жанр: %s | Доступна: %s",
                id, title, author, isbn, year, genre, available ? "Да" : "Нет");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Book book = (Book) o;
        return id == book.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}   
