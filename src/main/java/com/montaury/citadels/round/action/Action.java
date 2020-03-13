package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;

public interface Action {

    public void executeAction(Group group, CardPile pioche,GameRoundAssociations groups);

    public boolean canBeExecuted(Group group, CardPile pioche,GameRoundAssociations groups);

    public boolean canEndRound(Group group, CardPile pioche,GameRoundAssociations groups){
        return false;
    }
}