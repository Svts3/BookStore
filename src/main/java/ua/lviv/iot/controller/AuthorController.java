package ua.lviv.iot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.dto.AuthorDTO;
import ua.lviv.iot.dto.assembler.AuthorDTOAssembler;
import ua.lviv.iot.exception.AuthorNotFoundException;
import ua.lviv.iot.model.Author;
import ua.lviv.iot.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private AuthorService authorService;

    private AuthorDTOAssembler authorDTOAssembler;

    @Autowired
    public AuthorController(AuthorService authorService, AuthorDTOAssembler authorDTOAssembler){
        this.authorService = authorService;
        this.authorDTOAssembler = authorDTOAssembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable("id")Long id) throws AuthorNotFoundException {
        Author author = authorService.findAuthorById(id);
        AuthorDTO authorDTO =  authorDTOAssembler.toModel(author);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<CollectionModel<AuthorDTO>> findAll(){
        List<Author> authors = authorService.findAllAuthors();
        CollectionModel<AuthorDTO>collectionModel = authorDTOAssembler.toCollectionModel(authors);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<AuthorDTO> saveAuthor(@RequestBody Author author){

        Author author1 = authorService.saveAuthor(author);
        AuthorDTO authorDTO = authorDTOAssembler.toModel(author1);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable("id")Long id, @RequestBody Author author) throws AuthorNotFoundException {
        Author author1 = authorService.updateAuthor(id, author);
        AuthorDTO authorDTO = authorDTOAssembler.toModel(author1);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<AuthorDTO> deleteAuthor(@PathVariable("id")Long id) throws AuthorNotFoundException {
        Author author = authorService.findAuthorById(id);
        if(author==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorService.deleteAuthor(id);

        AuthorDTO authorDTO = authorDTOAssembler.toModel(author);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }


}
