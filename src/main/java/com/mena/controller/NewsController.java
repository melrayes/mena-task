package com.mena.controller;

import com.mena.model.News;
import com.mena.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class NewsController {

    @Autowired
    NewsService newsService;

    @GetMapping(path = "news")
    public ResponseEntity<List<News>> findAll(@RequestParam(value = "sort_by_date", required = false) boolean sort_by_date,
                                              @RequestParam(value = "sort_by_title", required = false) boolean sort_by_title) {
        if (sort_by_date || sort_by_title)
            return new ResponseEntity<>(newsService.findAllSortedBy(sort_by_date, sort_by_title), HttpStatus.OK);

        return new ResponseEntity<>(newsService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "news/{news-id}")
    public ResponseEntity<News> findById(@PathVariable("news-id") Long id) {
        News news = newsService.findById(id);
        if (news != null)
            return new ResponseEntity<>(news, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "news")
    public ResponseEntity<News> createNews(@RequestBody News news) {
        return new ResponseEntity<>(newsService.save(news), HttpStatus.CREATED);
    }


    @PutMapping(path = "news/{news-id}")
    public ResponseEntity<News> updateNews(@PathVariable("news-id") Long id, @RequestBody News news) {
        news.setId(id);
        return new ResponseEntity<>(newsService.save(news), HttpStatus.OK);
    }

    @DeleteMapping(path = "news/{news-id}")
    public ResponseEntity<News> deleteById(@PathVariable("news-id") Long id) {
        newsService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "news/search")
    public ResponseEntity<List<News>> searchNews(@RequestParam(value = "date", required = false) String dateQuery,
                                                 @RequestParam(value = "title", required = false) String titleQuery) {
        List<News> searchResult = newsService.search(dateQuery, titleQuery);
        if (searchResult != null && !searchResult.isEmpty())
            return new ResponseEntity<>(searchResult, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
