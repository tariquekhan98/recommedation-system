package com.indianexpress.application.models;

import org.neo4j.ogm.annotation.*;
import org.springframework.context.annotation.Primary;

import java.util.HashSet;
import java.util.Set;

@NodeEntity("Paragraph")
public class Paragraph {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String paragraph;

    @Relationship(type="FIRST_SENTENCE",direction = Relationship.OUTGOING)
    private Sentence firstSentences;

    @Relationship(type="LAST_SENTENCE",direction = Relationship.OUTGOING)
    private Sentence lastSentences;

    @Relationship(type="NEXT",direction = Relationship.INCOMING)
    private Paragraph previousParagraph;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public Sentence getFirstSentences() {
        return firstSentences;
    }

    public void setFirstSentences(Sentence firstSentences) {
        this.firstSentences = firstSentences;
    }

    public Sentence getLastSentences() {
        return lastSentences;
    }

    public void setLastSentences(Sentence lastSentences) {
        this.lastSentences = lastSentences;
    }

    public Paragraph getPreviousParagraph() {
        return previousParagraph;
    }

    public void setPreviousParagraph(Paragraph previousParagraph) {
        this.previousParagraph = previousParagraph;
    }
}
