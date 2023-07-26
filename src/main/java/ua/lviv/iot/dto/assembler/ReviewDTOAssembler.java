package ua.lviv.iot.dto.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ua.lviv.iot.controller.AuthorController;
import ua.lviv.iot.controller.ReviewController;
import ua.lviv.iot.dto.AuthorDTO;
import ua.lviv.iot.dto.ReviewDTO;
import ua.lviv.iot.exception.AuthorNotFoundException;
import ua.lviv.iot.exception.ReviewNotFoundException;
import ua.lviv.iot.model.Author;
import ua.lviv.iot.model.Review;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ReviewDTOAssembler implements RepresentationModelAssembler<Review, ReviewDTO> {
    @Override
    public CollectionModel<ReviewDTO> toCollectionModel(Iterable<? extends Review> entities) {
        CollectionModel<ReviewDTO> reviewDTOS = RepresentationModelAssembler.super.toCollectionModel(entities);
        Link selfLink = linkTo(methodOn(ReviewController.class).findAllReviews()).withSelfRel();
        reviewDTOS.add(selfLink);
        return reviewDTOS;
    }

    @Override
    public ReviewDTO toModel(Review entity) {
        ReviewDTO reviewDTO = ReviewDTO.builder().id(entity.getId()).content(entity.getContent())
                .user(entity.getUser().getFirstName() + entity.getUser().getLastName())
                .rate(entity.getRate())
                .book(entity.getBook().getTitle())
                .creationDate(entity.getCreationDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .build();
        try {
            Link selfLink = linkTo(methodOn(ReviewController.class).findReviewById(entity.getId())).withSelfRel();
            reviewDTO.add(selfLink);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return reviewDTO;
    }
}
