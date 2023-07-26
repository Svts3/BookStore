package ua.lviv.iot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.constants.ExceptionConstants;
import ua.lviv.iot.exception.AuthorNotFoundException;
import ua.lviv.iot.model.Author;
import ua.lviv.iot.repository.AuthorRepository;

import java.util.List;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author findAuthorById(Long id) throws AuthorNotFoundException {
        return authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(ExceptionConstants.AUTHOR_NOT_FOUND_EXCEPTION_MESSAGE));
    }
    public List<Author>findAllAuthors(){
        return authorRepository.findAll();
    }
    public Author saveAuthor(Author author){
        return authorRepository.save(author);
    }
    public Author updateAuthor(Long id, Author author) throws AuthorNotFoundException {
        Author author1 = findAuthorById(id);
        author1.setId(id);
        author1.setFirstName(author.getFirstName());
        author1.setLastName(author.getLastName());
        author1.setBooks(author.getBooks());
        return authorRepository.save(author1);
    }
    public String deleteAuthor(Long id) {
        authorRepository.deleteById(id);
        return String.format("Author with id %d was deleted!", id);
    }

}
