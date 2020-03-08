package com.github.sfragata.gameapi.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Unit test for Shoe class
 * @author Silvio Fragata
 *
 */
public class ShoeUnitTest {

    private static final int DECK_COUNT = 52;

    public ShoeUnitTest() {

        super();
    }

    @Test
    public void givenDeckWhenGetCardsThen52CardsReturned() {

        final Deck deck = new Deck();

        final Shoe shoe = new Shoe();

        shoe.addCards(deck);
        assertNotNull(shoe.getCards());
        assertEquals(DECK_COUNT, shoe.getCards().size());

    }

    @Test
    public void given2DecksWhenGetCardsThen104CardsReturned() {

        final Deck deck1 = new Deck();
        final Deck deck2 = new Deck();

        final Shoe shoe = new Shoe();

        shoe.addCards(deck1);
        shoe.addCards(deck2);

        assertNotNull(shoe.getCards());
        assertEquals(DECK_COUNT * 2, shoe.getCards().size());

    }
}
