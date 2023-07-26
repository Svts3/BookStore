package ua.lviv.iot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.exception.ReviewNotFoundException;
import ua.lviv.iot.model.Review;
import ua.lviv.iot.repository.ReviewRepository;

import java.util.Date;
import java.util.List;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review saveReview(Review review) {
        review.setCreationDate(new Date());
        return reviewRepository.save(review);
    }

    public Review findReviewById(Long id) throws Exception {
        return reviewRepository.findById(id).orElseThrow(
                () -> new ReviewNotFoundException("Review with such id was not found!"));
    }

    public List<Review>findAllReviews(){
        return reviewRepository.findAll();
    }


    public Review updateReview(Long id, Review review) throws Exception {
        Review review1 = findReviewById(id);
        review1.setRate(review.getRate());
        review1.setContent(review.getContent());
        review1.setLastModifiedDate(new Date());
        return reviewRepository.save(review1);
    }

    public List<Review>findReviewsByBookId(Long id){
        return reviewRepository.findReviewsByBookId(id);
    }

    public Review deelteReview(Long id) throws Exception {
        Review review1 = findReviewById(id);
        reviewRepository.deleteById(id);
        return review1;
    }


}
