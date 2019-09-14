package com.mithunkhatri.quoter.controllers;

import com.mithunkhatri.quoter.models.Quote;
import com.mithunkhatri.quoter.repositories.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/v1/quotes")
public class QuoteController {

    @Autowired
    private QuoteRepository repository;

    @GetMapping("/random")
    public Quote getRandomQuote() {
        Random random = new Random();
        return repository.getOne(random.nextInt(48000));
    }
}
