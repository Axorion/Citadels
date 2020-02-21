package com.montaury.citadels;


import com.montaury.citadels.district.Card;
import com.montaury.citadels.player.HumanController;
import com.montaury.citadels.player.Player;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;


import io.vavr.collection.List;
import io.vavr.collection.Set;
import org.junit.Before;
import org.junit.Test;


import static com.montaury.citadels.district.Card.*;
import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

public class CityTest {
    public Board board;
    public City firstCity;

    @Before
    public void setUp(){
        board = new Board();
        firstCity = new City(board);

    }

    @Test
    public void first_to_end_with_7_districts(){
        Possession possession= new Possession(0,null);

        firstCity.buildDistricts(List.of(MANOR_1, FORTRESS_1, PRISON_1, CASTLE_1, PALACE_1, WATCHTOWER_1, BATTLEFIELD_1));
        // MANOR_1 -> NOBLE - 3
        // FORTRESS_1 -> MILITARY - 5
        // PRISON_1 -> MILITARY - 2
        // CASTLE_1 -> NOBLE - 4
        // PALACE_1 -> NOBLE - 5
        // WATCHTOWER_1 -> MILITARY - 1
        // BATTLEFIELD_1 -> MILITARY - 3
        //end Value : 23

        int actualValue = firstCity.calculScore(possession);

        // AND BONUS : +4(First)
        int expectedValue = 27;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    public void first_to_end_with_8_districts(){
        Possession possession= new Possession(0,null);

        firstCity.buildDistricts(List.of(MANOR_1, FORTRESS_1, PRISON_1, CASTLE_1, PALACE_1, WATCHTOWER_1, BATTLEFIELD_1, DOCKS_1));
        // MANOR_1 -> NOBLE - 3
        // FORTRESS_1 -> MILITARY - 5
        // PRISON_1 -> MILITARY - 2
        // CASTLE_1 -> NOBLE - 4
        // PALACE_1 -> NOBLE - 5
        // WATCHTOWER_1 -> MILITARY - 1
        // BATTLEFIELD_1 -> MILITARY - 3
        // DOCKS_1 -> TRADE - 3
        //end Value : 26

        int actualValue = firstCity.calculScore(possession);

        // AND BONUS : +4 (First)
        int expectedValue = 30;

        assertThat(actualValue).isEqualTo(expectedValue);    }


    @Test
    public void first_to_end_with_7_districts_and_5_differents_districts(){
        Possession possession= new Possession(0,null);

        firstCity.buildDistricts(List.of(MANOR_1, FORTRESS_1, TAVERN_1, TEMPLE_1, CASTLE_1, PRISON_1, KEEP_1));
        // MANOR_1 -> NOBLE - 3
        // FORTRESS_1 -> MILITARY - 5
        // TAVERN_1 -> TRADE - 1
        // TEMPLE_1 -> RELIGIOUS - 1
        // CASTLE_1 -> NOBLE - 4
        // PRISON_1 -> MILITARY - 2
        // KEEP_1 -> SPECIAL - 3
        //end Value : 19

        int actualValue = firstCity.calculScore(possession);

        // AND BONUS : +3 (5DifferentTypes) + 4(First)
        int expectedValue = 26;

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void first_to_end_with_8_districts_and_5_differents_districts(){
        Possession possession= new Possession(0,null);

        firstCity.buildDistricts(List.of(MANOR_1, FORTRESS_1, TAVERN_1, TEMPLE_1, CASTLE_1, PRISON_1, KEEP_1, WATCHTOWER_1));
        // MANOR_1 -> NOBLE - 3
        // FORTRESS_1 -> MILITARY - 5
        // TAVERN_1 -> TRADE - 1
        // TEMPLE_1 -> RELIGIOUS - 1
        // CASTLE_1 -> NOBLE - 4
        // PRISON_1 -> MILITARY - 2
        // KEEP_1 -> SPECIAL - 3
        // WATCHTOWER_1 -> MILITARY - 1
        //end Value : 20

        int actualValue = firstCity.calculScore(possession);

        // AND BONUS : +3 (5DifferentTypes) + 4(First)
        int expectedValue = 27;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    public void end_with_7_districts(){
        City secondCity = new City(board);
        Possession possession= new Possession(0,null);

        firstCity.buildDistricts(List.of(MANOR_1, FORTRESS_1, TAVERN_1, TEMPLE_1, CASTLE_1, PRISON_1, KEEP_1));
        // MANOR_1 -> NOBLE - 3
        // FORTRESS_1 -> MILITARY - 5
        // TAVERN_1 -> TRADE - 1
        // TEMPLE_1 -> RELIGIOUS - 1
        // CASTLE_1 -> NOBLE - 4
        // PRISON_1 -> MILITARY - 2
        // KEEP_1 -> SPECIAL - 3

        secondCity.buildDistricts(List.of(MANOR_2, FORTRESS_2, PRISON_2, CASTLE_2, WATCHTOWER_1, BATTLEFIELD_1, DOCKS_1));
        // MANOR_2 -> NOBLE - 3
        // FORTRESS_2 -> MILITARY - 5
        // PRISON_2 -> MILITARY - 2
        // CASTLE_2 -> NOBLE - 4
        // WATCHTOWER_1 -> MILITARY - 1
        // BATTLEFIELD_1 -> MILITARY - 3
        // DOCKS_1 -> TRADE - 3
        //end Value : 21

        int actualValue = secondCity.calculScore(possession);

        // AND BONUS : 2 (complete)
        int expectedValue = 23;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    public void end_with_5_differents_districts(){
        City secondCity = new City(board);
        Possession possession= new Possession(0,null);

        firstCity.buildDistricts(List.of(MANOR_1, FORTRESS_1, TAVERN_1, TEMPLE_1, CASTLE_1, PRISON_1, KEEP_1));
        // MANOR_1 -> NOBLE - 3
        // FORTRESS_1 -> MILITARY - 5
        // TAVERN_1 -> TRADE - 1
        // TEMPLE_1 -> RELIGIOUS - 1
        // CASTLE_1 -> NOBLE - 4
        // PRISON_1 -> MILITARY - 2
        // KEEP_1 -> SPECIAL - 3

        secondCity.buildDistricts(List.of(MANOR_2, FORTRESS_2, TAVERN_1, TEMPLE_1, KEEP_1));
        // MANOR_2 -> NOBLE - 3
        // FORTRESS_2 -> MILITARY - 5
        // TAVERN_1 -> TRADE - 1
        // TEMPLE_1 -> RELIGIOUS - 1
        // KEEP_1 -> SPECIAL - 3
        //end Value : 13

        int actualValue = secondCity.calculScore(possession);

        // AND BONUS : +3 (5DifferentTypes)
        int expectedValue = 16;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    public void end_with_dragon_gate(){
        City secondCity = new City(board);
        Possession possession= new Possession(0,null);

        firstCity.buildDistricts(List.of(MANOR_1, FORTRESS_1, TAVERN_1, TEMPLE_1, CASTLE_1, PRISON_1, KEEP_1));
        // MANOR_1 -> NOBLE - 3
        // FORTRESS_1 -> MILITARY - 5
        // TAVERN_1 -> TRADE - 1
        // TEMPLE_1 -> RELIGIOUS - 1
        // CASTLE_1 -> NOBLE - 4
        // PRISON_1 -> MILITARY - 2
        // KEEP_1 -> SPECIAL - 3

        secondCity.buildDistrict(DRAGON_GATE); //SPECIAL - 6
        //end Value : 6

        int actualValue = secondCity.calculScore(possession);

        // AND BONUS : +2 (dragonGate Effect)
        int expectedValue = 8;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    public void end_with_university(){
        City secondCity = new City(board);
        Possession possession= new Possession(0,null);

        firstCity.buildDistricts(List.of(MANOR_1, FORTRESS_1, TAVERN_1, TEMPLE_1, CASTLE_1, PRISON_1, KEEP_1));
        // MANOR_1 -> NOBLE - 3
        // FORTRESS_1 -> MILITARY - 5
        // TAVERN_1 -> TRADE - 1
        // TEMPLE_1 -> RELIGIOUS - 1
        // CASTLE_1 -> NOBLE - 4
        // PRISON_1 -> MILITARY - 2
        // KEEP_1 -> SPECIAL - 3

        secondCity.buildDistrict(UNIVERSITY); //SPECIAL - 6
        //end Value : 6

        int actualValue = secondCity.calculScore(possession);

        // AND BONUS : +2 (university Effect)
        int expectedValue = 8;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    public void end_with_map_room(){
        City secondCity = new City(board);
        Set<Card> testHand = HashSet.empty();
        testHand= testHand.add(MANOR_2);
        testHand= testHand.add(MANOR_3);

        Possession possession= new Possession(0,testHand);

        firstCity.buildDistricts(List.of(MANOR_1, FORTRESS_1, TAVERN_1, TEMPLE_1, CASTLE_1, PRISON_1, KEEP_1));
        // MANOR_1 -> NOBLE - 3
        // FORTRESS_1 -> MILITARY - 5
        // TAVERN_1 -> TRADE - 1
        // TEMPLE_1 -> RELIGIOUS - 1
        // CASTLE_1 -> NOBLE - 4
        // PRISON_1 -> MILITARY - 2
        // KEEP_1 -> SPECIAL - 3

        secondCity.buildDistrict(MAP_ROOM); //SPECIAL - 5
        //end Value : 5

        int actualValue = secondCity.calculScore(possession);

        // AND BONUS : +2 (cards in hand)
        int expectedValue = 7;

        assertThat(actualValue).isEqualTo(expectedValue);
    }


    @Test
    public void end_with_treasury(){
        City secondCity = new City(board);
        Possession possession= new Possession(5,null);

        firstCity.buildDistricts(List.of(MANOR_1, FORTRESS_1, TAVERN_1, TEMPLE_1, CASTLE_1, PRISON_1, KEEP_1));
        // MANOR_1 -> NOBLE - 3
        // FORTRESS_1 -> MILITARY - 5
        // TAVERN_1 -> TRADE - 1
        // TEMPLE_1 -> RELIGIOUS - 1
        // CASTLE_1 -> NOBLE - 4
        // PRISON_1 -> MILITARY - 2
        // KEEP_1 -> SPECIAL - 3

        secondCity.buildDistricts(List.of(PRISON_2, TREASURY));
        // PRISON_2 -> MILITARY - 2
        // TREASURY -> SPECIAL - 5
        //end Value : 7

        int actualValue = secondCity.calculScore(possession);

        // AND BONUS : +5 (golds in hand)
        int expectedValue = 12;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

}