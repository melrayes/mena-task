package com.mena.repository;

import com.mena.model.News;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NewsRepositoryTest {

    @Autowired
    NewsRepository newsRepository;


    @Before
    public void setup() throws Exception {

    }

    @Test
    public void testFindNews() throws Exception {
        News news = new News();
        news.setTitle("test-title");
        news.setContent("test content");
        news = newsRepository.save(news);
        Assert.assertEquals(news, newsRepository.findById(news.getId()).get());
    }

    @Test
    public void testFailFindNews() throws Exception {
        News news = new News();
        news.setTitle("test-title");
        news.setContent("test content");
        news = newsRepository.save(news);
        newsRepository.delete(news);
        Assert.assertEquals(Optional.empty(), newsRepository.findById(news.getId()));
    }
}
