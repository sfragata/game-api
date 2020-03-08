/**
 *
 */
package com.github.sfragata.gameapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.github.sfragata.gameapi.domain.Card;
import com.github.sfragata.gameapi.domain.CardCount;
import com.github.sfragata.gameapi.domain.Deck;
import com.github.sfragata.gameapi.domain.FaceValue;
import com.github.sfragata.gameapi.domain.Game;
import com.github.sfragata.gameapi.domain.Player;
import com.github.sfragata.gameapi.domain.Suit;
import com.github.sfragata.gameapi.exception.GameAlreadyExistsException;
import com.github.sfragata.gameapi.exception.GameNotFoundException;
import com.github.sfragata.gameapi.exception.PlayerNotFoundException;
import com.github.sfragata.gameapi.service.GameService;
import com.github.sfragata.gameapi.service.PlayerService;

/**
 * Unit test for GameController class
 * @author Silvio Fragata
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerUnitTest {

    private static final String GAME_1_ALREADY_EXISTS = "Game 1 already exists";

    private static final String GAME_1_NOT_FOUND = "Game 1 not found";

    private static final String PLAYER_1_NOT_FOUND = "Player 1 not found";

    private static final String BASE_PATH = "/gameapi";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private PlayerService playerService;

    public GameControllerUnitTest() {

        super();
    }

    @Test
    public void createGameReturnsSuccessful()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.post(BASE_PATH + "/1");
        doNothing().when(this.gameService).createGame(any(Game.class));
        this.mvc.perform(accept).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void createGameReturnGameAlreadyExists()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.post(BASE_PATH + "/1");
        doThrow(new GameAlreadyExistsException(1)).when(this.gameService).createGame(any(Game.class));

        this.mvc.perform(accept).andExpect(status().is4xxClientError())
            .andExpect(content().string(GAME_1_ALREADY_EXISTS));
    }

    @Test
    public void deleteGameReturnsSuccessful()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.delete(BASE_PATH + "/1");
        doNothing().when(this.gameService).deleteGame(anyInt());
        this.mvc.perform(accept).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void deleteGameReturnGameNotFound()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.delete(BASE_PATH + "/1");
        doThrow(new GameNotFoundException(1)).when(this.gameService).deleteGame(anyInt());
        this.mvc.perform(accept).andExpect(status().is4xxClientError()).andExpect(content().string(GAME_1_NOT_FOUND));
    }

    @Test
    public void addDeckReturnsSuccessful()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.post(BASE_PATH + "/1/deck");
        doNothing().when(this.gameService).addDeck(anyInt(), any(Deck.class));
        this.mvc.perform(accept).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void addDeckReturnGameNotFound()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.post(BASE_PATH + "/1/deck");
        doThrow(new GameNotFoundException(1)).when(this.gameService).addDeck(anyInt(), any(Deck.class));
        this.mvc.perform(accept).andExpect(status().is4xxClientError()).andExpect(content().string(GAME_1_NOT_FOUND));
    }

    @Test
    public void addPlayerReturnsSuccessful()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.post(BASE_PATH + "/1/player");
        doNothing().when(this.gameService).addPlayer(anyInt(), any(Player.class));
        this.mvc.perform(accept).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void addPlayerReturnGameNotFound()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.post(BASE_PATH + "/1/player");
        doThrow(new GameNotFoundException(1)).when(this.gameService).addPlayer(anyInt(), any(Player.class));
        this.mvc.perform(accept).andExpect(status().is4xxClientError()).andExpect(content().string(GAME_1_NOT_FOUND));
    }

    @Test
    public void deletePlayerReturnsSuccessful()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.delete(BASE_PATH + "/1/player/1");
        doNothing().when(this.gameService).removePlayer(anyInt(), anyInt());
        this.mvc.perform(accept).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void deletePlayerReturnsGameNotFound()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.delete(BASE_PATH + "/1/player/1");
        doThrow(new GameNotFoundException(1)).when(this.gameService).removePlayer(anyInt(), anyInt());
        this.mvc.perform(accept).andExpect(status().is4xxClientError()).andExpect(content().string(GAME_1_NOT_FOUND));
    }

    @Test
    public void deletePlayerReturnsPlayerNotFound()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.delete(BASE_PATH + "/1/player/1");
        doThrow(new PlayerNotFoundException(1)).when(this.gameService).removePlayer(anyInt(), anyInt());
        this.mvc.perform(accept).andExpect(status().is4xxClientError()).andExpect(content().string(PLAYER_1_NOT_FOUND));
    }

    @Test
    public void dealCardsReturnsSuccessful()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.post(BASE_PATH + "/1/player/1/deal");
        final Game game = new Game();
        game.setGameId(1);
        when(this.gameService.dealCards(anyInt(), anyInt())).thenReturn(game);

        this.mvc.perform(accept).andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.gameId").value(1));
    }

    @Test
    public void dealCardsReturnGameNotFound()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.post(BASE_PATH + "/1/player/1/deal");
        when(this.gameService.dealCards(anyInt(), anyInt())).thenThrow(new GameNotFoundException(1));

        this.mvc.perform(accept).andExpect(status().is4xxClientError()).andExpect(content().string(GAME_1_NOT_FOUND));
    }

    @Test
    public void dealCardsReturnlayerNotFound()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.post(BASE_PATH + "/1/player/1/deal");
        when(this.gameService.dealCards(anyInt(), anyInt())).thenThrow(new PlayerNotFoundException(1));
        this.mvc.perform(accept).andExpect(status().is4xxClientError()).andExpect(content().string(PLAYER_1_NOT_FOUND));
    }

    @Test
    public void getPlayerReturnsSuccessful()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.get(BASE_PATH + "/1/player/1");
        final Game game = new Game();
        game.setGameId(1);
        final Player player = new Player(1);
        when(this.gameService.getGame(anyInt())).thenReturn(game);
        when(this.playerService.getPlayer(anyInt(), anyList())).thenReturn(player);

        this.mvc.perform(accept).andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.playerId").value(1));
    }

    @Test
    public void getPlayerReturnGameNotFound()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.get(BASE_PATH + "/1/player/1");
        when(this.gameService.getGame(anyInt())).thenThrow(new GameNotFoundException(1));

        this.mvc.perform(accept).andExpect(status().is4xxClientError()).andExpect(content().string(GAME_1_NOT_FOUND));
        verify(this.playerService, never()).getPlayer(anyInt(), anyList());
    }

    @Test
    public void getPlayerReturnPlayerNotFound()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.get(BASE_PATH + "/1/player/1");
        final Game game = new Game();
        game.setGameId(1);
        when(this.gameService.getGame(anyInt())).thenReturn(game);
        when(this.playerService.getPlayer(anyInt(), anyList())).thenThrow(new PlayerNotFoundException(1));

        this.mvc.perform(accept).andExpect(status().is4xxClientError()).andExpect(content().string(PLAYER_1_NOT_FOUND));
    }

    @Test
    public void listPlayersReturnsSuccessful()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.get(BASE_PATH + "/1/players");
        final Player player = new Player(1);
        final List<Player> players = new ArrayList<>();
        players.add(player);
        when(this.gameService.listPlayers(anyInt())).thenReturn(players);

        this.mvc.perform(accept).andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$..playerId").value(1));
    }

    @Test
    public void listPlayersReturnGameNotFound()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.get(BASE_PATH + "/1/players");
        when(this.gameService.listPlayers(anyInt())).thenThrow(new GameNotFoundException(1));

        this.mvc.perform(accept).andExpect(status().is4xxClientError()).andExpect(content().string(GAME_1_NOT_FOUND));
    }

    @Test
    public void listCardBySuitReturnsSuccessful()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.get(BASE_PATH + "/1/deck");
        final Map<Suit, Integer> map = new HashMap<>();
        map.put(Suit.SPADES, 1);
        when(this.gameService.findCardsBySuit(anyInt())).thenReturn(map);

        this.mvc.perform(accept).andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.SPADES").value(1));
    }

    @Test
    public void listCardBySuitReturnGameNotFound()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.get(BASE_PATH + "/1/deck");
        when(this.gameService.findCardsBySuit(anyInt())).thenThrow(new GameNotFoundException(1));

        this.mvc.perform(accept).andExpect(status().is4xxClientError()).andExpect(content().string(GAME_1_NOT_FOUND));
    }

    @Test
    public void listRemainingCardsReturnsSuccessful()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.get(BASE_PATH + "/1/deck/cards");

        final List<CardCount> cardCounts = new ArrayList<>();
        final CardCount cardCount = new CardCount();
        cardCount.setCard(new Card(Suit.SPADES, FaceValue.ACE));
        cardCount.getCount().incrementAndGet();
        cardCounts.add(cardCount);

        when(this.gameService.findRemaingCards(anyInt())).thenReturn(cardCounts);

        this.mvc.perform(accept).andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$..suit").value("SPADES"))
            .andExpect(jsonPath("$..count").value(1)).andExpect(jsonPath("$..faceValue").value("ACE"));
    }

    @Test
    public void listRemainingCardsReturnGameNotFound()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.get(BASE_PATH + "/1/deck/cards");

        when(this.gameService.findRemaingCards(anyInt())).thenThrow(new GameNotFoundException(1));

        this.mvc.perform(accept).andExpect(status().is4xxClientError()).andExpect(content().string(GAME_1_NOT_FOUND));
    }

    @Test
    public void shuffleCardsReturnsSuccessful()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.post(BASE_PATH + "/1/shuffle");

        doNothing().when(this.gameService).shuffle(anyInt());

        this.mvc.perform(accept).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shuffleCardsReturnGameNotFound()
        throws Exception {

        final MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.post(BASE_PATH + "/1/shuffle");

        doThrow(new GameNotFoundException(1)).when(this.gameService).shuffle(anyInt());
        this.mvc.perform(accept).andExpect(status().is4xxClientError()).andExpect(content().string(GAME_1_NOT_FOUND));
    }

}
