package com.montaury.citadels;


import com.montaury.citadels.character.Character;
import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.DestructibleDistrict;
import com.montaury.citadels.district.District;
import com.montaury.citadels.player.HumanController;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.GameRoundAssociations;
import com.montaury.citadels.round.Group;
import io.vavr.collection.*;
import com.montaury.citadels.Citadels;

import org.junit.Before;
import org.junit.Test;


import static com.montaury.citadels.Citadels.textCity;
import static com.montaury.citadels.Citadels.textDistrict;
import static com.montaury.citadels.district.Card.*;
import static com.montaury.citadels.round.action.DestroyDistrictAction.districtsDestructibleBy;
import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;



public class DestroyDistrictTest {

    public Board board;
    public City firstCity;
    public Player player1;

    @Before
    public void setUp() {
        board = new Board();
        firstCity = new City(board);
        player1 = new Player("guigeek",8,firstCity,null);
        player1.add(50);


        firstCity.buildDistrict(CHURCH_1);// COST TO DESTROY = 1
        firstCity.buildDistrict(WATCHTOWER_1); //COST TO DESTROY = 0
    }

    @Test
    public void destroy_1_district() {
        firstCity.destroyDistrict(CHURCH_1);
        assertThat(firstCity.getDistrictCards().size()).isEqualTo(1);

        player1.pay(firstCity.destructionCost(CHURCH_1));
        assertThat(player1.gold()).isEqualTo(49);
    }


    @Test
    public void destroy_district_of_cost_0(){
        /*
        firstCity.destroyDistrict(CHURCH_1);
        //List<DestructibleDistrict> destructibleDistricts2 = firstCity.districtsDestructibleBy(player1);
        //List<DestructibleDistrict> destructibleDistrictsExpected = null;
        //destructibleDistrictsExpected.append(CHURCH_1);
        System.out.println("    City: " + textCity(player1));
        System.out.println("    Gold: " + player1.gold());


        List<Group> associations1 = List.empty();
        City secondCity, thirdCity;
        secondCity = new City(board);
        thirdCity = new City(board);
        Player player2, player3, player4;


        player2 = new Player("bbbbb",12,secondCity,null);
        player3 = new Player("aaa",9,thirdCity,null);

        player4 = new Player("condo", 15, null, null);
        player4.add(50);

        secondCity.buildDistrict(CHURCH_2);// COST TO DESTROY = 1
        secondCity.buildDistrict(WATCHTOWER_2); //COST TO DESTROY = 0

        thirdCity.buildDistrict(KEEP_1);// COST TO DESTROY = 1
        thirdCity.buildDistrict(MONASTERY_1); //COST TO DESTROY = 2

        associations1 = associations1.append(new Group(player1, Character.MAGICIAN));
        associations1 = associations1.append(new Group(player2, Character.KING));
        associations1 = associations1.append(new Group(player3, Character.BISHOP));
        associations1 = associations1.append(new Group(player4, Character.WARLORD));
        List<Group> associations = associations1;
        GameRoundAssociations groups = new GameRoundAssociations(associations);

        Map<Player, List<DestructibleDistrict>> LAMAP = districtsDestructibleBy(groups,player3);
        DestructibleDistrict myDistrict;
        myDistrict = player1.controller.selectDistrictToDestroyAmong(LAMAP);

        //assertThat(destructibleDistrictsExpected).isEqualTo(destructibleDistricts2);
        */
    }

}