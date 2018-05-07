package com.mena.service;

import com.mena.repository.NewsRepository;
import com.mena.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsRepository newsRepository;

    public News save(News news) {
        return newsRepository.save(news);
    }

    public News findById(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        newsRepository.deleteById(id);
    }

    public List<News> search(String dateQuery, String titleQuery) {
        boolean searchByDate = dateQuery != null && !dateQuery.isEmpty();
        boolean searchByTitle = titleQuery != null && !titleQuery.isEmpty();

        if (searchByDate && searchByTitle)
            return newsRepository.findTop10ByDateByTitleContainingIgnoreCase(dateQuery, titleQuery);
        else if (searchByDate)
            return newsRepository.findTop10ByDateContainingIgnoreCase(dateQuery);
        else if (searchByTitle)
            return newsRepository.findTop10ByTitleContainingIgnoreCase(titleQuery);

        return null;
    }

    @Override
    public List<News> findAll() {
        return StreamSupport
                .stream(newsRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List findAllSortedBy(boolean sort_by_date, boolean sort_by_title) {
        if (sort_by_date && sort_by_title)
            return newsRepository.findAllByOrderByDateAscTitleAsc();
        else if (sort_by_date)
            return newsRepository.findAllByOrderByDateAsc();
        else if (sort_by_title)
            return newsRepository.findAllByOrderByTitleAsc();

        return findAll();
    }


}