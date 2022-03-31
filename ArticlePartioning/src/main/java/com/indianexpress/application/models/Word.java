package com.indianexpress.application.models;

import org.neo4j.ogm.annotation.*;

@NodeEntity("Word")
public class Word {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String word;

    @Relationship(type="NEXT",direction = Relationship.INCOMING)
    private Word previousWord;

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

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Word getPreviousWord() {
        return previousWord;
    }

    public void setPreviousWord(Word previousWord) {
        this.previousWord = previousWord;
    }
}
