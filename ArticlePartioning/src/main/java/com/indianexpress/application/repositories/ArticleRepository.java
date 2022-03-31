package com.indianexpress.application.repositories;

import com.indianexpress.application.models.Article;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends Neo4jRepository<Article, Long> {
    Optional<Article> findOneByTitle(String title);
}
