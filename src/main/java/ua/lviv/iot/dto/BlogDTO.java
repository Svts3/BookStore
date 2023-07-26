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
@Relation(itemRelation = "blog", collectionRelation = "blogs")
public class BlogDTO extends RepresentationModel<BlogDTO> {

    private Long id;
    private String title;
    private String content;
    private Date creationDate;
    private Date lastModifiedDate;
    private String authorName;

}
