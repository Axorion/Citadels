package com.montaury.citadels.round.action;

public enum ActionType {
    BUILD_DISTRICT("Build district", new BuildGetDistrictAction()),
    DESTROY_DISTRICT("Destroy district", new DestroyGetDistrictAction()),
    DISCARD_CARD_FOR_2_COINS("Discard card for 2 coins", new DiscardGetCardFor2CoinsAction()),
    DRAW_2_CARDS_KEEP_1("Draw 2 cards and keep 1", new Draw2GetCardsKeep1Action()),
    DRAW_3_CARDS_FOR_2_COINS("Draw 3 cards for 2 coins", new Draw3GetCardsFor2CoinsAction()),
    DRAW_3_CARDS_KEEP_1("Draw 3 cards and keep 1", new Draw3GetCardsKeep1Action()),
    END_ROUND("End round", new EndRoundAction()),
    EXCHANGE_CARDS_WITH_OTHER_PLAYER("Exchange cards with other player", new ExchangeGetCardsWithOtherGetPlayerAction()),
    EXCHANGE_CARDS_WITH_PILE("Exchange cards with pile", new ExchangeGetCardsWithPileAction()),
    KILL_ACTION("Kill", new KillAction()),
    PICK_2_CARDS("Pick 2 cards", new Pick2GetCardsAction()),
    RECEIVE_1_COIN("Receive 1 coin", new Receive1CoinAction()),
    RECEIVE_2_COIN("Receive 2 coin", new Receive2CoinsAction()),
    RECEIVE_INCOME("Receive income", new ReceiveIncomeAction()),
    ROB_ACTION("Rob", new RobAction()),
    PICK_4_CARDS("Navigateur", new Pick4GetCardsAction()),
    RECEIVE_4_COIN("Navigateur", new Receive4CoinsAction());




    ActionType(String name, Action action) {
        this.name = name;
        this.action = action;
    }

    public Action getAction() { return this.action; }
    public String getName() { return this.name; }

    private final Action action;
    private final String name;
}
