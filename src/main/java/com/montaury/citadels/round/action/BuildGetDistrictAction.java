package com.montaury.citadels.round.action;

import com.montaury.citadels.district.Card;
import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;

public class BuildGetDistrictAction implements Action{
    @Override
    public void executeAction(Group group, CardPile pioche, GameRoundAssociations groups) {
        Card card = group.getPlayer().controller.selectAmong(group.getPlayer().buildableDistrictsInHand());
        group.getPlayer().buildDistrict(card);
    }

    @Override
    public boolean canBeExecuted(Group group, CardPile pioche,GameRoundAssociations groups)
    {
        return (!group.getPlayer().buildableDistrictsInHand().isEmpty());
    }
}