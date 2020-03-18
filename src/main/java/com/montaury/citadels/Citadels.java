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
import com.montaury.citadels.round.action.*;
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

        System.out.println("Quel est votre age ? ");
        int playerAge = scanner.nextInt();

        Board board = new Board();
        Player p = new Player(playerName, playerAge, new City(board), new HumanController());
        List<Player> players = List.of(p);

        int nbP;
        do {
            System.out.println("Saisir le nombre de joueurs total (entre 2 et 8): ");
            nbP = scanner.nextInt();
        } while (nbP < 2 || nbP > 8);

        for (int joueurs = 1; joueurs < nbP; joueurs ++) {
            Player player = new Player("Computer " + joueurs, 35, new City(board), new ComputerController());
            players = players.append(player);
        }

        CardPile pioche = new CardPile(Card.all().toList().shuffle());

        players.forEach(player -> {
            player.addGold(2);
            player.addCards(pioche.draw(2));
        });
        Player crown = players.maxBy(Player::getAge).get();

        String answer;
        List<Character> characterOfTheGame = List.of(Character.ASSASSIN, Character.THIEF, Character.MAGICIAN, Character.KING, Character.BISHOP, Character.MERCHANT, Character.WARLORD);
        System.out.println("Voulez-vous remplacer l'ARTCHITECTE par le NAVIGUATEUR ? (oui/non) ");
        answer = scanner.next();
        if (answer == "oui" || answer == "Oui"){
            characterOfTheGame.push(Character.NAVIGATEUR);
        }
        else {
            characterOfTheGame.push(Character.ARCHITECT);
        }

        List<Group> roundAssociations;
        do {
            java.util.List<Player> list = players.asJavaMutable();
            Collections.rotate(list, -players.indexOf(crown));
            List<Player> playersInOrder = List.ofAll(list);
            RandomCharacterSelector randomCharacterSelector = new RandomCharacterSelector();
            List<Character> availableCharacters = characterOfTheGame;

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
                            Set<ActionType> baseActions = HashSet.of(ActionType.DRAW_2_CARDS_KEEP_1, ActionType.RECEIVE_2_COIN);
                            List<District> districts = group.getPlayer().getCity().getDistricts();
                            Set<ActionType> availableActions = baseActions;
                            for (District d : districts) {
                                if (d == District.OBSERVATORY) {
                                    availableActions = availableActions.replace(ActionType.DRAW_2_CARDS_KEEP_1, ActionType.DRAW_3_CARDS_KEEP_1);
                                }
                            }
                            // keep only actions that getPlayer can realize

                            List<ActionType> possibleActions = List.empty();
                            for (ActionType actionType : availableActions) {
                                if (actionType.getAction().canBeExecuted(group, pioche, groups))
                                    possibleActions = possibleActions.append(actionType);
                            }

                            // execute selected action

                            ActionType chosenAction = group.getPlayer().controller.selectActionAmong(possibleActions.toList());
                            chosenAction.getAction().executeAction(group, pioche, groups);
                            actionExecuted(group, chosenAction, associations);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            // receive powers from the getCharacter
                            List<ActionType> powers = group.getCharacter().getPowers();
////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            List<ActionType>  extraActions = List.empty();
                            for (District d : group.getPlayer().getCity().getDistricts()) {
                                if (d == District.SMITHY) {
                                    extraActions = extraActions.append(ActionType.DRAW_3_CARDS_FOR_2_COINS);
                                }
                                if (d == District.LABORATORY) {
                                    extraActions = extraActions.append(ActionType.DISCARD_CARD_FOR_2_COINS);
                                }
                            }
                            Set<ActionType> availableActions11 = Group.OPTIONAL_ACTIONS
                                    .addAll(powers)
                                    .addAll(extraActions);

                            //availableActions11.add(ActionType.END_ROUND);

                            ActionType actionType11;
                            do {
                                Set<ActionType> availableActions1 = availableActions11;
                                // keep only actions that getPlayer can realize

                                List<ActionType> possibleActions2 = List.empty();
                                for (ActionType actionType : availableActions1) {
                                    if (actionType.getAction().canBeExecuted(group, pioche, groups))
                                        possibleActions2 = possibleActions2.append(actionType);
                                }
                                ActionType actionChoisie = group.getPlayer().controller.selectActionAmong(possibleActions2.toList());

                                // execute selected action
                                actionChoisie.getAction().executeAction(group,pioche,groups);
                                actionExecuted(group, actionChoisie, associations);
                                actionType11 = actionChoisie;
                                availableActions11 = availableActions11.remove(actionType11);
                            }
                            while (!availableActions11.isEmpty() && actionType11 != ActionType.END_ROUND && actionType11.getAction().canEndRound(group, pioche, groups)==false);
                            //Traitements relatifs aux nouvelles cartes
                            if(group.getPlayer().getCity().has(District.PARC) && group.getPlayer().getCards().isEmpty()){
                                ActionType.PICK_2_CARDS.getAction().executeAction(group, pioche, groups);
                            }
                            if(group.getPlayer().getCity().has(District.HOSPICE) && group.getPlayer().getGold() == 0){
                                ActionType.RECEIVE_1_COIN.getAction().executeAction(group, pioche, groups);
                            }
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

    public static void actionExecuted(Group association, ActionType actionType, List<Group> associations) {
        System.out.println("Player " + association.getPlayer().name() + " executed action " + actionType.getName());
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
