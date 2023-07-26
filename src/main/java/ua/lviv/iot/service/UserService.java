package ua.lviv.iot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.lviv.iot.constants.ExceptionConstants;
import ua.lviv.iot.exception.BookNotFoundException;
import ua.lviv.iot.exception.UserNotFoundException;
import ua.lviv.iot.model.Blog;
import ua.lviv.iot.model.Book;
import ua.lviv.iot.model.Review;
import ua.lviv.iot.model.User;
import ua.lviv.iot.repository.*;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private BookRepository bookRepository;

    private ReviewRepository reviewRepository;

    private BlogRepository blogRepository;


    @Autowired
    public UserService(UserRepository userRepository, BookRepository bookRepository, RoleRepository roleRepository,
                       ReviewRepository reviewRepository, BlogRepository blogRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.blogRepository = blogRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with such email was not found!"));
    }

    public User findUserById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(ExceptionConstants.CUSTOMER_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> findAllCustomers() {
        return userRepository.findAll();
    }

    public User saveCustomer(User user) {
        return userRepository.save(user);
    }

    public User updateCustomer(Long id, User user) throws Exception {
        User user1 = findUserById(id);
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setEmail(user.getEmail());
        user1.setBooks(user.getBooks());
        return userRepository.save(user1);
    }

    public List<Book> findFavouriteBooksByUserId(Long id) throws Exception {
        User user = findUserById(id);
        return user.getFavouriteBooks();
    }

    public Book addBookToFavourite(Long userId, Book book) throws Exception {
        User user = findUserById(userId);
        Book book1 = bookRepository.findById(book.getId()).orElseThrow(() -> new BookNotFoundException("Book with id " +
                book.getId() + "was not found!"));
        user.getFavouriteBooks().add(book1);
        userRepository.save(user);
        return book1;
    }

    public User addBooksToUser(Long id, List<Book> books) throws Exception {
        User user1 = findUserById(id);
        books.forEach((i) -> {
            try {
                Book book = bookRepository.findById(i.getId()).orElseThrow(
                        () -> new BookNotFoundException("Book with id " + i.getId() + "was not found!"));
                user1.getBooks().add(book);
            } catch (BookNotFoundException e) {
                throw new RuntimeException(e);
            }

        });
        return userRepository.save(user1);
    }

    public List<Book> findBooksByUserId(Long id) throws Exception {
        return bookRepository.findBooksByUserId(id);
    }

    public String deleteCustomer(Long id) {
        userRepository.deleteById(id);
        return String.format("Customer with id %d was deleted!", id);
    }

    public List<Blog> findUserBlogsById(Long id) throws Exception {
        return blogRepository.findBlogdByUserId(id);
    }

    public List<Review> findUserReviewsById(Long id) throws Exception {
        return reviewRepository.findReviewsByUserId(id);
    }


}
