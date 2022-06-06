package com.codegym.cms.service;

import com.codegym.cms.model.Blog;

import java.util.List;

public interface IBlogService {
    List<Blog> findAll();

    Blog findById(Long id);

    void save(Blog t);

    void remove(Long id);
}