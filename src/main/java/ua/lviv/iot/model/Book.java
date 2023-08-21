package ua.lviv.iot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageSource;
    private String title;
    @Column(name = "description", length = 2000)
    private String description;
    private String genre;
    private Double price;
    @Column(name = "date_of_addition")
    private Date dateOfAddition;
    @ManyToMany(mappedBy = "books")
    private List<User> user;
    @ManyToOne()
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @OneToMany()
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private List<Review>reviews;


}
