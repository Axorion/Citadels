package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.District;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

public class Draw3GetCardsKeep1Action implements Action{
    @Override
    public void executeAction(Group group, CardPile pioche, GameRoundAssociations groups)
    {
        Set<Card> cardsDrawn = pioche.draw(3);
        if (!group.getPlayer().getCity().has(District.LIBRARY)) {
            Card keptCard = group.getPlayer().controller.selectAmong(cardsDrawn);
            pioche.discard(cardsDrawn.remove(keptCard).toList());
            cardsDrawn = HashSet.of(keptCard);
        }
        group.getPlayer().addCards(cardsDrawn);
    }

    @Override
    public boolean canBeExecuted(Group group, CardPile pioche, GameRoundAssociations groups)
    {return true;}
}

