package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;

public class DestroyGetDistrictAction implements Action {
    @Override
    public void executeAction(Group group, CardPile pioche, GameRoundAssociations groups) {

    }

    @Override
    public boolean canBeExecuted(Group group, CardPile pioche,GameRoundAssociations groups)
    {
        return (true);
    }
}
