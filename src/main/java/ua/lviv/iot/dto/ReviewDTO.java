package ua.lviv.iot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(itemRelation = "review", collectionRelation = "reviews")
public class ReviewDTO extends RepresentationModel<ReviewDTO> {

    private Long id;
    private String content;
    private Integer rate;
    private String book;
    private String user;
    private Date creationDate;
    private Date lastModifiedDate;


}
