package net.infojobs.marvel;

import com.arnaudpiroelle.marvel.api.objects.Character;

public class SimpleCharacter {

    private String name;
    private String description;
    private String photo;

    public static SimpleCharacter fromCharacter(Character character) {
        SimpleCharacter simpleCharacter = new SimpleCharacter();
        simpleCharacter.setName(character.getName());
        simpleCharacter.setDescription(character.getDescription());
        simpleCharacter.setPhoto(character.getThumbnail().getPath() + "." + character.getThumbnail().getExtension());
        return simpleCharacter;
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
}
