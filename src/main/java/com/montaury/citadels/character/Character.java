package com.montaury.citadels.character;

import com.montaury.citadels.district.DistrictType;
import io.vavr.control.Option;

import io.vavr.collection.List;

public enum Character {
    ASSASSIN(1, "Assassin", List.of("Kill")),
    THIEF(2, "Thief", List.of("Rob")),
    MAGICIAN(3, "Magician", List.of("Exchange getCards with ohter getPlayer", "Exchange getCards with pile")),
    KING(4, "King",  List.of("Receive income"), DistrictType.NOBLE),
    BISHOP(5, "Bishop", List.of("Receive income"), DistrictType.RELIGIOUS),
    MERCHANT(6, "Merchant", List.of("Receive income", "Receive one getGold"), DistrictType.TRADE),
    ARCHITECT(7, "Architect", List.of("Pick 2 getCards", "build getDistrict", "Build getDistrict")),
    WARLORD(8, "Warlord", List.of("Receive income", "Destroy getDistrict"), DistrictType.MILITARY);

    Character(int number, String name, List<String> powers)
    {
        this(number, name, powers, null);
    }

    Character(int number, String name,  List<String> powers, DistrictType associatedDistrictType)
    {
        this.number = number;
        this.name = name;
        this.associatedDistrictType = Option.of(associatedDistrictType);
        this.powers = powers;
    }


    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Option<DistrictType> associatedDistrictType() {
        return associatedDistrictType;
    }

    public List<String> getPowers(){return powers;}

    private final int number;
    private final String name;
    private final Option<DistrictType> associatedDistrictType;
    private final List<String> powers;

}