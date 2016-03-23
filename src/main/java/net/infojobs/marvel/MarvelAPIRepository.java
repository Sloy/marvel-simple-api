package net.infojobs.marvel;

import com.arnaudpiroelle.marvel.api.exceptions.AuthorizationException;
import com.arnaudpiroelle.marvel.api.exceptions.QueryException;
import com.arnaudpiroelle.marvel.api.objects.Character;
import com.arnaudpiroelle.marvel.api.params.name.character.ListCharacterParamName;
import com.arnaudpiroelle.marvel.api.services.sync.CharactersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static net.infojobs.marvel.StreamsUtils.concat;
import static net.infojobs.marvel.StreamsUtils.singleItem;

@Repository
@Qualifier("api")
public class MarvelAPIRepository implements MarvelRepository {

    public static final int RESULT_LIMIT = 50;
    public static final String RESULT_ORDER = "-modified";
    @Autowired
    private CharactersService charactersService;

    private Random random = new Random();

    @Override
    public List<SimpleCharacter> characters() throws QueryException, AuthorizationException {
        return requestCharactersPage(0)
          .filter(character -> !character.getDescription().isEmpty())
          .map(SimpleCharacter::fromCharacter)
          .collect(concat(Stream.of(SimpleCharacter.CUSTOMS)))
          .sorted((o1, o2) -> randomOrder())
          .collect(toList());
    }

    private Stream<Character> requestCharactersPage(int page) throws AuthorizationException, QueryException {
        Map<ListCharacterParamName, String> options = new HashMap<>();
        options.put(ListCharacterParamName.ORDER_BY, RESULT_ORDER);
        options.put(ListCharacterParamName.LIMIT, String.valueOf(RESULT_LIMIT));
        options.put(ListCharacterParamName.OFFSET, String.valueOf(page * RESULT_LIMIT));
        return charactersService.listCharacter(options).getData().getResults().parallelStream();
    }

    @Override
    public SimpleCharacter character(@PathVariable("name") String name) throws QueryException, AuthorizationException {
        Optional<SimpleCharacter> custom = findCustom(name);
        if (custom.isPresent()) {
            return custom.get();
        }

        Map<ListCharacterParamName, String> options = new HashMap<>();
        options.put(ListCharacterParamName.NAME, name);
        List<Character> results = charactersService.listCharacter(options).getData().getResults();

        return results.stream()
          .map(SimpleCharacter::fromCharacter)
          .limit(1)
          .collect(singleItem());
    }

    private Optional<SimpleCharacter> findCustom(@PathVariable("name") String name) {
        try {
            SimpleCharacter customCharacter = Stream.of(SimpleCharacter.CUSTOMS)
              .filter(simpleCharacter -> simpleCharacter.getName().equals(name))
              .collect(singleItem());
            return Optional.of(customCharacter);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private int randomOrder() {
        return random.nextInt(2) - 1;
    }
}