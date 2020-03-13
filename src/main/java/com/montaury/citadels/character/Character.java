package com.montaury.citadels.character;

import com.montaury.citadels.district.DistrictType;
import com.montaury.citadels.round.action.ActionType;
import io.vavr.control.Option;

import io.vavr.collection.List;

public enum Character {
    ASSASSIN(1, "Assassin", List.of(ActionType.KILL_ACTION)),
    THIEF(2, "Thief", List.of(ActionType.ROB_ACTION)),
    MAGICIAN(3, "Magician", List.of(ActionType.EXCHANGE_CARDS_WITH_OTHER_PLAYER, ActionType.EXCHANGE_CARDS_WITH_PILE)),
    KING(4, "King",  List.of(ActionType.RECEIVE_INCOME), DistrictType.NOBLE),
    BISHOP(5, "Bishop", List.of(ActionType.RECEIVE_INCOME), DistrictType.RELIGIOUS),
    MERCHANT(6, "Merchant", List.of(ActionType.RECEIVE_INCOME, ActionType.RECEIVE_1_COIN), DistrictType.TRADE),
    ARCHITECT(7, "Architect", List.of(ActionType.PICK_2_CARDS, ActionType.BUILD_DISTRICT, ActionType.BUILD_DISTRICT)),
    NAVIGATEUR(7,"Navigateur", List.of(ActionType.PICK_4_CARDS, ActionType.RECEIVE_4_COIN)),
    WARLORD(8, "Warlord", List.of(ActionType.RECEIVE_INCOME, ActionType.DESTROY_DISTRICT), DistrictType.MILITARY);


    Character(int number, String name, List<ActionType> powers)
    {
        this(number, name, powers, null);
    }

    Character(int number, String name,  List<ActionType> powers, DistrictType associatedDistrictType)
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

    public List<ActionType> getPowers(){return powers;}

    private final int number;
    private final String name;
    private final Option<DistrictType> associatedDistrictType;
    private final List<ActionType> powers;

}