package ua.lviv.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.lviv.iot.model.Author;
import ua.lviv.iot.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book>findBooksByUserId(Long id);

     Optional<Book> findBookByTitle(String title);
     @Query(value = "SELECT * FROM book JOIN (SELECT book_id, avg(rate)as rate FROM REVIEWS group by book_id)r" +
             " on book.id=r.book_id order by r.rate DESC LIMIT 5", nativeQuery = true)
     List<Book>findTopRatedBooks();

}
