package com.montaury.citadels.round.action;

package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;

public class Pick4GetCardsAction implements Action{
    @Override
    public void executeAction(Group group, CardPile pioche, GameRoundAssociations groups)
    {
        group.getPlayer().addCards(pioche.draw(2));
    }

    @Override
    public boolean canBeExecuted(Group group, CardPile pioche, GameRoundAssociations groups)
    {
        return(pioche.canDraw(2));
    }

    @Override
    public boolean canEndRound(Group group, CardPile pioche, GameRoundAssociations groups)
    {
        return true;
    }
}

