package com.github.sfragata.gameapi.domain;

/**
 * Enum to represent the face value of a card (without regarding its suit)
 * @author Silvio Fragata
 */
public enum FaceValue {
        ACE("A", 1),
        TWO("2", 2),
        THREE("3", 3),
        FOUR("4", 4),
        FIVE("5", 5),
        SIX("6", 6),
        SEVEN("7", 7),
        EIGHT("8", 8),
        NINE("9", 9),
        TEN("10", 10),
        JACK("J", 11),
        QUEEN("Q", 12),
        KING("K", 13);

    private String face;

    private Integer value;

    FaceValue(final String pFace, final Integer pValue) {

        this.face = pFace;
        this.value = pValue;
    }

    public String getFace() {

        return this.face;
    }

    public Integer getValue() {

        return this.value;
    }

}
