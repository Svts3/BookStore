package ua.lviv.iot.controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.lviv.iot.dto.BookDTO;
import ua.lviv.iot.dto.ReviewDTO;
import ua.lviv.iot.dto.assembler.BookDTOAssembler;
import ua.lviv.iot.dto.assembler.ReviewDTOAssembler;
import ua.lviv.iot.exception.BookNotFoundException;
import ua.lviv.iot.model.Book;
import ua.lviv.iot.model.Review;
import ua.lviv.iot.repository.BookRepository;
import ua.lviv.iot.service.BookService;
import ua.lviv.iot.service.ReviewService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    private BookDTOAssembler bookDTOAssembler;

    private ReviewDTOAssembler reviewDTOAssembler;

    private ReviewService reviewService;


    @Autowired
    public BookController(BookService bookService, BookDTOAssembler bookDTOAssembler, ReviewService reviewService,
                          ReviewDTOAssembler reviewDTOAssembler) {
        this.bookService = bookService;
        this.bookDTOAssembler = bookDTOAssembler;
        this.reviewService = reviewService;
        this.reviewDTOAssembler = reviewDTOAssembler;
    }

    @GetMapping("/")
    public ResponseEntity<CollectionModel<BookDTO>> findAllBooks() {
        List<Book> books = bookService.findAllBooks();
        CollectionModel<BookDTO> bookDTOs = bookDTOAssembler.toCollectionModel(books);
        return new ResponseEntity<>(bookDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> findBookById(@PathVariable("id") Long id) throws Exception {
        Book book = bookService.findBookById(id);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookDTO bookDTO = bookDTOAssembler.toModel(book);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }
    @GetMapping("/title/{title}")
    public ResponseEntity<BookDTO> findBookByTitle(@PathVariable("title")String title) throws Exception{
        Book book = bookService.findBookByTitle(title);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookDTO bookDTO = bookDTOAssembler.toModel(book);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }
    @GetMapping("/top")
    public ResponseEntity<CollectionModel<BookDTO>>findTopRatedBooks(){
        List<Book> books = bookService.findTopRatedBooks();
        CollectionModel<BookDTO> bookDTOS = bookDTOAssembler.toCollectionModel(books);
        return new ResponseEntity<>(bookDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<CollectionModel<ReviewDTO>> findReviewsByBookId(@PathVariable("id") Long id) {
        List<Review> reviews = reviewService.findReviewsByBookId(id);
        CollectionModel<ReviewDTO>reviewDTOS = reviewDTOAssembler.toCollectionModel(reviews);
        return new ResponseEntity<>(reviewDTOS, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<BookDTO> saveBook(@RequestBody Book book) {
        Book book1 = bookService.saveBook(book);
        BookDTO bookDTO = bookDTOAssembler.toModel(book1);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") Long id, @RequestBody Book book) throws BookNotFoundException {
        Book book1 = bookService.updateBook(id, book);
        BookDTO bookDTO = bookDTOAssembler.toModel(book1);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDTO> deleteBook(@PathVariable("id") Long id) throws BookNotFoundException {
        Book book = bookService.findBookById(id);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.deleteBook(id);
        BookDTO bookDTO = bookDTOAssembler.toModel(book);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }
}
