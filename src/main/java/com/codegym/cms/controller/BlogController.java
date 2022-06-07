package com.codegym.cms.controller;

import com.codegym.cms.model.Blog;
import com.codegym.cms.model.Category;
import com.codegym.cms.service.blog.IBlogService;
import com.codegym.cms.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories(Pageable pageable){
        return categoryService.findAll(pageable);
    }

    @GetMapping("/create-blog")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/blog/create");
        modelAndView.addObject("blog", new Blog());
        return modelAndView;
    }

    @PostMapping("/create-blog")
    public ModelAndView saveBlog(@ModelAttribute("blog") Blog blog, Pageable pageable){
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("/blog/list");
        Page<Blog> blogs = blogService.findAll(pageable);
        modelAndView.addObject("blogs", blogs);
        modelAndView.addObject("message", "New blog created successfully");
        return modelAndView;
    }

    @GetMapping("/blogs")
    public ModelAndView listBlogs(@RequestParam("search") Optional<String> search, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("/blog/list");
        Page<Blog> blogs;
        if (search.isPresent()){
            blogs = blogService.findAllByTitleContaining(search.get(), pageable);
        } else {
            blogs = blogService.findAll(pageable);
        }
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }

    @GetMapping("/edit-blog/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/blog/edit");
        Blog blog = blogService.findById(id).get();
        modelAndView.addObject("blog", blog);
        return modelAndView;
    }

    @PostMapping("/edit-blog")
    public ModelAndView updateBlog(@ModelAttribute("blog") Blog blog, Pageable pageable){
        ModelAndView modelAndView = new ModelAndView("/blog/list");
        blogService.save(blog);
        Page<Blog> blogs = blogService.findAll(pageable);
        modelAndView.addObject("blogs", blogs);
        modelAndView.addObject("message", "Blog updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete-blog/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("/blog/delete");
        Blog blog = blogService.findById(id).get();
        modelAndView.addObject("blog", blog);
        return modelAndView;
    }

    @GetMapping("/delete-and-show-blog/{id}")
    public ModelAndView deleteBlog(@PathVariable Long id, Pageable pageable){
        ModelAndView modelAndView = new ModelAndView("/blog/list");
        blogService.remove(id);
        Page<Blog> blogs = blogService.findAll(pageable);
        modelAndView.addObject("blogs", blogs);
        modelAndView.addObject("message", "Blog deleted successfully");
        return modelAndView;
    }
}
