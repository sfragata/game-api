package com.github.sfragata.gameapi.exception;

/**
 * Exception to inform that the player doesn't exist
 * @author Silvio Fragata
 *
 */
public class PlayerNotFoundException
    extends Exception {

    private static final String PLAYER_NOT_FOUND = "Player %d not found";

    /**
     * Constructor
     * @param playerId the player id
     */
    public PlayerNotFoundException(final Integer playerId) {

        super(String.format(PLAYER_NOT_FOUND, playerId));
    }

}
