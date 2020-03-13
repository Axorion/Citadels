package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;

public class EndRoundAction implements Action{
    @Override
    public void executeAction(Group group, CardPile pioche, GameRoundAssociations groups) {
        System.out.println("Fin du round");
    }
    @Override
    public boolean canBeExecuted(Group group, CardPile pioche, GameRoundAssociations groups) {
        return(true);
    }
}
