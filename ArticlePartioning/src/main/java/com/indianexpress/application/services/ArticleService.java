package com.indianexpress.application.services;

import com.indianexpress.application.models.Article;
import com.indianexpress.application.models.Paragraph;
import com.indianexpress.application.repositories.ArticleRepository;
import com.indianexpress.application.shared.exceptions.NotFoundException;
import com.indianexpress.application.shared.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ParagraphService paragraphService;

    private static final String PARAGRAPH_SPLIT_REGEX = "(..)";

    private Article getInstance(Object id) throws ValidationException {
        if (id == null) return null;
        long articleId;
        try {
            articleId = Long.parseLong(String.valueOf(id));
        } catch (Exception ex) {
            throw new ValidationException("Invalid id of Article");
        }
        Optional<Article> optionalContent = articleRepository.findById(articleId);
        if (!optionalContent.isPresent()) return null;
        return optionalContent.get();
    }

    private String validateTitle(Article instance, Object titleObject) throws ValidationException {
        if(instance!=null && titleObject==null)
            return instance.getTitle();

        if (instance == null && titleObject==null){
            throw new ValidationException("title is required");
        }
        String title=String.valueOf(titleObject);
        return title;
    }

    private String validateText(Article instance, Object textObject) throws ValidationException {
        if(instance!=null && textObject==null)
            return instance.getTitle();

        if (instance == null && textObject==null){
            throw new ValidationException("text is required");
        }

        String text=String.valueOf(textObject);
        return text;
    }

    public Map<String, Object> validate(Article instance, Map<?, ?> requestBody) throws ValidationException {
        Map<String, Object> validatedData = new HashMap<String, Object>();
        validatedData.put("title", validateTitle(instance, requestBody.get("title")));
        validatedData.put("text",validateText(instance,requestBody.get("text")));
        return validatedData;
    }

    private Article getInstanceInstantiatedWithValidatedData(Article article,
                                                             Map<String, Object> validatedData) {
        if(article==null){
                article=new Article();
        }

        article.setTitle((String)validatedData.get("title"));
        article.setText((String) validatedData.get("text"));
        return article;
    }

    public Article save(HashMap<?,?> requestBody, Object id) throws ValidationException, NotFoundException {
        Article instance=getInstance(id);
        Map<String,Object> validatedData= validate(instance,requestBody);
        Article article = getInstanceInstantiatedWithValidatedData(instance,validatedData);
        String[] paragraphs = article.getText().split("\n");
        Paragraph firstParagraph=null, lastParagraph=null;
        HashMap<String, String> hm;
        Paragraph previousParagraph = null;
        int paragraphCount=1, size=paragraphs.length;
        for(String str:paragraphs){
            hm=new HashMap<>();
            hm.put("title", String.format("Para%s",paragraphCount));
            hm.put("paragraph", str.trim());
            previousParagraph = paragraphService.save(hm, null, previousParagraph);
            if(paragraphCount==1)
                firstParagraph=previousParagraph;
            else if(paragraphCount==size)
                lastParagraph=previousParagraph;
            paragraphCount+=1;
        }
        article.setFirstParagraphs(firstParagraph);
        article.setLastParagraphs(lastParagraph);
        article=articleRepository.save(article);
        return article;
    }
}
