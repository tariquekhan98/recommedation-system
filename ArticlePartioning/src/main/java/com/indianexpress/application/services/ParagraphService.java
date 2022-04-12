package com.indianexpress.application.services;

import com.indianexpress.application.models.Article;
import com.indianexpress.application.models.Paragraph;
import com.indianexpress.application.models.Sentence;
import com.indianexpress.application.repositories.ParagraphRepository;
import com.indianexpress.application.shared.exceptions.NotFoundException;
import com.indianexpress.application.shared.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class ParagraphService {
    @Autowired
    ParagraphRepository paragraphRepository;

    @Autowired
    SentenceService sentenceService;

    private Paragraph getInstance(Object id) throws NotFoundException, ValidationException {
        if (id == null) return null;
        long paraId;
        try {
            paraId = Long.parseLong(String.valueOf(id));
        } catch (Exception ex) {
            throw new ValidationException("Invalid id of Paragraph");
        }
        Optional<Paragraph> optionalContent = paragraphRepository.findById(paraId);
        if (!optionalContent.isPresent()) return null;
        return optionalContent.get();
    }

    private String validateParagraph(Paragraph instance, Object paragraphObject) throws ValidationException {
        if(instance!=null && paragraphObject==null)
            return instance.getParagraph();

        if (instance == null && paragraphObject==null){
            throw new ValidationException("Paragraph is required");
        }
        String paragraph=String.valueOf(paragraphObject);
        return paragraph;
    }

    public Map<String, Object> validate(Paragraph instance, Map<?, ?> requestBody) throws ValidationException {
        Map<String, Object> validatedData = new HashMap<String, Object>();
        validatedData.put("title", requestBody.get("title"));
        validatedData.put("paragraph", validateParagraph(instance, requestBody.get("paragraph")));
        return validatedData;
    }

    private Paragraph getInstanceInstantiatedWithValidatedData(Paragraph paragraph,
                                                             Map<String, Object> validatedData) {
        if(paragraph==null){
            paragraph=new Paragraph();
        }

        paragraph.setTitle((String) validatedData.get("title"));
        paragraph.setParagraph((String)validatedData.get("paragraph"));
        return paragraph;
    }

    public Paragraph save(HashMap<?,?> requestBody, Object id, Paragraph previousParagraph) throws ValidationException, NotFoundException, IOException {
        Paragraph instance=getInstance(id);
        Map<String,Object> validatedData= validate(instance,requestBody);
        Paragraph paragraph = getInstanceInstantiatedWithValidatedData(instance,validatedData);
        String[] sentences = paragraph.getParagraph().split("[.?]");
        Sentence firstSentence= null, lastSentence=null;
        HashMap<String, String> hm;
        Sentence previousSentence=null;
        int sentenceCount=1, size=sentences.length;
        for(String str:sentences){
            hm=new HashMap<>();
            hm.put("title", String.format("Sen%s",sentenceCount));
            hm.put("sentence", str.trim());
            previousSentence = sentenceService.save(hm, null, previousSentence);
            if(sentenceCount==1)
                firstSentence=previousSentence;
            else if (sentenceCount==size)
                lastSentence=previousSentence;
            sentenceCount+=1;
        }
        paragraph.setFirstSentences(firstSentence);
        paragraph.setLastSentences(lastSentence);
        paragraph.setPreviousParagraph(previousParagraph);
        paragraph = paragraphRepository.save(paragraph);
        return paragraph;
    }

}
