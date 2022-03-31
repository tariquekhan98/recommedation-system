package com.indianexpress.application.models;

import org.neo4j.ogm.annotation.*;

@NodeEntity("Article")
public class Article {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String text;

    @Relationship(type="FIRST_PARAGRAPH",direction = Relationship.OUTGOING)
    private Paragraph firstParagraphs;

    @Relationship(type="LAST_PARAGRAPH",direction = Relationship.OUTGOING)
    private Paragraph lastParagraphs;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Paragraph getFirstParagraphs() {
        return firstParagraphs;
    }

    public void setFirstParagraphs(Paragraph firstParagraphs) {
        this.firstParagraphs = firstParagraphs;
    }

    public Paragraph getLastParagraphs() {
        return lastParagraphs;
    }

    public void setLastParagraphs(Paragraph lastParagraphs) {
        this.lastParagraphs = lastParagraphs;
    }
}
