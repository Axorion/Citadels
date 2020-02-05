package com.montaury.citadels;


import com.montaury.citadels.district.Card;
import com.montaury.citadels.player.HumanController;
import com.montaury.citadels.player.Player;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import io.vavr.collection.Set;

import static com.montaury.citadels.district.Card.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CityTest {

    @Test
    public void FirstToEndWith7Districts(){
        Board board = new Board();
        City testCity = new City(board);
        Possession possession= new Possession(0,null);

        testCity.buildDistrict(MANOR_1); //NOBLE - 3
        testCity.buildDistrict(FORTRESS_1); //MILITARY - 5
        testCity.buildDistrict(PRISON_1); //MILITARY - 2
        testCity.buildDistrict(CASTLE_1); //NOBLE - 4
        testCity.buildDistrict(PALACE_1); //NOBLE - 5
        testCity.buildDistrict(WATCHTOWER_1); //MILITARY - 1
        testCity.buildDistrict(BATTLEFIELD_1); //MILITARY - 3
        //end Value : 23

        int actualValue = testCity.score(possession);

        // AND BONUS : +4(First)
        int expectedValue = 27;

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void FirstToEndWith8Districts(){
        Board board = new Board();
        City testCity = new City(board);
        Possession possession= new Possession(0,null);

        testCity.buildDistrict(MANOR_1); //NOBLE - 3
        testCity.buildDistrict(FORTRESS_1); //MILITARY - 5
        testCity.buildDistrict(PRISON_1); //MILITARY - 2
        testCity.buildDistrict(CASTLE_1); //NOBLE - 4
        testCity.buildDistrict(PALACE_1); //NOBLE - 5
        testCity.buildDistrict(WATCHTOWER_1); //MILITARY - 1
        testCity.buildDistrict(BATTLEFIELD_1); //MILITARY - 3
        testCity.buildDistrict(DOCKS_1); //TRADE - 3
        //end Value : 26

        int actualValue = testCity.score(possession);

        // AND BONUS : +4 (First)
        int expectedValue = 30;

        assertEquals(expectedValue, actualValue);
    }


    @Test
    public void FirstToEndWith7DistrictsAnd5DifferentsDistricts(){
        Board board = new Board();
        City testCity = new City(board);
        Possession possession= new Possession(0,null);

        testCity.buildDistrict(MANOR_1); //NOBLE - 3
        testCity.buildDistrict(FORTRESS_1); //MILITARY - 5
        testCity.buildDistrict(TAVERN_1); //TRADE - 1
        testCity.buildDistrict(TEMPLE_1); //RELIGIOUS - 1
        testCity.buildDistrict(CASTLE_1); //NOBLE - 4
        testCity.buildDistrict(PRISON_1); //MILITARY - 2
        testCity.buildDistrict(KEEP_1); //SPECIAL - 3
        //end Value : 19

        int actualValue = testCity.score(possession);

        // AND BONUS : +3 (5DifferentTypes) + 4(First)
        int expectedValue = 26;

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void FirstToEndWith8DistrictsAnd5DifferentsDistricts(){
        Board board = new Board();
        City testCity = new City(board);
        Possession possession= new Possession(0,null);

        testCity.buildDistrict(MANOR_1); //NOBLE - 3
        testCity.buildDistrict(FORTRESS_1); //MILITARY - 5
        testCity.buildDistrict(TAVERN_1); //TRADE - 1
        testCity.buildDistrict(TEMPLE_1); //RELIGIOUS - 1
        testCity.buildDistrict(CASTLE_1); //NOBLE - 4
        testCity.buildDistrict(PRISON_1); //MILITARY - 2
        testCity.buildDistrict(KEEP_1); //SPECIAL - 3
        testCity.buildDistrict(WATCHTOWER_1); //MILITARY - 1
        //end Value : 20

        int actualValue = testCity.score(possession);

        // AND BONUS : +3 (5DifferentTypes) + 4(First)
        int expectedValue = 27;

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void EndWith7Districts(){
        Board board = new Board();
        City firstCity = new City(board);
        City secondCity = new City(board);
        Possession possession= new Possession(0,null);

        firstCity.buildDistrict(MANOR_1);
        firstCity.buildDistrict(FORTRESS_1);
        firstCity.buildDistrict(TAVERN_1);
        firstCity.buildDistrict(TEMPLE_1);
        firstCity.buildDistrict(CASTLE_1);
        firstCity.buildDistrict(PRISON_1);
        firstCity.buildDistrict(KEEP_1);

        secondCity.buildDistrict(MANOR_2); //NOBLE - 3
        secondCity.buildDistrict(FORTRESS_2); //MILITARY - 5
        secondCity.buildDistrict(PRISON_2); //MILITARY - 2
        secondCity.buildDistrict(CASTLE_2); //NOBLE - 4
        secondCity.buildDistrict(WATCHTOWER_1); //MILITARY - 1
        secondCity.buildDistrict(BATTLEFIELD_1); //MILITARY - 3
        secondCity.buildDistrict(DOCKS_1); //TRADE - 3
        //end Value : 21

        int actualValue = secondCity.score(possession);

        // AND BONUS : 0 none
        int expectedValue = 21;

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void EndWith5DifferentsDistricts(){
        Board board = new Board();
        City firstCity = new City(board);
        City secondCity = new City(board);
        Possession possession= new Possession(0,null);

        firstCity.buildDistrict(MANOR_1);
        firstCity.buildDistrict(FORTRESS_1);
        firstCity.buildDistrict(TAVERN_1);
        firstCity.buildDistrict(TEMPLE_1);
        firstCity.buildDistrict(CASTLE_1);
        firstCity.buildDistrict(PRISON_1);
        firstCity.buildDistrict(KEEP_1);

        secondCity.buildDistrict(MANOR_2); //NOBLE - 3
        secondCity.buildDistrict(FORTRESS_2); //MILITARY - 5
        secondCity.buildDistrict(TAVERN_1); //TRADE - 1
        secondCity.buildDistrict(TEMPLE_1); //RELIGIOUS - 1
        secondCity.buildDistrict(KEEP_1); //SPECIAL - 3
        //end Value : 13

        int actualValue = secondCity.score(possession);

        // AND BONUS : +3 (5DifferentTypes)
        int expectedValue = 16;

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void EndWithDragonGate(){
        Board board = new Board();
        City firstCity = new City(board);
        City secondCity = new City(board);
        Possession possession= new Possession(0,null);

        firstCity.buildDistrict(MANOR_1);
        firstCity.buildDistrict(FORTRESS_1);
        firstCity.buildDistrict(TAVERN_1);
        firstCity.buildDistrict(TEMPLE_1);
        firstCity.buildDistrict(CASTLE_1);
        firstCity.buildDistrict(PRISON_1);
        firstCity.buildDistrict(KEEP_1);

        secondCity.buildDistrict(DRAGON_GATE); //SPECIAL - 6
        //end Value : 6

        int actualValue = secondCity.score(possession);

        // AND BONUS : +2 (dragonGate Effect)
        int expectedValue = 8;

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void EndWithUniversity(){
        Board board = new Board();
        City firstCity = new City(board);
        City secondCity = new City(board);
        Possession possession= new Possession(0,null);

        firstCity.buildDistrict(MANOR_1);
        firstCity.buildDistrict(FORTRESS_1);
        firstCity.buildDistrict(TAVERN_1);
        firstCity.buildDistrict(TEMPLE_1);
        firstCity.buildDistrict(CASTLE_1);
        firstCity.buildDistrict(PRISON_1);
        firstCity.buildDistrict(KEEP_1);

        secondCity.buildDistrict(UNIVERSITY); //SPECIAL - 6
        //end Value : 6

        int actualValue = secondCity.score(possession);

        // AND BONUS : +2 (university Effect)
        int expectedValue = 8;

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void EndWithMapRoom(){
        Board board = new Board();
        City firstCity = new City(board);
        City secondCity = new City(board);
        Set<Card> testHand = HashSet.empty();
        testHand.add(MANOR_2);
        testHand.add(MANOR_3);

        Possession possession= new Possession(0,testHand);

        firstCity.buildDistrict(MANOR_1);
        firstCity.buildDistrict(FORTRESS_1);
        firstCity.buildDistrict(TAVERN_1);
        firstCity.buildDistrict(TEMPLE_1);
        firstCity.buildDistrict(CASTLE_1);
        firstCity.buildDistrict(PRISON_1);
        firstCity.buildDistrict(KEEP_1);

        secondCity.buildDistrict(MAP_ROOM); //SPECIAL - 5
        //end Value : 5

        int actualValue = secondCity.score(possession);

        // AND BONUS : +2 (cards in hand)
        int expectedValue = 7;

        assertEquals(expectedValue, actualValue);
    }


    @Test
    public void EndWithTreasury(){
        Board board = new Board();
        City firstCity = new City(board);
        City secondCity = new City(board);

        Possession possession= new Possession(5,null);

        firstCity.buildDistrict(MANOR_1);
        firstCity.buildDistrict(FORTRESS_1);
        firstCity.buildDistrict(TAVERN_1);
        firstCity.buildDistrict(TEMPLE_1);
        firstCity.buildDistrict(CASTLE_1);
        firstCity.buildDistrict(PRISON_1);
        firstCity.buildDistrict(KEEP_1);

        secondCity.buildDistrict(PRISON_2); //MILITARY - 2
        secondCity.buildDistrict(TREASURY); //SPECIAL - 5
        //end Value : 7

        int actualValue = secondCity.score(possession);

        // AND BONUS : +5 (cards in hand)
        int expectedValue = 12;

        assertEquals(expectedValue, actualValue);
    }

}