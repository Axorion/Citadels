package com.montaury.citadels.player;

import com.montaury.citadels.City;
import com.montaury.citadels.Possession;
import com.montaury.citadels.district.Card;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

public class Player {
    private final String name;
    private final int age;
    private final City city;
    public final PlayerController controller;
    private int gold;
    public Set<Card> cards = HashSet.empty();

    public Player(String name, int age, City city, PlayerController controller) {
        this.name = name;
        this.age = age;
        this.city = city;
        this.gold = 0;
        this.controller = controller;
    }

    public String name() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public City getCity() {
        return city;
    }

    public void addGold(int goldCoins) {
        gold+= (goldCoins);
    }

    public void addCards(Set<Card> cards) {
        this.cards = this.cards.addAll(cards);
    }

    public void addCardInHand(Card card) {
        cards = cards.add(card);
    }

    public int getGold() {
        return gold;
    }

    public boolean canAfford(int cost) {
        return cost <= gold;
    }

    private boolean canBuildDistrict(Card card) {
        return gold >=(card.getDistrict().getCost()) && !city.has(card.getDistrict());
    }

    public Set<Card> buildableDistrictsInHand() {
        return cards.filter(this::canBuildDistrict);
    }

    public void buildDistrict(Card card) {
        if (!canBuildDistrict(card)) {
            return;
        }
        cards = cards.remove(card);
        city.buildDistrict(card);
        gold -= (card.getDistrict().getCost());
    }

    public int score() {
        return city.calculScore(new Possession(gold, cards));
    }

    public void exchangeHandWith(Player otherPlayer) {
        Set<Card> swappingHand = cards;
        cards = otherPlayer.cards;
        otherPlayer.cards = swappingHand;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void steal(Player otherPlayer) {
        gold += otherPlayer.gold;
        otherPlayer.gold = 0;
    }

    public void pay(int cost) {
        gold -= ((cost));
    }

}
