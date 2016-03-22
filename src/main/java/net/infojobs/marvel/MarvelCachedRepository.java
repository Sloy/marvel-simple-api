package net.infojobs.marvel;

import com.arnaudpiroelle.marvel.api.exceptions.AuthorizationException;
import com.arnaudpiroelle.marvel.api.exceptions.QueryException;
import com.fewlaps.quitnowcache.QNCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;

@Repository
@Qualifier("cache")
public class MarvelCachedRepository implements MarvelRepository {

    @Autowired
    private QNCache cache;

    @Autowired
    @Qualifier("api")
    private MarvelRepository wrappedRepository;

    @Override
    public List<SimpleCharacter> characters() throws IOException, QueryException, AuthorizationException {
        List<SimpleCharacter> cached = cache.get("ALL");
        if (cached == null) {
            cached = wrappedRepository.characters();
            cache.set("ALL", cached, 30 * 1000);
        }
        return cached;
    }

    @Override
    public SimpleCharacter character(@PathVariable("name") String name) throws QueryException, AuthorizationException {
        SimpleCharacter cached = cache.get(name);
        if (cached == null) {
            cached = wrappedRepository.character(name);
            cache.set(name, cached, 30 * 1000);
        }
        return cached;
    }
}
