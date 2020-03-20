package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.round.GameRoundAssociations;
import io.vavr.collection.Set;
import com.montaury.citadels.round.Group;
import com.montaury.citadels.character.InGameCharacters;

public class ExchangeGetCardsWithPileAction implements Action{
    @Override
    public void executeAction(Group group, CardPile pioche, GameRoundAssociations groups, InGameCharacters charactersOfTheGame)
    {
        Set<Card> cardsToSwap = group.getPlayer().controller.selectManyAmong(group.getPlayer().getCards());
        group.getPlayer().cards = group.getPlayer().getCards().removeAll(cardsToSwap);
        group.getPlayer().addCards(pioche.swapWith(cardsToSwap.toList()));
    }

    @Override
    public boolean canBeExecuted(Group group, CardPile pioche, GameRoundAssociations groups)
    {
        return(!group.getPlayer().getCards().isEmpty() && pioche.canDraw(1));
    }
}
