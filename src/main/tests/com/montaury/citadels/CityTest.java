package com.montaury.citadels;


import com.montaury.citadels.player.HumanController;
import com.montaury.citadels.player.Player;
import org.junit.jupiter.api.Test;

class CityTest {
    @Test
    public void VictoireAve5TypesDifférents(){
        Board board = new Board();
        City soumoulou = new City(board);

        Player p = new Player("guigui", 15, soumoulou, new HumanController());

        soumoulou.buildDistrict(     );
        soumoulou.buildDistrict(     );
        soumoulou.buildDistrict();
        soumoulou.buildDistrict();
        soumoulou.buildDistrict();
        soumoulou.buildDistrict();
        soumoulou.buildDistrict();

    }


}