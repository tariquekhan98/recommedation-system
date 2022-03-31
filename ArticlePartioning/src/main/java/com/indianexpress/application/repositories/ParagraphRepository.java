package com.indianexpress.application.repositories;

import com.indianexpress.application.models.Paragraph;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParagraphRepository extends Neo4jRepository<Paragraph, Long> {
}
