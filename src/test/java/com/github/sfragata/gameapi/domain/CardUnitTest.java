/**
 *
 */
package com.github.sfragata.gameapi.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.github.sfragata.gameapi.domain.Card;
import com.github.sfragata.gameapi.domain.FaceValue;
import com.github.sfragata.gameapi.domain.Suit;

/**
 * Unit test for Card class
 * @author Silvio Fragata
 *
 */
public class CardUnitTest {

    private static final int EQUALS = 0;

    private static final int MORE_THAN = 1;

    private static final int LESS_THAN = -1;

    public CardUnitTest() {

        super();
    }

    @Test
    public void givenTwoCardsOfTheSameSuitWhenComparingToThenComparingByFaceValueReversed() {

        final Card aceSpades = new Card(Suit.SPADES, FaceValue.ACE);

        final Card threeDiamonds = new Card(Suit.SPADES, FaceValue.THREE);

        assertTrue(aceSpades.compareTo(threeDiamonds) >= MORE_THAN);
        assertTrue(threeDiamonds.compareTo(aceSpades) <= LESS_THAN);

    }

    @Test
    public void givenSameCardsWhenComparingToThenReturnEquals() {

        final Card aceSpades = new Card(Suit.SPADES, FaceValue.ACE);

        final Card aceSpades2 = new Card(Suit.SPADES, FaceValue.ACE);

        assertEquals(EQUALS, aceSpades.compareTo(aceSpades2));
        assertEquals(0, aceSpades2.compareTo(aceSpades));
        assertEquals(aceSpades, aceSpades2);

    }

    @Test
    public void givenHeartsAndSpadesWhenComparingToThenReturnHeartsFirst() {

        final Card aceHearts = new Card(Suit.HEARTS, FaceValue.ACE);

        final Card threeSpades = new Card(Suit.SPADES, FaceValue.THREE);

        // HEARTS comes first than SPADES

        assertTrue(aceHearts.compareTo(threeSpades) <= LESS_THAN);
        assertTrue(threeSpades.compareTo(aceHearts) >= MORE_THAN);

    }

    @Test
    public void givenHeartsAndClubsWhenComparingToThenReturnHeartsFirst() {

        final Card aceHearts = new Card(Suit.HEARTS, FaceValue.ACE);

        final Card threeClubs = new Card(Suit.CLUBS, FaceValue.THREE);

        // HEARTS comes first than CLUBS

        assertTrue(aceHearts.compareTo(threeClubs) <= LESS_THAN);
        assertTrue(threeClubs.compareTo(aceHearts) >= MORE_THAN);

    }

    @Test
    public void givenHeartsAndDiamondsWhenComparingToThenReturnHeartsFirst() {

        final Card aceHearts = new Card(Suit.HEARTS, FaceValue.ACE);

        final Card threeDiamonds = new Card(Suit.DIAMONDS, FaceValue.THREE);

        // HEARTS comes first than DIAMONDS

        assertTrue(aceHearts.compareTo(threeDiamonds) <= LESS_THAN);
        assertTrue(threeDiamonds.compareTo(aceHearts) >= MORE_THAN);

    }

    @Test
    public void givenSpadesAndClubsWhenComparingToThenReturnSpadesFirst() {

        final Card aceSpades = new Card(Suit.SPADES, FaceValue.ACE);

        final Card threeClubs = new Card(Suit.CLUBS, FaceValue.THREE);

        // SPADES comes first than CLUBS
        assertTrue(aceSpades.compareTo(threeClubs) <= LESS_THAN);
        assertTrue(threeClubs.compareTo(aceSpades) >= MORE_THAN);

    }

    @Test
    public void givenSpadesAndDiamondsWhenComparingToThenReturnSpadesFirst() {

        final Card aceSpades = new Card(Suit.SPADES, FaceValue.ACE);

        final Card threeDiamonds = new Card(Suit.DIAMONDS, FaceValue.THREE);

        // SPADES comes first than DIAMONDS

        assertTrue(aceSpades.compareTo(threeDiamonds) <= LESS_THAN);
        assertTrue(threeDiamonds.compareTo(aceSpades) >= MORE_THAN);

    }

    @Test
    public void givenClubsAndDiamondsWhenComparingToThenReturnClubsFirst() {

        final Card aceClubs = new Card(Suit.CLUBS, FaceValue.ACE);

        final Card threeDiamonds = new Card(Suit.DIAMONDS, FaceValue.THREE);

        // CLUBS comes first than DIAMONDS

        assertTrue(aceClubs.compareTo(threeDiamonds) <= LESS_THAN);
        assertTrue(threeDiamonds.compareTo(aceClubs) >= MORE_THAN);

    }

}
