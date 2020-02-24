package nl.tudelft.oopp.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import nl.tudelft.oopp.demo.entities.Quote;
import nl.tudelft.oopp.demo.repositories.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QuoteController {

    @Autowired
    private QuoteRepository repository;

    /**
     * GET Endpoint to retrieve a random quote.
     *
     * @return randomly selected {@link Quote}.
     */
    @GetMapping("quote")
    @ResponseBody
    public Quote getRandomQuote() {
        Quote q1 = new Quote(
                1,
                "A clever person solves a problem. A wise person avoids it.",
                "Albert Einstein"
        );

        Quote q2 = new Quote(
                2,
                "The computer was born to solve problems that did not exist before.",
                "Bill Gates"
        );

        Quote q3 = new Quote(
                3,
                "Tell me and I forget.  Teach me and I remember.  Involve me and I learn.",
                "Benjamin Franklin"
        );

        List<Quote> quotes = repository.findAll();

        quotes.add(q1);
        quotes.add(q2);
        quotes.add(q3);
        System.out.println(quotes.size());
        repository.save(q1);
        return quotes.get(new Random().nextInt(quotes.size()));
    }

}
