package com.github.sfragata.gameapi.exception;

/**
 * Exception to inform that the game doesn't exist
 * @author Silvio Fragata
 *
 */
public class GameNotFoundException
    extends Exception {

    private static final String GAME_NOT_FOUND = "Game %d not found";

    /**
     * Constructor
     * @param gameId the game id
     */
    public GameNotFoundException(final Integer gameId) {

        super(String.format(GAME_NOT_FOUND, gameId));
    }

}
