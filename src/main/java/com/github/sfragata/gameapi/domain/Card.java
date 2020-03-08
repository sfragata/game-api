package com.github.sfragata.gameapi.domain;

import java.util.Comparator;
import java.util.Objects;

/**
 * Domain class representing a Card
 * The information stored are: The Suit of the card and the Face with value of card
 * @author Silvio Fragata
 */
public class Card
    implements Comparable<Card> {

    private Suit suit;

    private FaceValue faceValue;

    /**
     * Constructor
     * @param suit the Suit
     * @param faceValue the face value
     */
    public Card(final Suit suit, final FaceValue faceValue) {

        super();
        this.suit = suit;
        this.faceValue = faceValue;
    }

    public Suit getSuit() {

        return this.suit;
    }

    public void setSuit(
        final Suit suit) {

        this.suit = suit;
    }

    public FaceValue getFaceValue() {

        return this.faceValue;
    }

    public void setFaceValue(
        final FaceValue faceValue) {

        this.faceValue = faceValue;
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.faceValue, this.suit);
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
        final Card other = (Card) obj;
        return this.faceValue == other.faceValue && this.suit == other.suit;
    }

    @Override
    public String toString() {

        return String.format("%s of %s", this.faceValue.getFace(), this.suit);
    }

    /**
     * method to compare two objects of the same type
     * The Suit will be sort as: HEARTS ,SPADES, CLUBS AND DIAMONDS and the FaceValue will be sorted reversed by value
     */
    @Override
    public int compareTo(
        final Card pCard) {

        return Comparator.comparing(Card::getSuit)
            .thenComparing(card -> card.getFaceValue().getValue(), Comparator.reverseOrder()).compare(this, pCard);

    }

}
