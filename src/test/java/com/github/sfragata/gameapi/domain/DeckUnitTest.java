/**
 *
 */
package com.github.sfragata.gameapi.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.github.sfragata.gameapi.domain.Deck;

/**
 * Unit test for Deck class
 * @author Silvio Fragata
 *
 */
public class DeckUnitTest {

    private static final int DECK_COUNT = 52;

    public DeckUnitTest() {

    }

    @Test
    public void givenDeckWhenGetCardsThen52CardsReturned() {

        final Deck deck = new Deck();

        assertNotNull(deck.getCards());
        assertEquals(DECK_COUNT, deck.getCards().size());

    }

}
