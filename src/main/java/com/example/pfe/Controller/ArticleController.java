package com.example.pfe.Controller;


import com.example.pfe.dto.ArticleDto;
import com.example.pfe.model.Article;
import com.example.pfe.repositories.ArticleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/articles")
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping
    public ResponseEntity<?> addArticle(@Valid @RequestBody ArticleDto articleDto) {
        if (articleDto.getName().equals("")) {
            return new ResponseEntity<>("Please enter article name ", HttpStatus.BAD_REQUEST);
        }
        if (articleDto.getPrice() == 0) {
            return new ResponseEntity<>("Please enter article price ", HttpStatus.BAD_REQUEST);
        }
        Article article = new Article();
        article.setName(articleDto.getName());
        article.setPrice(articleDto.getPrice());
        articleRepository.save(article);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articleList = articleRepository.findAll();
        return new ResponseEntity<>(articleList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable() int id) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            return new ResponseEntity<>(article.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Article not found !", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticleById(@PathVariable() int id) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            articleRepository.delete(article.get());
            return new ResponseEntity<>("Article deleted successfully !", HttpStatus.OK);
        }
        return new ResponseEntity<>("Article not found !", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateArticleById(@PathVariable() int id, @Valid @RequestBody ArticleDto articleDto) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            if (!articleDto.getName().equals("")) {
                article.get().setName(articleDto.getName());
            }
            if (articleDto.getPrice() != 0) {
                article.get().setPrice(articleDto.getPrice());
            }
            articleRepository.save(article.get());
            return new ResponseEntity<>(article.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Article not found !", HttpStatus.NOT_FOUND);
    }
}
