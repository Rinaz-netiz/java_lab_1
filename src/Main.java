import models.Book;
import services.LibraryManager;
import utils.FileManager;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static LibraryManager libraryManager;
    private static Scanner scanner;

    public static void main(String[] args) {
        libraryManager = new LibraryManager();
        scanner = new Scanner(System.in);

        loadFromFile(FileManager.getDefaultFile());
        
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   МЕНЕДЖЕР БИБЛИОТЕКИ - версия 1.0   ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        boolean running = true;

        while (running) {
            printMenu();
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        editBook();
                        break;
                    case 3:
                        deleteBook();
                        break;
                    case 4:
                        listBooks();
                        break;
                    case 5:
                        searchBooks();
                        break;
                    case 6:
                        saveToFile();
                        break;
                    case 7:
                        loadFromFileMenu();
                        break;
                    case 8:
                        exportToText();
                        break;
                    case 0:
                        running = false;
                        saveToFile(FileManager.getDefaultFile());
                        System.out.println("\nДо свидания!");
                        break;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
            }
        }
        
        scanner.close();
    }
    
    private static void printMenu() {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("ГЛАВНОЕ МЕНЮ:");
        System.out.println("─".repeat(50));
        System.out.println("1. Добавить книгу");
        System.out.println("2. Редактировать книгу");
        System.out.println("3. Удалить книгу");
        System.out.println("4. Показать все книги");
        System.out.println("5. Поиск книги");
        System.out.println("6. Сохранить в файл");
        System.out.println("7. Загрузить из файла");
        System.out.println("8. Экспортировать в текст");
        System.out.println("0. Выход");
        System.out.println("─".repeat(50));
        System.out.print("Выберите действие: ");
    }

    private static void addBook() {
        System.out.println("\n=== ДОБАВЛЕНИЕ КНИГИ ===");
        
        System.out.print("Название: ");
        String title = scanner.nextLine().trim();
        
        System.out.print("Автор: ");
        String author = scanner.nextLine().trim();
        
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();
        
        System.out.print("Год издания: ");
        int year = 0;
        try {
            year = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат года. Установлен год 0.");
        }
        
        System.out.print("Жанр: ");
        String genre = scanner.nextLine().trim();
        
        libraryManager.addBook(title, author, isbn, year, genre);
    }
    
    private static void editBook() {
        System.out.println("\n=== РЕДАКТИРОВАНИЕ КНИГИ ===");
        
        System.out.print("Введите ID книги: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Book book = libraryManager.findBookById(id);
            
            if (book == null) {
                System.out.println("Книга с ID " + id + " не найдена.");
                return;
            }
            
            System.out.println("\nТекущие данные:");
            System.out.println(book);
            System.out.println("\nОставьте поле пустым, чтобы не изменять его.");
            
            System.out.print("Новое название [" + book.getTitle() + "]: ");
            String title = scanner.nextLine().trim();
            
            System.out.print("Новый автор [" + book.getAuthor() + "]: ");
            String author = scanner.nextLine().trim();
            
            System.out.print("Новый ISBN [" + book.getIsbn() + "]: ");
            String isbn = scanner.nextLine().trim();
            
            System.out.print("Новый год [" + book.getYear() + "]: ");
            String yearStr = scanner.nextLine().trim();
            int year = yearStr.isEmpty() ? book.getYear() : Integer.parseInt(yearStr);
            
            System.out.print("Новый жанр [" + book.getGenre() + "]: ");
            String genre = scanner.nextLine().trim();
            
            System.out.print("Доступна? (да/нет) [" + (book.isAvailable() ? "да" : "нет") + "]: ");
            String availableStr = scanner.nextLine().trim().toLowerCase();
            boolean available = availableStr.isEmpty() ? book.isAvailable() : 
                               (availableStr.equals("да") || availableStr.equals("yes") || availableStr.equals("y"));
            
            if (libraryManager.editBook(id, title, author, isbn, year, genre, available)) {
                System.out.println("Книга успешно обновлена!");
            } else {
                System.out.println("Ошибка при обновлении книги.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ID.");
        }
    }
    
    private static void deleteBook() {
        System.out.println("\n=== УДАЛЕНИЕ КНИГИ ===");
        
        System.out.print("Введите ID книги для удаления: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            
            Book book = libraryManager.findBookById(id);
            if (book == null) {
                System.out.println("Книга с ID " + id + " не найдена.");
                return;
            }
            
            System.out.println("\nБудет удалена книга:");
            System.out.println(book);
            System.out.print("Подтвердите удаление (да/нет): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            
            if (confirm.equals("да") || confirm.equals("yes") || confirm.equals("y")) {
                if (libraryManager.deleteBook(id)) {
                    System.out.println("Книга успешно удалена!");
                } else {
                    System.out.println("Ошибка при удалении книги.");
                }
            } else {
                System.out.println("Удаление отменено.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ID.");
        }
    }
    
    private static void listBooks() {
        List<Book> books = libraryManager.getAllBooks();
        
        if (books.isEmpty()) {
            System.out.println("\nБиблиотека пуста.");
            return;
        }
        
        System.out.println("\n" + "=".repeat(100));
        System.out.println("СПИСОК ВСЕХ КНИГ (" + books.size() + " шт.)");
        System.out.println("=".repeat(100));
        
        for (Book book : books) {
            System.out.println(book);
        }
        
        System.out.println("=".repeat(100));
    }
    
    private static void searchBooks() {
        System.out.println("\n=== ПОИСК КНИГ ===");
        System.out.println("Искать по:");
        System.out.println("1. Названию");
        System.out.println("2. Автору");
        System.out.println("3. ISBN");
        System.out.println("4. Жанру");
        System.out.println("5. Году");
        System.out.println("6. Всем полям");
        
        System.out.print("Выберите критерий поиска: ");
        String choice = scanner.nextLine().trim();
        
        String searchBy = "all";
        switch (choice) {
            case "1": searchBy = "title"; break;
            case "2": searchBy = "author"; break;
            case "3": searchBy = "isbn"; break;
            case "4": searchBy = "genre"; break;
            case "5": searchBy = "year"; break;
            case "6": searchBy = "all"; break;
            default:
                System.out.println("Неверный выбор.");
                return;
        }
        
        System.out.print("Введите поисковый запрос: ");
        String searchTerm = scanner.nextLine().trim();
        
        List<Book> results = libraryManager.searchBooks(searchTerm, searchBy);
        
        if (results.isEmpty()) {
            System.out.println("\nКниги не найдены.");
        } else {
            System.out.println("\n" + "=".repeat(100));
            System.out.println("РЕЗУЛЬТАТЫ ПОИСКА (" + results.size() + " шт.)");
            System.out.println("=".repeat(100));
            
            for (Book book : results) {
                System.out.println(book);
            }
            
            System.out.println("=".repeat(100));
        }
    }
    
    private static void saveToFile() {
        saveToFile(FileManager.getDefaultFile());
    }
    
    private static void saveToFile(String filename) {
        List<Book> books = libraryManager.getAllBooks();
        
        if (FileManager.saveToFile(books, filename)) {
            System.out.println("✓ Данные успешно сохранены в файл: " + filename);
        } else {
            System.out.println("Ошибка при сохранении файла.");
        }
    }
    
    private static void loadFromFileMenu() {
        System.out.print("Введите имя файла (Enter для " + FileManager.getDefaultFile() + "): ");
        String filename = scanner.nextLine().trim();
        
        if (filename.isEmpty()) {
            filename = FileManager.getDefaultFile();
        }
        
        loadFromFile(filename);
    }
    
    private static void loadFromFile(String filename) {
        List<Book> books = FileManager.loadFromFile(filename);
        
        if (books != null) {
            libraryManager.setBooks(books);
            System.out.println("Загружено книг: " + books.size());
        } else {
            System.out.println("Ошибка при загрузке файла.");
        }
    }
    
    private static void exportToText() {
        System.out.print("Введите имя файла для экспорта (например, books.txt): ");
        String filename = scanner.nextLine().trim();
        
        if (filename.isEmpty()) {
            filename = "data/books.txt";
        }
        
        List<Book> books = libraryManager.getAllBooks();
        
        if (FileManager.exportToText(books, filename)) {
            System.out.println("Данные успешно экспортированы в файл: " + filename);
        } else {
            System.out.println("Ошибка при экспорте файла.");
        }
    } 
}
