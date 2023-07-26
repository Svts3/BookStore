package ua.lviv.iot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.constants.ExceptionConstants;
import ua.lviv.iot.exception.BookNotFoundException;
import ua.lviv.iot.model.Book;
import ua.lviv.iot.repository.BookRepository;

import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book findBookById(Long id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(ExceptionConstants.BOOK_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    public Book findBookByTitle(String title) throws BookNotFoundException {
        return bookRepository.findBookByTitle(title).orElseThrow(() -> new BookNotFoundException(
                String.format("Book %s was not found", title)));
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }
    public List<Book>findTopRatedBooks(){
        return bookRepository.findTopRatedBooks();
    }
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book book) throws BookNotFoundException {
        Book book1 = findBookById(id);
        book1.setAuthor(book.getAuthor());
        book1.setTitle(book.getTitle());
        book1.setPrice(book.getPrice());
        book1.setGenre(book.getGenre());
        book1.setDescription(book.getDescription());
        return bookRepository.save(book1);
    }

    public String deleteBook(Long id) {
        bookRepository.deleteById(id);
        return String.format("Book with id %d was deleted!", id);
    }

}
