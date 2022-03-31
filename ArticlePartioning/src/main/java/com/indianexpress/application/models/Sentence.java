package com.indianexpress.application.models;

import org.neo4j.ogm.annotation.*;

@NodeEntity("Sentence")
public class Sentence {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String sentence;

    @Relationship(type="FIRST_WORD",direction = Relationship.OUTGOING)
    private Word firstWord;

    @Relationship(type="LAST_WORD",direction = Relationship.OUTGOING)
    private Word lastWord;

    @Relationship(type="NEXT",direction = Relationship.INCOMING)
    private Sentence previousSentence;

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

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public Word getFirstWord() {
        return firstWord;
    }

    public void setFirstWord(Word firstWord) {
        this.firstWord = firstWord;
    }

    public Word getLastWord() {
        return lastWord;
    }

    public void setLastWord(Word lastWord) {
        this.lastWord = lastWord;
    }

    public Sentence getPreviousSentence() {
        return previousSentence;
    }

    public void setPreviousSentence(Sentence previousSentence) {
        this.previousSentence = previousSentence;
    }
}
