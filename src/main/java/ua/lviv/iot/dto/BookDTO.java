package ua.lviv.iot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jdk.jfr.Relational;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import ua.lviv.iot.model.Author;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(itemRelation = "book", collectionRelation = "books")
public class BookDTO extends RepresentationModel<BookDTO> {

    private Long id;
    private String imageSource;
    private String title;
    private String description;
    private String genre;
    private Double price;
    private String authorName;

}
