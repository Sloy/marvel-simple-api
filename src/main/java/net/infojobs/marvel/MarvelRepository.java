package net.infojobs.marvel;

import com.arnaudpiroelle.marvel.api.exceptions.AuthorizationException;
import com.arnaudpiroelle.marvel.api.exceptions.QueryException;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;

public interface MarvelRepository {
    List<SimpleCharacter> characters() throws IOException, QueryException, AuthorizationException;

    SimpleCharacter character(@PathVariable("name") String name) throws QueryException, AuthorizationException;
}
