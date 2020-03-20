package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.character.InGameCharacters;
import com.montaury.citadels.round.GameRoundAssociations;

import com.montaury.citadels.round.Group;
import io.vavr.collection.List;

public class RobAction implements Action{
    @Override
    public void executeAction(Group group, CardPile pioche, GameRoundAssociations groups, InGameCharacters charactersOfTheGame)
    {
        Character character = group.getPlayer().controller.selectAmong(
                charactersOfTheGame.inGameCharactersToRob()
                .removeAll(groups.associations.find(Group::isMurdered).map(Group::getCharacter)));
        groups.associationToCharacter(character).peek(association -> association.stolenBy(group.getPlayer()));
    }

    @Override
    public boolean canBeExecuted(Group group, CardPile pioche, GameRoundAssociations groups)
    {return true;}
}
