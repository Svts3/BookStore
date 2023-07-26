package ua.lviv.iot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.dto.ReviewDTO;
import ua.lviv.iot.dto.assembler.ReviewDTOAssembler;
import ua.lviv.iot.model.Review;
import ua.lviv.iot.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;

    private ReviewDTOAssembler reviewDTOAssembler;

    @Autowired
    public ReviewController(ReviewService reviewService, ReviewDTOAssembler reviewDTOAssembler) {
        this.reviewService = reviewService;
        this.reviewDTOAssembler = reviewDTOAssembler;
    }


    @GetMapping("/")
    public ResponseEntity<CollectionModel<ReviewDTO>> findAllReviews() {
        List<Review> reviews = reviewService.findAllReviews();
        CollectionModel<ReviewDTO> reviewDTOS = reviewDTOAssembler.toCollectionModel(reviews);
        return new ResponseEntity<>(reviewDTOS, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> findReviewById(@PathVariable("id") Long id) throws Exception {
        Review review = reviewService.findReviewById(id);
        ReviewDTO reviewDTO = reviewDTOAssembler.toModel(review);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ReviewDTO> saveReview(@RequestBody Review review) {
        Review review1 = reviewService.saveReview(review);
        ReviewDTO reviewDTO = reviewDTOAssembler.toModel(review1);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable("id") Long id, @RequestBody Review review)
            throws Exception {
        Review review1 = reviewService.updateReview(id, review);
        ReviewDTO reviewDTO = reviewDTOAssembler.toModel(review1);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewDTO> deleteReview(@PathVariable("id") Long id) throws Exception {
        Review review1 = reviewService.deelteReview(id);
        ReviewDTO reviewDTO = reviewDTOAssembler.toModel(review1);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

}
