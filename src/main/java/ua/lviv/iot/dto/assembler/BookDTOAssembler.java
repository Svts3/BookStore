package ua.lviv.iot.dto.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ua.lviv.iot.controller.BlogController;
import ua.lviv.iot.controller.BookController;
import ua.lviv.iot.dto.BookDTO;
import ua.lviv.iot.dto.UserDTO;
import ua.lviv.iot.model.Book;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@Component
public class BookDTOAssembler implements RepresentationModelAssembler<Book, BookDTO> {
    @Override
    public CollectionModel<BookDTO> toCollectionModel(Iterable<? extends Book> entities) {
        CollectionModel<BookDTO> bookDTOS = RepresentationModelAssembler.super.toCollectionModel(entities);
        Link selfLink = linkTo(methodOn(BookController.class).findAllBooks()).withSelfRel();
        bookDTOS.add(selfLink);
        return bookDTOS;
    }

    @Override
    public BookDTO toModel(Book entity) {
        BookDTO bookDTO = BookDTO.builder().id(entity.getId()).imageSource(entity.getImageSource()).title(entity.getTitle()).description(entity.getDescription()).
                genre(entity.getGenre()).price(entity.getPrice()).authorName(entity.getAuthor().getFirstName()+" "+entity.getAuthor().getLastName()).build();
        try {
            linkTo(methodOn(BookController.class).findBookById(entity.getId())).withSelfRel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return bookDTO;

    }
}
