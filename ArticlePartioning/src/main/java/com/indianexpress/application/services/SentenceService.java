package com.indianexpress.application.services;

import com.indianexpress.application.models.Sentence;
import com.indianexpress.application.models.Word;
import com.indianexpress.application.repositories.SentenceRepository;
import com.indianexpress.application.shared.exceptions.NotFoundException;
import com.indianexpress.application.shared.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SentenceService {
    @Autowired
    SentenceRepository sentenceRepository;

    @Autowired
    WordService wordService;

    private Sentence getInstance(Object id) throws NotFoundException, ValidationException {
        if (id == null) return null;
        long senId;
        try {
            senId= Long.parseLong(String.valueOf(id));
        } catch (Exception ex) {
            throw new ValidationException("Invalid id of Sentence");
        }
        Optional<Sentence> optionalContent = sentenceRepository.findById(senId);
        if (!optionalContent.isPresent()) return null;
        return optionalContent.get();
    }

    private String validateSentence(Sentence instance, Object sentenceObject) throws ValidationException {
        if(instance!=null && sentenceObject==null)
            return instance.getSentence();

        if (instance == null && sentenceObject==null){
            throw new ValidationException("Sentence is required");
        }
        String paragraph=String.valueOf(sentenceObject);
        return paragraph;
    }

    public Map<String, Object> validate(Sentence instance, Map<?, ?> requestBody) throws ValidationException {
        Map<String, Object> validatedData = new HashMap<String, Object>();
        validatedData.put("title", requestBody.get("title"));
        validatedData.put("sentence", validateSentence(instance, requestBody.get("sentence")));
        return validatedData;
    }

    private Sentence getInstanceInstantiatedWithValidatedData(Sentence sentence,
                                                               Map<String, Object> validatedData) {
        if(sentence==null){
            sentence=new Sentence();
        }
        sentence.setTitle((String) validatedData.get("title"));
        sentence.setSentence((String)validatedData.get("sentence"));
        return sentence;
    }

    public Sentence save(HashMap<?,?> requestBody, Object id, Sentence previousSentence) throws ValidationException, NotFoundException {
        Sentence instance=getInstance(id);
        Map<String,Object> validatedData= validate(instance,requestBody);
        Sentence sentence = getInstanceInstantiatedWithValidatedData(instance,validatedData);
        String sentenceStr = sentence.getSentence().replaceAll("[$&+,:;=?@#|'<>.^*()%!-]", "");
        String[] words = sentenceStr.split("[\\s]");
        Word firstWord=null, lastWord=null;
        HashMap<String, String> hm;
        Word previousWord=null;
        int wordCount=1, size=words.length;
        for(String str:words){
            hm=new HashMap<>();
            hm.put("title", String.format("Word%s",wordCount));
            hm.put("word", str.trim());
            previousWord = wordService.save(hm, null, previousWord);
            if(wordCount==1){
                firstWord=previousWord;
            }else if(wordCount==size){
               lastWord=previousWord;
            }
            wordCount+=1;
        }
        sentence.setFirstWord(firstWord);
        sentence.setLastWord(lastWord);
        sentence.setPreviousSentence(previousSentence);
        sentence = sentenceRepository.save(sentence);
        return sentence;
    }
}

