package com.github.sfragata.gameapi.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a Deck of cards -> 52 Cards. 13 Clubs, Diamonds, Spades, and Hearts. (Suit and FaceValue Enums)
 * @author Silvio Fragata
 */
public class Deck {
    private final List<Card> cards = new ArrayList<>(52);

    /**
     * Default constructor
     */
    public Deck() {

        super();
        initCards();
    }

    public List<Card> getCards() {

        return Collections.unmodifiableList(this.cards);
    }

    private void initCards() {

        for (final Suit suit : Suit.values()) {
            for (final FaceValue faceValue : FaceValue.values()) {
                this.cards.add(new Card(suit, faceValue));
            }
        }

    }

}
