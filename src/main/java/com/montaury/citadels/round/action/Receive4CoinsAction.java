package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;

public class Receive4CoinsAction implements Action{
    @Override
    public void executeAction(Group group, CardPile pioche, GameRoundAssociations groups)
    {
        group.getPlayer().addGold(4);
    }

    @Override
    public boolean canBeExecuted(Group group, CardPile pioche, GameRoundAssociations groups){
        return true;
    }

    @Override
    public boolean canEndRound(Group group, CardPile pioche, GameRoundAssociations groups){
            return true;
    }
}