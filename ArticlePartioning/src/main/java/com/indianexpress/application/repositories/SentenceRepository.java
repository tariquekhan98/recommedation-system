package com.indianexpress.application.repositories;

import com.indianexpress.application.models.Sentence;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentenceRepository extends Neo4jRepository<Sentence, Long> {
}
