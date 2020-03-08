package com.github.sfragata.gameapi.exception;

/**
 * Exception to inform that the game already exists
 * @author Silvio Fragata
 *
 */
public class GameAlreadyExistsException
    extends Exception {

    private static final String GAME_ALREADY_EXISTS = "Game %d already exists";

    /**
     * Constructor
     * @param gameId the game id
     */
    public GameAlreadyExistsException(final Integer gameId) {

        super(String.format(GAME_ALREADY_EXISTS, gameId));
    }

}
