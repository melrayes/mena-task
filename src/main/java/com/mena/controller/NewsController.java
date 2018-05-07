package com.mena.controller;

import com.mena.model.News;
import com.mena.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * The News controller.
 */
@RestController
public class NewsController {

    /**
     * The News service.
     */
    @Autowired
    NewsService newsService;

    /**
     * Find all news entity instances.
     *
     * @param sort_by_date  the sort by date
     * @param sort_by_title the sort by title
     * @return the response entity
     */
    @GetMapping(path = "news")
    public ResponseEntity<List<News>> findAll(@RequestParam(value = "sort_by_date", required = false) boolean sort_by_date,
                                              @RequestParam(value = "sort_by_title", required = false) boolean sort_by_title) {
        if (sort_by_date || sort_by_title)
            return new ResponseEntity<>(newsService.findAllSortedBy(sort_by_date, sort_by_title), HttpStatus.OK);

        return new ResponseEntity<>(newsService.findAll(), HttpStatus.OK);
    }

    /**
     * Find by News id.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping(path = "news/{news-id}")
    public ResponseEntity<News> findById(@PathVariable("news-id") Long id) {
        News news = newsService.findById(id);
        if (news != null)
            return new ResponseEntity<>(news, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Create news entity.
     *
     * @param news the news
     * @return the response entity
     */
    @PostMapping(path = "news")
    public ResponseEntity<News> createNews(@RequestBody News news) {
        return new ResponseEntity<>(newsService.save(news), HttpStatus.CREATED);
    }


    /**
     * Update news entity.
     *
     * @param id   the id
     * @param news the news
     * @return the response entity
     */
    @PutMapping(path = "news/{news-id}")
    public ResponseEntity<News> updateNews(@PathVariable("news-id") Long id, @RequestBody News news) {
        news.setId(id);
        return new ResponseEntity<>(newsService.save(news), HttpStatus.OK);
    }

    /**
     * Delete by news id .
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping(path = "news/{news-id}")
    public ResponseEntity<News> deleteById(@PathVariable("news-id") Long id) {
        newsService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Search news entity.
     *
     * @param dateQuery  the date query
     * @param titleQuery the title query
     * @return the response entity
     */
    @GetMapping(path = "news/search")
    public ResponseEntity<List<News>> searchNews(@RequestParam(value = "date", required = false) String dateQuery,
                                                 @RequestParam(value = "title", required = false) String titleQuery) {
        List<News> searchResult = newsService.search(dateQuery, titleQuery);
        if (searchResult != null && !searchResult.isEmpty())
            return new ResponseEntity<>(searchResult, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
