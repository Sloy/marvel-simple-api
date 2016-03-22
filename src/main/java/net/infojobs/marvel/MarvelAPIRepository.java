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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Repository
@Qualifier("api")
public class MarvelAPIRepository implements MarvelRepository {

    @Autowired
    private CharactersService charactersService;

    private Random random = new Random();

    @Override
    public List<SimpleCharacter> characters() throws IOException, QueryException, AuthorizationException {
        Map<ListCharacterParamName, String> options = new HashMap<>();
        options.put(ListCharacterParamName.ORDER_BY, "-modified");
        options.put(ListCharacterParamName.LIMIT, "100");
        List<Character> results = charactersService.listCharacter(options).getData().getResults();

        return results.stream()
          .filter(character -> !character.getDescription().isEmpty())
          .sorted((o1, o2) -> randomOrder())
          .map(SimpleCharacter::fromCharacter)
          .collect(toList());
    }

    @Override
    public SimpleCharacter character(@PathVariable("name") String name) throws QueryException, AuthorizationException {
        Map<ListCharacterParamName, String> options = new HashMap<>();
        options.put(ListCharacterParamName.NAME, name);
        List<Character> results = charactersService.listCharacter(options).getData().getResults();

        return results.stream()
          .map(SimpleCharacter::fromCharacter)
          .limit(1)
          .collect(Collectors.collectingAndThen(toList(), l -> {
              if (l.size() == 1) return l.get(0);
              throw new RuntimeException();
          }));
    }

    private int randomOrder() {
        return random.nextInt(2) - 1;
    }
}