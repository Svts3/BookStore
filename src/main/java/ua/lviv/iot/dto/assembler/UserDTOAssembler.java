package ua.lviv.iot.dto.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ua.lviv.iot.controller.BlogController;
import ua.lviv.iot.controller.UserController;
import ua.lviv.iot.dto.BlogDTO;
import ua.lviv.iot.dto.UserDTO;
import ua.lviv.iot.model.User;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserDTOAssembler implements RepresentationModelAssembler<User, UserDTO> {
    @Override
    public CollectionModel<UserDTO> toCollectionModel(Iterable<? extends User> entities) {
        CollectionModel<UserDTO> userDTOS = RepresentationModelAssembler.super.toCollectionModel(entities);
        Link selfLink = linkTo(methodOn(UserController.class).findAllCustomers()).withSelfRel();
        userDTOS.add(selfLink);
        return userDTOS;
    }

    @Override
    public UserDTO toModel(User entity) {
        UserDTO userDTO = UserDTO.builder().id(entity.getId()).firstName(entity.getFirstName())
                .lastName(entity.getLastName()).email(entity.getEmail()).build();

        try {
            Link selfLink = linkTo(methodOn(UserController.class).findUserById(entity.getId(), SecurityContextHolder.getContext().getAuthentication())).withSelfRel();
            Link userBooksLink = linkTo(methodOn(UserController.class).findBooksByUserId(entity.getId())).withRel("books");
            Link userBlogsLink = linkTo(methodOn(UserController.class).findBlogsByUserId(entity.getId())).withRel("blogs");
            userDTO.add(selfLink,userBooksLink,userBlogsLink);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return userDTO;
    }
}
