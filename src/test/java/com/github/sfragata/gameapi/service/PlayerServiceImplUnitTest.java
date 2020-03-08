package com.github.sfragata.gameapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.sfragata.gameapi.domain.Player;
import com.github.sfragata.gameapi.exception.PlayerNotFoundException;

/**
 * Unit test for PlayerService class
 * @author Silvio Fragata
 *
 */
@SpringBootTest
public class PlayerServiceImplUnitTest {

    private static final int PLAYER_ID = 1;

    private static List<Player> players = new ArrayList<>();

    @Autowired
    private PlayerService playerService;

    public PlayerServiceImplUnitTest() {

        super();
        players.add(new Player(1));
        players.add(new Player(2));
    }

    @Test
    void givenExistantPlayerIdWhenFindByIdThenPlayerIsReturned()
        throws PlayerNotFoundException {

        final Player player = this.playerService.getPlayer(PLAYER_ID, players);

        assertNotNull(player);
        assertEquals(PLAYER_ID, player.getPlayerId());
    }

    @Test
    void givenValidPlayerIdAndNullListWhenFindByIdThenPlayerNotFoundExceptionIsThrown() {

        assertThrows(PlayerNotFoundException.class, () -> this.playerService.getPlayer(PLAYER_ID, null));

    }

    @Test
    void givenValidPlayerIdAndEmptyListWhenFindByIdThenPlayerNotFoundExceptionIsThrown() {

        assertThrows(PlayerNotFoundException.class,
            () -> this.playerService.getPlayer(PLAYER_ID, Collections.emptyList()));

    }

    @Test
    void givenNonExistantPlayerIdWhenFindByIdThenPlayerNotFoundExceptionIsThrown() {

        assertThrows(PlayerNotFoundException.class, () -> this.playerService.getPlayer(999, players));

    }

}
