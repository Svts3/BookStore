package ua.lviv.iot.controller;

import org.aspectj.weaver.bcel.ExceptionRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.dto.BlogDTO;
import ua.lviv.iot.dto.assembler.BlogDTOAssembler;
import ua.lviv.iot.model.Blog;
import ua.lviv.iot.service.BlogService;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {

    private BlogService blogService;

    private BlogDTOAssembler blogDTOAssembler;
    @Autowired
    public BlogController(BlogService blogService, BlogDTOAssembler blogDTOAssembler) {
        this.blogService = blogService;
        this.blogDTOAssembler = blogDTOAssembler;
    }

    @GetMapping("/")
    public ResponseEntity<CollectionModel<BlogDTO>> findAllBlogs() {
        List<Blog>blogs = blogService.findAllBlogs();
        CollectionModel<BlogDTO> blogDTOS = blogDTOAssembler.toCollectionModel(blogs);
        return new ResponseEntity<>(blogDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogDTO> findBlogById(@PathVariable("id") Long id) throws Exception {
        Blog blog = blogService.findBlogById(id);
        BlogDTO blogDTO = blogDTOAssembler.toModel(blog);
        return new ResponseEntity<>(blogDTO, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<BlogDTO> saveBlog(@RequestBody Blog blog){
        Blog blog1 = blogService.saveBlog(blog);
        BlogDTO blogDTO = blogDTOAssembler.toModel(blog1);
        return new ResponseEntity<>(blogDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogDTO> updateBlog(@PathVariable("id")Long id, @RequestBody Blog blog) throws Exception{
        Blog blog1 = blogService.updateBlog(id, blog);
        BlogDTO blogDTO = blogDTOAssembler.toModel(blog1);
        return new ResponseEntity<>(blogDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BlogDTO> deleteBlog(@PathVariable("id")Long id) throws Exception{
        Blog blog = blogService.deleteBlogById(id);
        BlogDTO blogDTO = blogDTOAssembler.toModel(blog);
        return new ResponseEntity<>(blogDTO, HttpStatus.OK);
    }



}
