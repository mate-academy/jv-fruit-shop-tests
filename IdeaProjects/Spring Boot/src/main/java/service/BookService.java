package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.BookRepository;

import java.awt.print.Book;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<java.awt.print.Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<java.awt.print.Book> getBooksByCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }
}