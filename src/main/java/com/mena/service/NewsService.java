package com.mena.service;

import com.mena.model.News;

import java.util.List;

public interface NewsService {
    News save(News news);

    News findById(Long id);

    void delete(Long id);

    List<News> search(String dateQuery, String title);

    List findAll();

    List findAllSortedBy(boolean sort_by_date, boolean sort_by_title);
}
