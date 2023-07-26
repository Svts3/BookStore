package ua.lviv.iot.dto.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ua.lviv.iot.controller.AuthorController;
import ua.lviv.iot.controller.BookController;
import ua.lviv.iot.dto.AuthorDTO;
import ua.lviv.iot.dto.BookDTO;
import ua.lviv.iot.exception.AuthorNotFoundException;
import ua.lviv.iot.model.Author;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@Component
public class AuthorDTOAssembler implements RepresentationModelAssembler<Author, AuthorDTO> {
    @Override
    public CollectionModel<AuthorDTO> toCollectionModel(Iterable<? extends Author> entities) {
        CollectionModel<AuthorDTO> authorDTOS = RepresentationModelAssembler.super.toCollectionModel(entities);
        Link selfLink = linkTo(methodOn(AuthorController.class).findAll()).withSelfRel();
        authorDTOS.add(selfLink);
        return authorDTOS;
    }

    @Override
    public AuthorDTO toModel(Author entity) {
        AuthorDTO authorDTO = AuthorDTO.builder().id(entity.getId()).firstName(entity.getFirstName()).lastName(entity.getLastName()).build();
        try {
            linkTo(methodOn(AuthorController.class).findById(entity.getId())).withSelfRel();
        } catch (AuthorNotFoundException e) {
            throw new RuntimeException(e);
        }
        return authorDTO;
    }
}
