package com.montaury.citadels;

import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.DestructibleDistrict;
import com.montaury.citadels.district.District;
import com.montaury.citadels.district.DistrictType;
import com.montaury.citadels.player.Player;
import io.vavr.collection.List;

import static com.montaury.citadels.district.District.GREAT_WALL;
import static com.montaury.citadels.district.District.HAUNTED_CITY;

public class City {
    private static final int END_GAME_DISTRICT_NUMBER = 7;
    private final Board board;
    private List<Card> districtCards = List.empty();

    public List<Card> getDistrictCards(){
        return districtCards;
    }

    public City(Board board) {
        this.board = board;
    }

    public void buildDistrict(Card card) {
        districtCards = districtCards.append(card);
        if (isComplete()) {
            board.mark(this);
        }
    }
	
	public void buildDistricts(List<Card> cards) {
        for (Card card : cards) {
            districtCards = districtCards.append(card);
        }
        if (isComplete()) {
            board.mark(this);
        }
    }

    public boolean isComplete() {
        return districtCards.size() >= END_GAME_DISTRICT_NUMBER;
    }

    public int calculScore(Possession possession) {
        int score = calculCoutDistricts()
                  + calculScoreBonus(possession);

        return score;
    }

    public int calculCoutDistricts(){
        int cout = 0;
        for (int district = 0; district < getDistricts().size(); district++) {
            cout += getDistricts().get(district).getCost();
        }
        return cout;
    }

    public int calculScoreBonus(Possession possession){
        int scoreBonus = 0;
        scoreBonus += districtsScoreBonus(possession);
        if (winsAllColorBonus()) {
            scoreBonus += 3;
        }
        if (board.isFirst(this)) {
            scoreBonus += 2;
        }
        if (isComplete()) {
            scoreBonus += 2;
        }
        return scoreBonus;

    }

    private int districtsScoreBonus(Possession possession) {
        int score = 0;
        for (District d : getDistricts()) {
            if (d == District.DRAGON_GATE) {
                score += 2;
            }
            if (d == District.UNIVERSITY) {
                score += 2;
            }
            if (d == District.TREASURY) {
                score += possession.gold;
            }
            if (d == District.MAP_ROOM) {
                score += possession.cardsInHandCount();
            }
        }
        return score;
    }

    private boolean winsAllColorBonus() {
        int districtTypes[] = new int[DistrictType.values().length];
        for (District d : getDistricts()) {
            districtTypes[d.getDistrictType().ordinal()]++;
        }
        if (districtTypes[DistrictType.MILITARY.ordinal()] > 0 && districtTypes[DistrictType.NOBLE.ordinal()] > 0 && districtTypes[DistrictType.RELIGIOUS.ordinal()] > 0 && districtTypes[DistrictType.SPECIAL.ordinal()] > 0 && districtTypes[DistrictType.TRADE.ordinal()] > 0)
            return true;

        if (has(HAUNTED_CITY)) {
            int zeros = 0;
            for (int i = 0; i < districtTypes.length; i++) {
                if (districtTypes[i] == 0) {
                    zeros++;
                }
            }
            if (zeros == 1 && districtTypes[DistrictType.SPECIAL.ordinal()] > 1) {
                return true;
            }
            else return false;
        } else return false;
    }

    public boolean has(District district) {
        return getDistricts().contains(district);
    }

    public void destroyDistrict(Card card) {
        districtCards = districtCards.remove(card);
    }

    public List<DestructibleDistrict> districtsDestructibleBy(Player player) {
        return isComplete() ?
                List.empty() :
                districtCards
                        .filter(card -> card.getDistrict().isDestructible())
                        .filter(card -> player.canAfford(destructionCost(card)))
                        .map(card -> new DestructibleDistrict(card, destructionCost(card)));
    }

    private int destructionCost(Card card) {
        return card.getDistrict().getCost() - (has(GREAT_WALL) && card.getDistrict() != GREAT_WALL ? 0 : (1));
    }

    public List<District> getDistricts() {
        return districtCards.map(Card::getDistrict);
    }
}
