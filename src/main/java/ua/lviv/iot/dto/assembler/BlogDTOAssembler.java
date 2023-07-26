package ua.lviv.iot.dto.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ua.lviv.iot.controller.BlogController;
import ua.lviv.iot.dto.AuthorDTO;
import ua.lviv.iot.dto.BlogDTO;
import ua.lviv.iot.model.Author;
import ua.lviv.iot.model.Blog;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@Component
public class BlogDTOAssembler implements RepresentationModelAssembler<Blog, BlogDTO> {
    @Override
    public CollectionModel<BlogDTO> toCollectionModel(Iterable<? extends Blog> entities) {
        CollectionModel<BlogDTO> blogDTOS = RepresentationModelAssembler.super.toCollectionModel(entities);
        Link selfLink =linkTo(methodOn(BlogController.class)).withSelfRel();
        blogDTOS.add(selfLink);
        return blogDTOS;
    }

    @Override
    public BlogDTO toModel(Blog entity) {
        BlogDTO blogDTO = BlogDTO.builder().id(entity.getId()).title(entity.getTitle()).content(entity.getContent())
                .creationDate(entity.getCreationDate()).lastModifiedDate(entity.getLastModifiedDate()).authorName(String.format("%s %s", entity.getUser().getFirstName(), entity.getUser().getLastName())).build();
        try {
            linkTo(methodOn(BlogController.class).findBlogById(entity.getId())).withSelfRel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return blogDTO;
    }
}
