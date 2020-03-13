package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;

public class Draw3GetCardsFor2CoinsAction implements Action{
    @Override
    public void executeAction(Group group, CardPile pioche, GameRoundAssociations groups)
    {
        group.getPlayer().addCards(pioche.draw(3));
        group.getPlayer().pay(2);
    }

    @Override
    public boolean canBeExecuted(Group group, CardPile pioche, GameRoundAssociations groups)
    {
        return(pioche.canDraw(3) && group.getPlayer().canAfford(2));
    }
}
