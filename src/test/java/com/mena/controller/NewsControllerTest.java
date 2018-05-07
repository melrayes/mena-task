package com.mena.controller;

import com.mena.model.News;
import com.mena.repository.NewsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class NewsControllerTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private NewsRepository newsRepository;

    @Before
    public void setup() throws Exception {
        newsRepository.deleteAll();
    }

    @Test
    public void testNewsShouldBeCreated() throws Exception {
        HttpEntity<Object> news = getHttpEntity("{ \"title\": \"hello\", \"description\": \"test description\" , \"content\": \"test content\" }");
        ResponseEntity<News> resultAsset = template.postForEntity("/news", news, News.class);
        Assert.assertNotNull(resultAsset.getBody().getId());
    }

    @Test
    public void testNewsSearch() throws Exception {
        News news = new News();
        news.setTitle("test_title_to_search");
        news.setContent("test content");
        news = newsRepository.save(news);

        ResponseEntity<News[]> resultAsset = template.getForEntity("/news/search?title=title_to", News[].class);
        Assert.assertEquals(news.getId(), resultAsset.getBody()[0].getId());
    }

    @Test
    public void testFindAll() throws Exception {
        HttpEntity<Object> news = getHttpEntity("{ \"title\": \"hello\", \"description\": \"test description\" , \"content\": \"test content\" }");
        template.postForEntity("/news", news, News.class);

        news = getHttpEntity("{ \"title\": \"hello\", \"description\": \"test description\" , \"content\": \"test content\" }");
        template.postForEntity("/news", news, News.class);

        ResponseEntity<News[]> resultAsset = resultAsset = template.getForEntity("/news", News[].class);
        Assert.assertEquals(resultAsset.getBody().length, 2);
    }

    @Test
    public void testNewsDelete() throws Exception {
        News news = new News();
        news.setTitle("test title");
        news.setContent("test content");
        news = newsRepository.save(news);
        Assert.assertNotNull(newsRepository.findById(news.getId()));
        template.delete("/news/" + news.getId());
        Assert.assertEquals(Optional.empty(), newsRepository.findById(news.getId()));
    }

    @Test
    public void testNewsUpdate() throws Exception {
        News news = new News();
        news.setTitle("test title");
        news.setContent("test content");
        news = newsRepository.save(news);
        HttpEntity<Object> updatedNewsJson = getHttpEntity("{\"title\": \"updated_title\", \"content\": \"updated content\" }");

        template.put("/news/" + news.getId(), updatedNewsJson, News.class);
        Optional<News> updatedNews = newsRepository.findById(news.getId());
        Assert.assertEquals("updated_title", updatedNews.get().getTitle());
    }

    @Test
    public void testGetNews() throws Exception {
        News news = new News();
        news.setTitle("test title");
        news.setContent("test content");
        news = newsRepository.save(news);

        HttpEntity<News> actualNews = template.getForEntity("/news/" + news.getId(), News.class);
        Assert.assertEquals(news.getId(), actualNews.getBody().getId());
        Assert.assertEquals(news.hashCode(), actualNews.getBody().hashCode());
        Assert.assertTrue(news.equals(actualNews.getBody()));

    }

    private HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Object>(body, headers);
    }
}
