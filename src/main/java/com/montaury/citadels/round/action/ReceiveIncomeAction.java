package com.montaury.citadels.round.action;

import com.montaury.citadels.CardPile;
import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.District;
import com.montaury.citadels.district.DistrictType;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import com.montaury.citadels.character.InGameCharacters;

public class ReceiveIncomeAction implements Action{
    @Override
    public void executeAction(Group group, CardPile pioche, GameRoundAssociations groups, InGameCharacters charactersOfTheGame)
    {
        DistrictType type = null;
        if (group.character == Character.BISHOP) {
            type = DistrictType.RELIGIOUS;
        }
        if (group.character == Character.WARLORD) {
            type = DistrictType.MILITARY;
        }
        if (group.character == Character.KING) {
            type = DistrictType.NOBLE;
        }
        if (group.character == Character.MERCHANT) {
            type = DistrictType.TRADE;
        }
        if (type != null) {
            for (District d : group.getPlayer().getCity().getDistricts()) {
                if (d.getDistrictType() == type) {
                    group.getPlayer().addGold(1);
                }
                if (d == District.MAGIC_SCHOOL) {
                    group.getPlayer().addGold(1);
                }
            }
        }
    }

    @Override
    public boolean canBeExecuted(Group group, CardPile pioche, GameRoundAssociations groups)
    {return true;}
}
