/**
 *
 */
package com.github.sfragata.gameapi.service;

import java.util.List;

import com.github.sfragata.gameapi.domain.Player;
import com.github.sfragata.gameapi.exception.PlayerNotFoundException;

/**
 * Interface for the player service
 * @author Silvio Fragata
 *
 */
public interface PlayerService {

    /**
     * method to return a player from the list of players
     * @param playerId the player id
     * @param players list of players
     * @return the Player object
     * @throws PlayerNotFoundException if the player doesn't exist
     */
    Player getPlayer(
        final Integer playerId,
        List<Player> players)
        throws PlayerNotFoundException;
}
