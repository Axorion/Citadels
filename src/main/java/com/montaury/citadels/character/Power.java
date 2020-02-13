package com.montaury.citadels.character;

import com.montaury.citadels.round.Group;
import io.vavr.collection.List;

public enum Power {
    KILL("Kill"),
    ROB("Rob"),
    EXCHANGE_OHTER_PLAYER("Exchange cards with other player"),
    EXCHANGE_PILE("Exchange cards with pile"),
    RECEIVE_INCOME("Receive income"),
    RECEIVE_GOLD("Receive 1 gold"),
    PICK_CARDS("Pick 2 cards"),
    BUILD_DISTRICT_1("Build district"),
    BUILD_DISTRICT_2("Build district"),
    DESTROY_DISTRICT("Destroy district");

    Power(String namePower) {
        this.namePower = namePower;
    }

    private String namePower;

    public String getNamePower() {
        return this.namePower;
    }



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
}
