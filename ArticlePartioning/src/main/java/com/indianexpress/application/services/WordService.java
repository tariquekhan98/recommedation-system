package com.indianexpress.application.services;

import com.indianexpress.application.models.Sentence;
import com.indianexpress.application.models.Word;
import com.indianexpress.application.repositories.SentenceRepository;
import com.indianexpress.application.repositories.WordRepository;
import com.indianexpress.application.shared.exceptions.NotFoundException;
import com.indianexpress.application.shared.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WordService {
    @Autowired
    WordRepository wordRepository;

    private Word getInstance(Object id) throws NotFoundException, ValidationException {
        if (id == null) return null;
        long wordId;
        try {
            wordId= Long.parseLong(String.valueOf(id));
        } catch (Exception ex) {
            throw new ValidationException("Invalid id of Word");
        }
        Optional<Word> optionalContent = wordRepository.findById(wordId);
        if (!optionalContent.isPresent()) return null;
        return optionalContent.get();
    }

    private String validateWord(Word instance, Object wordObject) throws ValidationException {
        if(instance!=null && wordObject==null)
            return instance.getWord();

        if (instance == null && wordObject==null){
            throw new ValidationException("Word is required");
        }
        String word=String.valueOf(wordObject);
        return word;
    }

    public Map<String, Object> validate(Word instance, Map<?, ?> requestBody) throws ValidationException {
        Map<String, Object> validatedData = new HashMap<String, Object>();
        validatedData.put("title", requestBody.get("title"));
        validatedData.put("word", validateWord(instance, requestBody.get("word")));
        return validatedData;
    }

    private Word getInstanceInstantiatedWithValidatedData(Word word,
                                                              Map<String, Object> validatedData) {
        if(word==null){
            word=new Word();
        }

        word.setTitle((String) validatedData.get("title"));
        word.setWord((String)validatedData.get("word"));
        return word;
    }

    public Word save(HashMap<?,?> requestBody, Object id, Word previousWord) throws ValidationException, NotFoundException {
        Word instance=getInstance(id);
        Map<String,Object> validatedData= validate(instance,requestBody);
        Word word = getInstanceInstantiatedWithValidatedData(instance,validatedData);
        word.setPreviousWord(previousWord);
        word = wordRepository.save(word);
        return word;
    }
}
