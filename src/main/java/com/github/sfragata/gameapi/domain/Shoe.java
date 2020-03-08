package com.github.sfragata.gameapi.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a game deck (one or more decks)
 * @author Silvio Fragata
 */
public class Shoe {

    private final List<Card> cards = Collections.synchronizedList(new ArrayList<>());

    /**
     * Default constructor
     */
    public Shoe() {

        super();
    }

    /**
     * Method to add a Deck to the game deck (shoe)
     * @param deck to be added
     */
    public void addCards(
        final Deck deck) {

        this.cards.addAll(deck.getCards());

    }

    public List<Card> getCards() {

        return this.cards;
    }

}
