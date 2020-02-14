package com.montaury.citadels.character;

import com.montaury.citadels.district.DistrictType;
import io.vavr.control.Option;

import io.vavr.collection.List;

public enum Character {
    ASSASSIN(1, "Assassin", List.of("Kill")),
    THIEF(2, "Thief", List.of("Rob")),
    MAGICIAN(3, "Magician", List.of("Exchange cards with ohter player", "Exchange cards with pile")),
    KING(4, "King",  List.of("Receive income"), DistrictType.NOBLE),
    BISHOP(5, "Bishop", List.of("Receive income"), DistrictType.RELIGIOUS),
    MERCHANT(6, "Merchant", List.of("Receive income", "Receive one gold"), DistrictType.TRADE),
    ARCHITECT(7, "Architect", List.of("Pick 2 cards", "build district", "Build district")),
    WARLORD(8, "Warlord", List.of("Receive income", "Destroy district"), DistrictType.MILITARY);

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


    public int number() {
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

    /*
    public static void receivePowers(Group group, List<String> listPower){

        if (group.character == Character.ASSASSIN) {
            listPower = List.of(Power.KILL.getNamePower());
        }
        else if (group.character == Character.THIEF) {
            listPower = List.of(Power.ROB.getNamePower());
        }
        else if (group.character == Character.MAGICIAN) {
            listPower = List.of(Power.EXCHANGE_OHTER_PLAYER.getNamePower(), Power.EXCHANGE_PILE.getNamePower());
        }
        else if (group.character == Character.KING) {
            listPower = List.of(Power.RECEIVE_INCOME.getNamePower());
        }
        else if (group.character == Character.BISHOP) {
            listPower = List.of(Power.RECEIVE_INCOME.getNamePower());
        }
        else if (group.character == Character.MERCHANT) {
            listPower = List.of(Power.RECEIVE_INCOME.getNamePower(), Power.RECEIVE_GOLD.getNamePower());
        }
        else if (group.character == Character.ARCHITECT) {
            listPower = List.of(Power.PICK_CARDS.getNamePower(), Power.BUILD_DISTRICT_1.getNamePower(), Power.BUILD_DISTRICT_2.getNamePower());
        }
        else if (group.character == Character.WARLORD) {
            listPower = List.of(Power.RECEIVE_INCOME.getNamePower(), Power.DESTROY_DISTRICT.getNamePower());
        }
        else {
            System.out.println("Uh oh");
        }

    }
     */
}