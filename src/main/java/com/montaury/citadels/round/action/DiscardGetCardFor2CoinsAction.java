package com.montaury.citadels.round.action;

import com.montaury.citadels.district.Card;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import com.montaury.citadels.CardPile;
import com.montaury.citadels.character.InGameCharacters;

public class DiscardGetCardFor2CoinsAction implements Action{
    @Override
    public void executeAction(Group group, CardPile pioche, GameRoundAssociations groups, InGameCharacters charactersOfTheGame) {
        Player player = group.getPlayer();
        Card card = player.controller.selectAmong(player.getCards());
        player.cards = player.getCards().remove(card);
        pioche.discard(card);
        player.addGold(2);
    }

    @Override
    public boolean canBeExecuted(Group group, CardPile pioche,GameRoundAssociations groups)
    {
        return (!group.getPlayer().getCards().isEmpty());
    }
}