package com.mithunkhatri.quoter.loaders;

import com.mithunkhatri.quoter.models.Quote;
import com.mithunkhatri.quoter.models.Tag;
import com.mithunkhatri.quoter.repositories.QuoteRepository;
import com.mithunkhatri.quoter.repositories.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class QuotesLoader implements CommandLineRunner {

    @Autowired
    private QuoteRepository repository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public void run(String... args) throws Exception {
        Thread.currentThread().setName("quotes-loader");
        log.info("Begin loading quotes");
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/resources/quotes.json")) {
            Object obj = jsonParser.parse(reader);
            JSONArray quotes = (JSONArray) obj;
            final Set<String> tagSet = new HashSet<>();
            quotes.forEach(rawQuote -> {
                JSONObject quoteObj = (JSONObject) rawQuote;
                Quote quote = new Quote();
                quote.setQuote(((String) quoteObj.get("Quote")).trim());
                quote.setAuthor(((String) quoteObj.get("Author")).trim());
                quote.setCategory(((String) quoteObj.get("Category")).trim());
                quote.setPopularity((Number) quoteObj.get("Popularity"));
                List<String> tagList = ((List<String>) quoteObj.get("Tags"))
                        .stream()
                        .map(tag -> tag.replace("-", " ").trim())
                        .collect(Collectors.toList());
                tagSet.addAll(tagList.stream().distinct().collect(Collectors.toList()));
                quote.setTags(tagList);
                try {
                    repository.save(quote);
                }catch (Exception e) {

                }
            });
            tagSet.forEach(tag -> {
                Tag tag1 = new Tag();
                tag1.setTagName(tag);
                tagRepository.save(tag1);
            });
            log.info(String.format("%s quotes have been loaded", quotes.size()));
            log.info(String.format("%s tags have been saved", tagSet.size()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
