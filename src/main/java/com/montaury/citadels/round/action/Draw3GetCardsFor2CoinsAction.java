package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import com.montaury.citadels.character.InGameCharacters;

public class Draw3GetCardsFor2CoinsAction implements Action{
    @Override
    public void executeAction(Group group, CardPile pioche, GameRoundAssociations groups, InGameCharacters charactersOfTheGame)
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
