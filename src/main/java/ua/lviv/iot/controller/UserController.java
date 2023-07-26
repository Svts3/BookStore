package ua.lviv.iot.controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.dto.BlogDTO;
import ua.lviv.iot.dto.BookDTO;
import ua.lviv.iot.dto.ReviewDTO;
import ua.lviv.iot.dto.UserDTO;
import ua.lviv.iot.dto.assembler.BlogDTOAssembler;
import ua.lviv.iot.dto.assembler.BookDTOAssembler;
import ua.lviv.iot.dto.assembler.ReviewDTOAssembler;
import ua.lviv.iot.dto.assembler.UserDTOAssembler;
import ua.lviv.iot.model.Blog;
import ua.lviv.iot.model.Book;
import ua.lviv.iot.model.Review;
import ua.lviv.iot.model.User;
import ua.lviv.iot.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private UserDTOAssembler userDTOAssembler;

    private BookDTOAssembler bookDTOAssembler;

    private BlogDTOAssembler blogDTOAssembler;

    private ReviewDTOAssembler reviewDTOAssembler;

    @Autowired
    public UserController(UserService userService, UserDTOAssembler userDTOAssembler, BookDTOAssembler bookDTOAssembler,
                          BlogDTOAssembler blogDTOAssembler, ReviewDTOAssembler reviewDTOAssembler) {

        this.userService = userService;
        this.userDTOAssembler = userDTOAssembler;
        this.bookDTOAssembler = bookDTOAssembler;
        this.blogDTOAssembler = blogDTOAssembler;
        this.reviewDTOAssembler = reviewDTOAssembler;
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable("id") Long id, Authentication authentication) throws Exception {
        User user = userService.findUserById(id);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User authenticationUser = userService.findByEmail(userDetails.getUsername());
        if (!id.equals(authenticationUser.getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDTO userDTO = userDTOAssembler.toModel(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<CollectionModel<UserDTO>> findAllCustomers() {
        List<User> users = userService.findAllCustomers();
        CollectionModel<UserDTO> collectionModel = userDTOAssembler.toCollectionModel(users);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);

    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> findUserByEmail(@PathVariable("email") String email, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (!email.equals(userDetails.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userService.findByEmail(email);
        UserDTO userDTO = userDTOAssembler.toModel(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }

    @PostMapping("/")
    public ResponseEntity<UserDTO> saveCustomer(@RequestBody User user) {
        User user1 = userService.saveCustomer(user);
        UserDTO userDTO = userDTOAssembler.toModel(user1);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> addBooksToUser(@PathVariable("id") Long id, @RequestBody List<Book>books) throws Exception {
        User user1 = userService.addBooksToUser(id,books);
        UserDTO userDTO = userDTOAssembler.toModel(user1);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteCustomer(@PathVariable("id") Long id) throws Exception {
        User user = userService.findUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDTO userDTO = userDTOAssembler.toModel(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}/books/favourite")
    public ResponseEntity<CollectionModel<BookDTO>>findFavouriteBooksByUserId(@PathVariable("id")Long id)
            throws Exception {
        List<Book> books = userService.findFavouriteBooksByUserId(id);
        CollectionModel<BookDTO>bookDTOS = bookDTOAssembler.toCollectionModel(books);
        return new ResponseEntity<>(bookDTOS,HttpStatus.OK);

    }

    @PostMapping("/{id}/books/favourite")
    public ResponseEntity<BookDTO>addBookToFavourite(@PathVariable("id")Long id, @RequestBody Book book)
            throws Exception {
        Book book1 = userService.addBookToFavourite(id, book);
        BookDTO bookDTO= bookDTOAssembler.toModel(book1);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<CollectionModel<BookDTO>> findBooksByUserId(@PathVariable("id") Long id) throws Exception {
        List<Book> books = userService.findBooksByUserId(id);
        CollectionModel<BookDTO> collectionModel = bookDTOAssembler.toCollectionModel(books);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/{id}/blogs")
    public ResponseEntity<CollectionModel<BlogDTO>> findBlogsByUserId(@PathVariable("id") Long id) throws Exception {
        List<Blog> blogs = userService.findUserBlogsById(id);
        CollectionModel<BlogDTO> blogDTOS = blogDTOAssembler.toCollectionModel(blogs);
        return new ResponseEntity<>(blogDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<CollectionModel<ReviewDTO>> findReviewsByUserId(@PathVariable("id") Long id) throws Exception {
        List<Review> reviews = userService.findUserReviewsById(id);
        CollectionModel<ReviewDTO> reviewDTOS = reviewDTOAssembler.toCollectionModel(reviews);
        return new ResponseEntity<>(reviewDTOS, HttpStatus.OK);
    }
}
