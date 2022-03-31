package com.indianexpress.application.repositories;

import com.indianexpress.application.models.Word;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends Neo4jRepository<Word, Long> {
}
