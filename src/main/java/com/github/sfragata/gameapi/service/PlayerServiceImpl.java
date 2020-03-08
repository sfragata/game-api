package com.github.sfragata.gameapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.sfragata.gameapi.domain.Player;
import com.github.sfragata.gameapi.exception.PlayerNotFoundException;

/**
 * Class that do operations for players
 * @author Silvio Fragata
 */
@Service
public class PlayerServiceImpl
    implements PlayerService {

    /**
     * Default constructor
     */
    public PlayerServiceImpl() {

        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getPlayer(
        final Integer playerId,
        final List<Player> players)
        throws PlayerNotFoundException {

        if (players == null || players.isEmpty()) {
            throw new PlayerNotFoundException(playerId);
        }

        return players.stream().filter(player -> player.getPlayerId().equals(playerId)).findFirst()
            .orElseThrow(() -> new PlayerNotFoundException(playerId));

    }

}
