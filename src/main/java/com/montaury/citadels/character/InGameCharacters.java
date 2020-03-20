package com.montaury.citadels.character;
import io.vavr.collection.List;


public class InGameCharacters {

    public InGameCharacters(List<Character> charactersInGame){
        this.charactersInGame = charactersInGame;
    }

    public List<Character> getInGameCharacters(){
        return charactersInGame;
    }

    public List<Character> inGameCharactersToRob(){
        List<Character> charactersToRob = charactersInGame;
        charactersToRob = charactersToRob.remove(Character.ASSASSIN);
        charactersToRob = charactersToRob.remove(Character.THIEF);
        return charactersToRob;
    }

    public List<Character> inGameCharactersToKill(){
        List<Character> charactersToKill = charactersInGame;
        charactersToKill = charactersToKill.remove(Character.ASSASSIN);
        return charactersToKill;
    }

    static private List<Character> charactersInGame;
}
