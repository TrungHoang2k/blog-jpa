package com.codegym.cms.repository;

import com.codegym.cms.model.Blog;

import java.util.List;

public interface IBLogRepository {
    List<Blog> findAll();

    Blog findById(Long id);

    void save(Blog t);

    void remove(Long id);
}
