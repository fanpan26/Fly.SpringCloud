package com.fyp.fly.services.article;


import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.services.article.dto.ArticleEditDto;
import com.fyp.fly.services.article.service.ArticleService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author fyp
 * @crate 2019/3/16 11:35
 * @project fly
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArticleServiceApplication.class)
@WebAppConfiguration
public class ArticleServiceApplicationTest {

    @Before
    public void before(){

    }

    @After
    public void after(){

    }

    @Autowired
    private ArticleService articleService;

    @Test
    public void addArticleValidateTest() {
        ArticleEditDto article = new ArticleEditDto();
        JsonResult result = articleService.add(article);
        Assert.assertTrue(result.getCode() == -1);
    }

    @Test
    public void addArticleTest() {
        ArticleEditDto article = new ArticleEditDto();
        article.setId(System.currentTimeMillis()/1000);
        article.setCategory(1);
        article.setContent("this is a test");
        article.setExperience(20);
        article.setTitle("this is a test");
        article.setUserId(1L);
        JsonResult result = articleService.add(article);
        Assert.assertTrue("lalalal",result.getCode() == 0);
    }
}
