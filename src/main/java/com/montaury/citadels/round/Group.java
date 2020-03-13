package com.montaury.citadels.round;

import com.montaury.citadels.character.Character;
import com.montaury.citadels.player.Player;
import com.montaury.citadels.round.action.ActionType;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import io.vavr.collection.List;

public class Group {

    public Group(Player player, Character character) {
        this.player = player;
        this.character = character;
    }

    public Player getPlayer() {
        return player;
    }

    public Character getCharacter() {
        return character;
    }

    public boolean isNot(Character character) {
        return this.character != character;
    }

    public void murder() {
        this.murdered = true;
    }

    public boolean isMurdered() {
        return murdered;
    }

    public void stolenBy(Player player) {
        stolenBy = Option.of(player);
    }

    public Option<Player> thief() {
        return stolenBy;
    }

    private final Player player;
    public final Character character;
    private boolean murdered;
    private Option<Player> stolenBy = Option.none();
    public static final Set<ActionType> OPTIONAL_ACTIONS = HashSet.of(ActionType.BUILD_DISTRICT, ActionType.END_ROUND);
}
