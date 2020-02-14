package com.montaury.citadels;


import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.DestructibleDistrict;
import com.montaury.citadels.district.District;
import com.montaury.citadels.player.HumanController;
import com.montaury.citadels.player.Player;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import com.montaury.citadels.Citadels;

import io.vavr.collection.List;
import io.vavr.collection.Set;
import org.junit.Before;
import org.junit.Test;


import static com.montaury.citadels.Citadels.textCity;
import static com.montaury.citadels.Citadels.textDistrict;
import static com.montaury.citadels.district.Card.*;
import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

public class DestroyDistrictTest {

    public Board board;
    public City firstCity;
    public Player player1;

    @Before
    public void setUp() {
        board = new Board();
        firstCity = new City(board);
        player1 = new Player("guigeek",8,firstCity,null);


        firstCity.buildDistrict(CHURCH_1);// COST TO DESTROY = 1
        firstCity.buildDistrict(WATCHTOWER_1); //COST TO DESTROY = 0
    }

    @Test
    public void destroy_district_of_cost_0(){
        //List<DestructibleDistrict> destructibleDistricts1 = firstCity.districtsDestructibleBy(player1);
        player1.add(15);
        System.out.println("    City: " + textCity(player1));
        System.out.println("    Gold: " + player1.gold());
        System.out.println("  ------  DESTRUCTION CHURCH ------  ");
        firstCity.destroyDistrict(CHURCH_1);
        //List<DestructibleDistrict> destructibleDistricts2 = firstCity.districtsDestructibleBy(player1);
        //List<DestructibleDistrict> destructibleDistrictsExpected = null;
        //destructibleDistrictsExpected.append(CHURCH_1);
        System.out.println("    City: " + textCity(player1));
        System.out.println("    Gold: " + player1.gold());
        //assertThat(destructibleDistrictsExpected).isEqualTo(destructibleDistricts2);
    }

}