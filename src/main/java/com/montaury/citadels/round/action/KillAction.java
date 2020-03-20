package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import io.vavr.collection.List;
import com.montaury.citadels.round.Group;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.character.InGameCharacters;

public class KillAction implements Action{
    @Override
    public void executeAction(Group group, CardPile pioche, GameRoundAssociations groups, InGameCharacters charactersOfTheGame)
    {
        Character characterToMurder = group.getPlayer().controller.selectAmong(charactersOfTheGame.inGameCharactersToKill());
        groups.associationToCharacter(characterToMurder).peek(Group::murder);
    }
//inGameCharacterToKill()
    @Override
    public boolean canBeExecuted(Group group, CardPile pioche, GameRoundAssociations groups)
    {return true;}
}
