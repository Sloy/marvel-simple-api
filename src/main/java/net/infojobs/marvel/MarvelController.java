package net.infojobs.marvel;

import com.arnaudpiroelle.marvel.api.exceptions.AuthorizationException;
import com.arnaudpiroelle.marvel.api.exceptions.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class MarvelController {

    @Autowired
    @Qualifier("cache")
    MarvelRepository marvelRepository;

    @RequestMapping("/characters")
    public List<SimpleCharacter> characters() throws IOException, QueryException, AuthorizationException {
        return marvelRepository.characters();
    }

    @RequestMapping("/characters/{name}")
    public SimpleCharacter character(@PathVariable("name") String name) throws QueryException, AuthorizationException {
        return marvelRepository.character(name);
    }
}