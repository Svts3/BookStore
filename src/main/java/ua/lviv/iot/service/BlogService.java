package ua.lviv.iot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.exception.BlogNotFoundException;
import ua.lviv.iot.model.Blog;
import ua.lviv.iot.repository.BlogRepository;

import java.util.Date;
import java.util.List;

@Service
public class BlogService {

    private BlogRepository blogRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }


    public Blog saveBlog(Blog blog){
        blog.setCreationDate(new Date());
        return blogRepository.save(blog);
    }
    public List<Blog> findAllBlogs(){
        return blogRepository.findAll();
    }

    public Blog findBlogById(Long id) throws BlogNotFoundException {
        return blogRepository.findById(id).orElseThrow(()->new BlogNotFoundException("Blog with such id was not found!"));
    }

    public Blog updateBlog(Long id, Blog blog)throws Exception{
        Blog blog1 = findBlogById(id);
        blog1.setContent(blog.getContent());
        blog1.setTitle(blog.getTitle());
        blog1.setLastModifiedDate(new Date());
        return blogRepository.save(blog1);
    }


    public Blog deleteBlogById(Long id) throws BlogNotFoundException {
        Blog blog = findBlogById(id);
        blogRepository.deleteById(id);
        return blog;
    }






}
