package net.infojobs.marvel;

import com.arnaudpiroelle.marvel.api.objects.Character;

public class SimpleCharacter {

    static SimpleCharacter[] CUSTOMS = {roger(), roc(), rafactor(), loud()};

    private String name;
    private String description;
    private String photo;

    private SimpleCharacter(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.photo = builder.photo;
    }

    public SimpleCharacter() {
    }

    public static SimpleCharacter fromCharacter(Character character) {
        SimpleCharacter simpleCharacter = new SimpleCharacter();
        simpleCharacter.setName(character.getName());
        simpleCharacter.setDescription(character.getDescription());
        simpleCharacter.setPhoto(character.getThumbnail().getPath() + "." + character.getThumbnail().getExtension());
        return simpleCharacter;
    }

    public static Builder builder() {
        return new Builder();
    }

    static SimpleCharacter roger() {
        return builder()
          .name("Super Roger")
          .description("Lorem ipsum")
          .photo("https://dl.dropboxusercontent.com/u/1587994/marvel-ij/super_roger.jpg")
          .build();
    }

    static SimpleCharacter roc() {
        return builder()
          .name("Rock Man")
          .description("Lorem ipsum")
          .photo("https://dl.dropboxusercontent.com/u/1587994/marvel-ij/rock_man.jpg")
          .build();
    }

    static SimpleCharacter rafactor() {
        return builder()
          .name("Dr. Rafactor")
          .description("Lorem ipsum")
          .photo("https://dl.dropboxusercontent.com/u/1587994/marvel-ij/dr_rafactor.jpg")
          .build();
    }

    static SimpleCharacter loud() {
        return builder()
          .name("Loud Ceo")
          .description("Lorem ipsum")
          .photo("https://dl.dropboxusercontent.com/u/1587994/marvel-ij/loud_ceo.jpg")
          .build();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "SimpleCharacter{" +
          "name='" + name + '\'' +
          ", description='" + description + '\'' +
          ", photo='" + photo + '\'' +
          '}';
    }

    public static final class Builder {
        private String name;
        private String description;
        private String photo;

        private Builder() {
        }

        public SimpleCharacter build() {
            return new SimpleCharacter(this);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder photo(String photo) {
            this.photo = photo;
            return this;
        }
    }
}
