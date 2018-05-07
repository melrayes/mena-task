package com.mena.repository;

import com.mena.model.News;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface NewsRepository extends PagingAndSortingRepository<News, Long> {

    List<News> findAllByOrderByTitleAsc();

    List<News> findAllByOrderByDateAscTitleAsc();

    List<News> findAllByOrderByDateAsc();

    List<News> findTop10ByTitleContainingIgnoreCase(String title);

    @Query("select news from News news where news.date =:date AND news.title like %:title% ")
    List<News> findTop10ByDateByTitleContainingIgnoreCase(@Param("date") String date, @Param("title") String title);

    @Query("select news from News news where news.date =:date")
    List<News> findTop10ByDateContainingIgnoreCase(@Param("date") String date);
}
