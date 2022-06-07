package com.codegym.cms.controller;

import com.codegym.cms.model.Blog;
import com.codegym.cms.model.Category;
import com.codegym.cms.service.blog.IBlogService;
import com.codegym.cms.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IBlogService blogService;

    @GetMapping("/categories")
    public ModelAndView listCategory(Pageable pageable){
        ModelAndView modelAndView = new ModelAndView("/category/list");
        modelAndView.addObject("categories", categoryService.findAll(pageable));
        return modelAndView;
    }

    @GetMapping("/view-category/{id}")
    public ModelAndView viewProvince(@PathVariable("id") Long id, Pageable pageable){
        Optional<Category> categoryOptional = categoryService.findById(id);
        if(!categoryOptional.isPresent()){
            return new ModelAndView("/error.404");
        }
        Iterable<Blog> blogs = blogService.findAllByCategory(categoryOptional.get(), pageable);

        ModelAndView modelAndView = new ModelAndView("/category/view");
        modelAndView.addObject("category", categoryOptional.get());
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }

    @GetMapping("/create-category")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/create-category")
    public ModelAndView saveCategory(@ModelAttribute("category") Category category, Pageable pageable){
        ModelAndView modelAndView = new ModelAndView("/category/list");
        categoryService.save(category);
        modelAndView.addObject("categories", categoryService.findAll(pageable));
        modelAndView.addObject("message", "Category created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-category/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("/category/edit");
        modelAndView.addObject("category", categoryService.findById(id));
        return modelAndView;
    }

    @PostMapping("/edit-category")
    public ModelAndView updateCategory(@ModelAttribute("category") Category category, Pageable pageable){
        ModelAndView modelAndView = new ModelAndView("/category/list");
        categoryService.save(category);
        modelAndView.addObject("categories", categoryService.findAll(pageable));
        modelAndView.addObject("message", "Updated category successfully");
        return modelAndView;
    }

    @GetMapping("delete-category/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/category/delete");
        modelAndView.addObject("category", categoryService.findById(id).get());
        return modelAndView;
    }

    @PostMapping("delete-category")
    public ModelAndView deleteCategory(@ModelAttribute("category") Category category, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("/category/list");
        categoryService.remove(category.getId());
        modelAndView.addObject("categories", categoryService.findAll(pageable));
        return modelAndView;
    }
}
