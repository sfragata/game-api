package com.github.sfragata.gameapi.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit test for Player class
 * @author Silvio Fragata
 *
 */
public class PlayerUnitTest {

    private static final int EQUALS = 0;

    private static final int MORE_THAN = 1;

    private static final int LESS_THAN = -1;

    public PlayerUnitTest() {

        super();
    }

    @Test
    public void givenTwolayersWithDifferentCardsWhenComparingToThenComparingByFaceValue() {

        final Player player1 = new Player(1);
        final Player player2 = new Player(2);

        player1.addCard(new Card(Suit.SPADES, FaceValue.ACE));

        player2.addCard(new Card(Suit.HEARTS, FaceValue.THREE));

        assertEquals(LESS_THAN, player1.compareTo(player2));
        assertEquals(MORE_THAN, player2.compareTo(player1));
        assertTrue(player1.getTotalValue() < player2.getTotalValue());

    }

    @Test
    public void givenTwoPlayersWithSameCardsWhenComparingToThenReturnEquals() {

        final Player player1 = new Player(1);
        final Player player2 = new Player(2);

        player1.addCard(new Card(Suit.SPADES, FaceValue.ACE));

        player2.addCard(new Card(Suit.SPADES, FaceValue.ACE));

        assertNotEquals(player1, player2);
        assertEquals(EQUALS, player1.compareTo(player2));

    }

}
