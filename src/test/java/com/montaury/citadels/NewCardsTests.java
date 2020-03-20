package com.montaury.citadels;


import com.montaury.citadels.district.Card;
import com.montaury.citadels.district.District;
import com.montaury.citadels.player.HumanController;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.Group;
import com.montaury.citadels.CardPile;
import com.montaury.citadels.round.action.ActionType;
import com.montaury.citadels.character.Character;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;


import io.vavr.collection.List;
import io.vavr.collection.Set;
import org.junit.Before;
import org.junit.Test;


import static com.montaury.citadels.district.Card.*;
import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

public class NewCardsTests {
    public Board board;
    public City firstCity;

    @Before
    public void setUp(){
        board = new Board();
        firstCity = new City(board);

    }

    @Test
    public void ParcCardTest() {
        Player player1 = new Player(null, 5, firstCity, null);
        Possession possession= new Possession(0,null);

        firstCity.buildDistrict(PARC); // pour la forme
        Group group = new Group(player1, Character.MAGICIAN);
        CardPile pioche = new CardPile(List.of(WATCHTOWER_1, WATCHTOWER_3, WATCHTOWER_2));

        ActionType.PICK_2_CARDS.getAction().executeAction(group, pioche, null, null);
    //ActionType.RECEIVE_2_COIN.getAction().executeAction(group, pioche, groups);
        assertThat(player1.getCards().size()).isEqualTo(2);
    }

    @Test
    public void HospiceCardTest() {
        Player player1 = new Player(null, 5, firstCity, null);
        Possession possession= new Possession(0,null);

        firstCity.buildDistrict(HOSPICE); // pour la forme
        Group group = new Group(player1, Character.MAGICIAN);

        ActionType.RECEIVE_1_COIN.getAction().executeAction(group, null, null, null);

        assertThat(player1.getGold()).isEqualTo(1);
    }
    }