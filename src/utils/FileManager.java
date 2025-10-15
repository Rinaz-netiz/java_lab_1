package utils;

import models.Book;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String DEFAULT_FILE = "data/books.dat";

    public static boolean saveToFile(List<Book> books, String filename) {
        try {
            File file = new File(filename);
            file.getParentFile().mkdirs();
            
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(books);
            }
            return true;
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении в файл: " + e.getMessage());
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Book> loadFromFile(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (List<Book>) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке из файла: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static boolean exportToText(List<Book> books, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("=".repeat(100));
            writer.newLine();
            writer.write("СПИСОК КНИГ В БИБЛИОТЕКЕ");
            writer.newLine();
            writer.write("=".repeat(100));
            writer.newLine();
            writer.newLine();
            
            for (Book book : books) {
                writer.write(book.toString());
                writer.newLine();
            }
            
            writer.newLine();
            writer.write("Всего книг: " + books.size());
            writer.newLine();
            
            return true;
        } catch (IOException e) {
            System.err.println("Ошибка при экспорте в текстовый файл: " + e.getMessage());
            return false;
        }
    }

    public static String getDefaultFile() {
        return DEFAULT_FILE;
    }
}
