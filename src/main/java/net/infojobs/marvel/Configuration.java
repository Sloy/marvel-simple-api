package net.infojobs.marvel;

import com.arnaudpiroelle.marvel.api.MarvelApi;
import com.arnaudpiroelle.marvel.api.services.sync.CharactersService;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Value("${PRIVATE_KEY}")
    private String privateKey;

    @Value("${PUBLIC_KEY}")
    private String publicKey;

    @Bean(autowire = Autowire.BY_TYPE)
    public CharactersService charactersService() {
        MarvelApi.configure()
          .withApiKeys(publicKey, privateKey)
          .init();

        return MarvelApi.getService(CharactersService.class);
    }
}
