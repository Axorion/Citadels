package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import com.montaury.citadels.character.InGameCharacters;

public class ExchangeGetCardsWithOtherGetPlayerAction implements Action{
    @Override
    public void executeAction(Group group, CardPile pioche, GameRoundAssociations groups, InGameCharacters charactersOfTheGame)
    {
        Player playerToSwapWith = group.getPlayer().controller.selectPlayerAmong(groups.associations.map(Group::getPlayer).remove(group.getPlayer()));
        group.getPlayer().exchangeHandWith(playerToSwapWith);
    }

    @Override
    public boolean canBeExecuted(Group group, CardPile pioche, GameRoundAssociations groups)
    {return true;}
}
