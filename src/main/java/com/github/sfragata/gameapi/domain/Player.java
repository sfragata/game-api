package com.github.sfragata.gameapi.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class representing a player (for this assignment the only the playerId will be used)
 * It stores the list of cards in its "hand" and the total value of cards
 * @author Silvio Fragata
 */
public class Player
    implements Comparable<Player> {

    private final Integer playerId;

    private final List<Card> cards = new ArrayList<>();

    private int totalValue;

    /**
     * Constructor
     * @param pPlayerId the player Id
     */
    public Player(final Integer pPlayerId) {

        super();
        this.playerId = pPlayerId;
        this.totalValue = 0;
    }

    /**
     * This method will store the new card added and calculates the total value
     * @param pCard the card to be added
     */
    public void addCard(
        final Card pCard) {

        this.cards.add(pCard);
        this.totalValue += pCard.getFaceValue().getValue();
    }

    public Integer getPlayerId() {

        return this.playerId;
    }

    public List<Card> getCards() {

        return this.cards;
    }

    public int getTotalValue() {

        return this.totalValue;
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.cards, this.playerId);
    }

    @Override
    public boolean equals(
        final Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        return Objects.equals(this.cards, other.cards) && Objects.equals(this.playerId, other.playerId);
    }

    @Override
    public int compareTo(
        final Player pPlayer) {

        return Integer.valueOf(this.totalValue).compareTo(pPlayer.getTotalValue());
    }

}
