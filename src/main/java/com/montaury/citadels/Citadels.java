package com.montaury.citadels;

import com.montaury.citadels.character.Character;

import com.montaury.citadels.character.RandomCharacterSelector;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.District;
import com.montaury.citadels.district.DistrictType;
import com.montaury.citadels.player.ComputerController;
import com.montaury.citadels.player.HumanController;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import com.montaury.citadels.round.action.DestroyDistrictAction;
import io.vavr.Tuple;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;

import java.util.Collections;
import java.util.Scanner;

public class Citadels {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! Quel est votre nom ? ");
        String playerName = scanner.next();
        System.out.println("Quel est votre getAge ? ");
        int playerAge = scanner.nextInt();
        Board board = new Board();
        Player p = new Player(playerName, playerAge, new City(board), new HumanController());
        List<Player> players = List.of(p);
        System.out.println("Saisir le nombre de joueurs total (entre 2 et 8): ");
        int nbP;
        do {
            nbP = scanner.nextInt();
        } while (nbP < 2 || nbP > 8);
        for (int joueurs = 0; joueurs < nbP; joueurs += 1) {
            Player player = new Player("Computer " + joueurs, 35, new City(board), new ComputerController());
            players = players.append(player);
        }
        CardPile pioche = new CardPile(Card.all().toList().shuffle());
        players.forEach(player -> {
            player.addGold(2);
            player.addCards(pioche.draw(2));
        });
        Player crown = players.maxBy(Player::getAge).get();

        List<Group> roundAssociations;
        do {
            java.util.List<Player> list = players.asJavaMutable();
            Collections.rotate(list, -players.indexOf(crown));
            List<Player> playersInOrder = List.ofAll(list);
            RandomCharacterSelector randomCharacterSelector = new RandomCharacterSelector();
            List<Character> availableCharacters = List.of(Character.ASSASSIN, Character.THIEF, Character.MAGICIAN, Character.KING, Character.BISHOP, Character.MERCHANT, Character.ARCHITECT, Character.WARLORD);

            List<Character> availableCharacters1 = availableCharacters;
            List<Character> discardedCharacters = List.empty();
            for (int i = 0; i < 1; i++) {
                Character discardedCharacter = randomCharacterSelector.among(availableCharacters1);
                discardedCharacters = discardedCharacters.append(discardedCharacter);
                availableCharacters1 = availableCharacters1.remove(discardedCharacter);
            }
            Character faceDownDiscardedCharacter = discardedCharacters.head();
            availableCharacters = availableCharacters.remove(faceDownDiscardedCharacter);

            List<Character> availableCharacters11 = availableCharacters.remove(Character.KING);
            List<Character> discardedCharacters1 = List.empty();
            for (int i = 0; i < 7 - playersInOrder.size() - 1; i++) {
                Character discardedCharacter = randomCharacterSelector.among(availableCharacters11);
                discardedCharacters1 = discardedCharacters1.append(discardedCharacter);
                availableCharacters11 = availableCharacters11.remove(discardedCharacter);
            }
            List<Character> faceUpDiscardedCharacters = discardedCharacters1;
            availableCharacters = availableCharacters.removeAll(faceUpDiscardedCharacters);

            List<Group> associations1 = List.empty();
            for (Player player : playersInOrder) {
                System.out.println(player.name() + " doit choisir un personnage");
                availableCharacters = availableCharacters.size() == 1 && playersInOrder.size() == 7 ? availableCharacters.append(faceDownDiscardedCharacter) : availableCharacters;
                Character selectedCharacter = player.controller.selectOwnCharacter(availableCharacters, faceUpDiscardedCharacters);
                availableCharacters = availableCharacters.remove(selectedCharacter);
                associations1 = associations1.append(new Group(player, selectedCharacter));
            }
            List<Group> associations = associations1;
            GameRoundAssociations groups = new GameRoundAssociations(associations);

            for (int iii = 0; iii < 8; iii++) {
                for (int ii = 0; ii < associations.size(); ii++) {
                    if (iii + 1 == associations.get(ii).character.getNumber()) {
                        if (associations.get(ii).isMurdered()) {}
                        else{
                            Group group = associations.get(ii);
                            associations.get(ii).thief().peek(thief -> thief.steal(group.getPlayer()));
                            Set<String> baseActions = HashSet.of("Draw 2 getCards and keep 1", "Receive 2 coins");
                            List<District> districts = group.getPlayer().getCity().getDistricts();
                            Set<String> availableActions = baseActions;
                            for (District d : districts) {
                                if (d == District.OBSERVATORY) {
                                    availableActions = availableActions.replace("Draw 2 getCards and keep 1", "Draw 3 getCards and keep 1");
                                }
                            }
                            // keep only actions that getPlayer can realize
                            List<String> possibleActions = List.empty();
                            for (String action : availableActions) {
                                if (action == "Draw 2 getCards and keep 1") {
                                    if (pioche.canDraw(2))
                                    possibleActions = possibleActions.append("Draw 2 getCards and keep 1");
                                }
                                else if (action == "Draw 3 getCards and keep 1") {
                                    if (pioche.canDraw(3))
                                        possibleActions = possibleActions.append("Draw 2 getCards and keep 1");
                                }
                                else {
                                    possibleActions = possibleActions.append(action);
                                }
                            }
                            String actionType = group.getPlayer().controller.selectActionAmong(possibleActions.toList());
                            // execute selected action
                            if (actionType == "Draw 2 getCards and keep 1") {
                                Set<Card> cardsDrawn = pioche.draw(2);
                                if (!group.getPlayer().getCity().has(District.LIBRARY)) {
                                    Card keptCard = group.getPlayer().controller.selectAmong(cardsDrawn);
                                    pioche.discard(cardsDrawn.remove(keptCard).toList());
                                    cardsDrawn = HashSet.of(keptCard);
                                }
                                group.getPlayer().addCards(cardsDrawn);
                            }
                            else if (actionType == "Receive 2 coins") {
                                group.getPlayer().addGold(2);
                            }
                            else if (actionType == "Draw 3 getCards and keep 1") {
                                Set<Card> cardsDrawn = pioche.draw(3);
                                if (!group.getPlayer().getCity().has(District.LIBRARY)) {
                                    Card keptCard = group.getPlayer().controller.selectAmong(cardsDrawn);
                                    pioche.discard(cardsDrawn.remove(keptCard).toList());
                                    cardsDrawn = HashSet.of(keptCard);
                                }
                                group.getPlayer().addCards(cardsDrawn);
                            }
                            actionExecuted(group, actionType, associations);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            // receive powers from the getCharacter
                            List<String> powers = group.getCharacter().getPowers();
////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            List<String>  extraActions = List.empty();
                            for (District d : group.getPlayer().getCity().getDistricts()) {
                                if (d == District.SMITHY) {
                                    extraActions = extraActions.append("Draw 3 getCards for 2 coins");
                                }
                                if (d == District.LABORATORY) {
                                    extraActions = extraActions.append("Discard getCard for 2 coins");
                                }
                            }
                            Set<String> availableActions11 = Group.OPTIONAL_ACTIONS
                                    .addAll(powers)
                                    .addAll(extraActions);
                            String actionType11;
                            do {
                                Set<String> availableActions1 = availableActions11;
                                // keep only actions that getPlayer can realize
                                List<String> possibleActions2 = List.empty();
                                for (String action : availableActions1) {
                                    if (action == "Build getDistrict") {
                                        if (!group.getPlayer().buildableDistrictsInHand().isEmpty())
                                            possibleActions2 = possibleActions2.append("Build getDistrict");
                                    }
                                    else if (action == "Destroy getDistrict") {
                                        if (DestroyDistrictAction.districtsDestructibleBy(groups, group.getPlayer()).exists(districtsByPlayer -> !districtsByPlayer._2().isEmpty())) {
                                            possibleActions2 = possibleActions2.append("Destroy getDistrict");
                                        }
                                    }
                                    else if (action == "Discard getCard for 2 coins") {
                                        if (!group.getPlayer().getCards().isEmpty()) {
                                            possibleActions2 = possibleActions2.append("Discard getCard for 2 coins");
                                        }
                                    }
                                    else if (action == "Draw 3 getCards for 2 coins") {
                                        if (pioche.canDraw(3) && group.getPlayer().canAfford(2))
                                        possibleActions2 = possibleActions2.append("Draw 3 getCards for 2 coins");
                                    }
                                    else if (action == "Exchange getCards with pile") {
                                        if (!group.getPlayer().getCards().isEmpty() && pioche.canDraw(1)) {
                                            possibleActions2 = possibleActions2.append("Exchange getCards with pile");
                                        }
                                    }
                                    else if (action == "Pick 2 getCards") {
                                        if (pioche.canDraw(2))
                                            possibleActions2 = possibleActions2.append("Pick 2 getCards");
                                    }
                                    else
                                        possibleActions2 = possibleActions2.append(action);
                                }
                                String actionType1 = group.getPlayer().controller.selectActionAmong(possibleActions2.toList());
                                // execute selected action
                                if (actionType1 == "End round")
                                    {} else if (actionType1 == "Build getDistrict") {
                                    Card card = group.getPlayer().controller.selectAmong(group.getPlayer().buildableDistrictsInHand());
                                    group.getPlayer().buildDistrict(card);
                                    }
                                    else if (actionType1 == "Discard getCard for 2 coins") {
                                    Player player = group.getPlayer();
                                    Card card = player.controller.selectAmong(player.getCards());
                                    player.cards = player.getCards().remove(card);
                                    pioche.discard(card);
                                    player.addGold(2);
                                    }
                                    else if (actionType1 == "Draw 3 getCards for 2 coins") {
                                    group.getPlayer().addCards(pioche.draw(3));
                                    group.getPlayer().pay(2);
                                    }
                                    else if (actionType1 == "Exchange getCards with pile") {
                                    Set<Card> cardsToSwap = group.getPlayer().controller.selectManyAmong(group.getPlayer().getCards());
                                    group.getPlayer().cards = group.getPlayer().getCards().removeAll(cardsToSwap);
                                    group.getPlayer().addCards(pioche.swapWith(cardsToSwap.toList()));
                                    }
                                    else if (actionType1 == "Exchange getCards with other getPlayer") {
                                    Player playerToSwapWith = group.getPlayer().controller.selectPlayerAmong(groups.associations.map(Group::getPlayer).remove(group.getPlayer()));
                                    group.getPlayer().exchangeHandWith(playerToSwapWith);
                                    }
                                    else if (actionType1 == "Kill") {
                                    Character characterToMurder = group.getPlayer().controller.selectAmong(List.of(Character.THIEF, Character.MAGICIAN, Character.KING, Character.BISHOP, Character.MERCHANT, Character.ARCHITECT, Character.WARLORD));
                                    groups.associationToCharacter(characterToMurder).peek(Group::murder);
                                    }
                                    else if (actionType1 == "Pick 2 getCards") {
                                    group.getPlayer().addCards(pioche.draw(2));
                                    }
                                    else if (actionType1 == "Receive 2 coins") {
                                    group.getPlayer().addGold(2);
                                    }
                                    else if (actionType1 == "Receive 1 getGold") {
                                    group.getPlayer().addGold(1);
                                    }
                                    else if (actionType1 == "Receive income") {
                                    DistrictType type = null;
                                    if (group.character == Character.BISHOP) {
                                            type = DistrictType.RELIGIOUS;
                                        }
                                    else if (group.character == Character.WARLORD) {
                                        type = DistrictType.MILITARY;
                                    }
                                    else if (group.character == Character.KING) {
                                        type = DistrictType.NOBLE;
                                    }
                                    else if (group.character == Character.MERCHANT) {
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
                                else if (actionType1 == "Destroy getDistrict") {/*
                                    City cityToDestroy = LA_CITY_CHOISIS
                                    LA_CITY_CHOISIS.districtDestructibleBy()*/

                                }
                                    else if (actionType1 == "Rob") {
                                    Character character = group.getPlayer().controller.selectAmong(List.of(Character.MAGICIAN, Character.KING, Character.BISHOP, Character.MERCHANT, Character.ARCHITECT, Character.WARLORD)
                                            .removeAll(groups.associations.find(Group::isMurdered).map(Group::getCharacter)));
                                    groups.associationToCharacter(character).peek(association -> association.stolenBy(group.getPlayer()));
                                }
                                actionExecuted(group, actionType1, associations);
                                actionType11 = actionType1;
                                availableActions11 = availableActions11.remove(actionType11);
                            }
                            while (!availableActions11.isEmpty() && actionType11 != "End round");
                        }
                    }
                }
            }
            roundAssociations = associations;
            crown = roundAssociations.find(a -> a.character == Character.KING).map(Group::getPlayer).getOrElse(crown);
        } while (!players.map(Player::getCity).exists(City::isComplete));

        // classe les joueurs par leur score
        // si ex-aequo, le premier est celui qui n'est pas assassiné
        // si pas d'assassiné, le gagnant est le joueur ayant eu le personnage avec le numéro d'ordre le plus petit au dernier tour
        System.out.println("Classement: " + roundAssociations.sortBy(a -> Tuple.of(a.getPlayer().score(), !a.isMurdered(), a.character))
                .reverse()
                .map(Group::getPlayer));
    }

    public static void actionExecuted(Group association, String actionType, List<Group> associations) {
        System.out.println("Player " + association.getPlayer().name() + " executed action " + actionType);
        associations.map(Group::getPlayer)
                .forEach(Citadels::displayStatus);
    }

    private static void displayStatus(Player player) {
        System.out.println("  Player " + player.name() + ":");
        System.out.println("    Gold coins: " + player.getGold());
        System.out.println("    City: " + textCity(player));
        System.out.println("    Hand size: " + player.getCards().size());
        if (player.controller instanceof HumanController) {
            System.out.println("    Hand: " + textHand(player));
        }
        System.out.println();
    }

    public static String textCity(Player player) {
        List<District> districts = player.getCity().getDistricts();
        return districts.isEmpty() ? "Empty" : districts.map(Citadels::textDistrict).mkString(", ");
    }

    public static String textDistrict(District district) {
        return district.name() + "(" + district.getDistrictType().name() + ", " + district.getCost() + ")";
    }

    private static String textHand(Player player) {
        Set<Card> cards = player.getCards();
        return cards.isEmpty() ? "Empty" : cards.map(Citadels::textCard).mkString(", ");
    }

    private static String textCard(Card card) {
        return textDistrict(card.getDistrict());
    }
}
