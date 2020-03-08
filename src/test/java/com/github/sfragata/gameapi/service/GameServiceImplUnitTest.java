package com.github.sfragata.gameapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.sfragata.gameapi.domain.CardCount;
import com.github.sfragata.gameapi.domain.Deck;
import com.github.sfragata.gameapi.domain.Game;
import com.github.sfragata.gameapi.domain.Player;
import com.github.sfragata.gameapi.domain.Suit;
import com.github.sfragata.gameapi.exception.GameAlreadyExistsException;
import com.github.sfragata.gameapi.exception.GameNotFoundException;
import com.github.sfragata.gameapi.exception.PlayerNotFoundException;
import com.github.sfragata.gameapi.helper.ListShuffleHelper;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplUnitTest {

    private static final int TOTAL_CARDS_BY_SUIT = 13;

    private static final int TOTAL_CARDS_DECK = 52;

    @Mock
    private PlayerService playerService;

    @Mock
    private ListShuffleHelper listShuffleHelper;

    @InjectMocks
    private final GameServiceImpl gameService = new GameServiceImpl();

    public GameServiceImplUnitTest() {

        super();
    }

    @Test
    void givenGameWhenCreateGameThenTheGameIsCreated()
        throws GameAlreadyExistsException,
        GameNotFoundException {

        // Given
        final Game game = createGame();
        // When
        // Then
        assertNotNull(this.gameService.getGame(game.getGameId()));
    }

    @Test
    void givenCreateTwoTimesTheSameGameWhenCreateGameThenGameAlreadyExistsExceptionIsThrown()
        throws GameAlreadyExistsException {

        // Given
        final Game game = createGame();
        // When
        // Then
        assertThrows(GameAlreadyExistsException.class, () -> this.gameService.createGame(game));
    }

    @Test
    void givenGameWhenDeleteGameThenTheGameIsDeleted()
        throws GameAlreadyExistsException,
        GameNotFoundException {

        // Given
        final Game game = createGame();
        // When
        this.gameService.deleteGame(game.getGameId());
        // Then
        assertThrows(GameNotFoundException.class, () -> this.gameService.getGame(game.getGameId()));
    }

    @Test
    void givenGameNotFoundWhenDeleteGameThenGameNotFoundExceptioIsThrown() {

        assertThrows(GameNotFoundException.class, () -> this.gameService.getGame(1));
    }

    @Test
    void givenGameWhenAddDeckThenDeckIsAdded()
        throws GameAlreadyExistsException,
        GameNotFoundException {

        // Given
        final Game game = createGame();
        // When
        this.gameService.addDeck(game.getGameId(), new Deck());

        // Then
        final Game gameReturned = this.gameService.getGame(game.getGameId());
        assertNotNull(gameReturned.getShoe());
        assertNotNull(gameReturned.getShoe().getCards());
        assertFalse(gameReturned.getShoe().getCards().isEmpty());

    }

    @Test
    void givenGameWhenAddPlayerThenPlayerIsAdded()
        throws GameAlreadyExistsException,
        GameNotFoundException {

        // Given
        final Game game = createGame();
        final Player player = createPlayer();
        // When
        this.gameService.addPlayer(game.getGameId(), player);

        // Then
        final Game gameReturned = this.gameService.getGame(game.getGameId());
        assertFalse(gameReturned.getPlayers().isEmpty());
        final Player playerReturned = gameReturned.getPlayers().iterator().next();
        assertEquals(player, playerReturned);

    }

    @Test
    void givenGameWhenRemovePlayerThenPlayerIsRemoved()
        throws GameAlreadyExistsException,
        GameNotFoundException,
        PlayerNotFoundException {

        // Given
        final Game game = createGame();
        final Player player = createPlayer();
        this.gameService.addPlayer(game.getGameId(), player);

        when(this.playerService.getPlayer(eq(player.getPlayerId()), anyList())).thenReturn(player);

        // When
        this.gameService.removePlayer(game.getGameId(), player.getPlayerId());

        // Then
        final Game gameReturned = this.gameService.getGame(game.getGameId());
        assertTrue(gameReturned.getPlayers().isEmpty());

        verify(this.playerService).getPlayer(eq(player.getPlayerId()), anyList());

    }

    @Test
    void givenGameWhenGetGameThenGameIsReturned()
        throws GameAlreadyExistsException,
        GameNotFoundException,
        PlayerNotFoundException {

        // Given
        final Game game = createGame();

        // When
        final Game gameReturned = this.gameService.getGame(game.getGameId());
        // Then
        assertNotNull(gameReturned);

    }

    @Test
    void givenGameAndPlayerWhenDealCardsThanPlayerReceiveCard()
        throws GameAlreadyExistsException,
        GameNotFoundException,
        PlayerNotFoundException {

        // Given
        final Game game = createGame();
        this.gameService.addDeck(game.getGameId(), new Deck());
        final Player player = createPlayer();
        this.gameService.addPlayer(game.getGameId(), player);

        when(this.playerService.getPlayer(eq(player.getPlayerId()), anyList())).thenReturn(player);
        // When
        final Game gameReturned = this.gameService.dealCards(game.getGameId(), player.getPlayerId());
        // Then
        assertNotNull(gameReturned);
        assertFalse(gameReturned.getPlayers().isEmpty());
        assertNotNull(gameReturned.getShoe());
        assertEquals(51, gameReturned.getShoe().getCards().size());
        final Player playerReturned = gameReturned.getPlayers().iterator().next();

        assertFalse(playerReturned.getCards().isEmpty());
        verify(this.playerService).getPlayer(eq(player.getPlayerId()), anyList());

    }

    @Test
    void givenGamePlayerAndNullShoeWhenDealCardsThanPlayerReceiveNothing()
        throws GameAlreadyExistsException,
        GameNotFoundException,
        PlayerNotFoundException {

        // Given
        final Game game = createGame();
        final Player player = createPlayer();
        this.gameService.addPlayer(game.getGameId(), player);

        when(this.playerService.getPlayer(eq(player.getPlayerId()), anyList())).thenReturn(player);
        // When
        final Game gameReturned = this.gameService.dealCards(game.getGameId(), player.getPlayerId());
        // Then
        assertNotNull(gameReturned);
        assertFalse(gameReturned.getPlayers().isEmpty());
        assertNull(gameReturned.getShoe());
        // assertEquals(51, gameReturned.getShoe().getCards().size());
        final Player playerReturned = gameReturned.getPlayers().iterator().next();

        assertTrue(playerReturned.getCards().isEmpty());
        verify(this.playerService).getPlayer(eq(player.getPlayerId()), anyList());

    }

    @Test
    void givenGameAndPlayerWhenListPlayersThanPlayersReturned()
        throws GameAlreadyExistsException,
        GameNotFoundException {

        // Given
        final Game game = createGame();
        final Player player = createPlayer();
        this.gameService.addPlayer(game.getGameId(), player);

        // When
        final List<Player> players = this.gameService.listPlayers(game.getGameId());
        // Then
        assertNotNull(players);
        assertFalse(players.isEmpty());

    }

    @Test
    void givenGameWhenListPlayersThanEmptyReturned()
        throws GameAlreadyExistsException,
        GameNotFoundException {

        // Given
        final Game game = createGame();

        // When
        final List<Player> players = this.gameService.listPlayers(game.getGameId());
        // Then
        assertNotNull(players);
        assertTrue(players.isEmpty());

    }

    @Test
    void givenGameAndCardsWhenFindCardsBySuitThanMapIsReturned()
        throws GameAlreadyExistsException,
        GameNotFoundException {

        // Given
        final Game game = createGame();
        this.gameService.addDeck(game.getGameId(), new Deck());
        // When
        final Map<Suit, Integer> cardsBySuit = this.gameService.findCardsBySuit(game.getGameId());
        // Then
        assertNotNull(cardsBySuit);
        assertFalse(cardsBySuit.isEmpty());
        cardsBySuit.entrySet().stream().forEach(entry -> {
            assertEquals(13, entry.getValue());
        });

    }

    @Test
    void givenGameAndPlayerCardsWhenFindCardsBySuitAfterCallDealCardsThanMapIsReturnedWithLessOneCount()
        throws GameAlreadyExistsException,
        GameNotFoundException,
        PlayerNotFoundException {

        // Given
        final Game game = createGame();
        this.gameService.addDeck(game.getGameId(), new Deck());
        final Player player = createPlayer();
        this.gameService.addPlayer(game.getGameId(), player);

        when(this.playerService.getPlayer(eq(player.getPlayerId()), anyList())).thenReturn(player);
        this.gameService.dealCards(game.getGameId(), player.getPlayerId());
        // When
        final Map<Suit, Integer> cardsBySuit = this.gameService.findCardsBySuit(game.getGameId());
        // Then
        assertNotNull(cardsBySuit);
        assertFalse(cardsBySuit.isEmpty());
        assertTrue(cardsBySuit.entrySet().stream().anyMatch(entry -> entry.getValue().equals(TOTAL_CARDS_BY_SUIT - 1)));

    }

    @Test
    void givenGameWithoutCardsWhenFindCardsBySuitThanMapEmptyIsReturned()
        throws GameAlreadyExistsException,
        GameNotFoundException {

        // Given
        final Game game = createGame();
        // When
        final Map<Suit, Integer> cardsBySuit = this.gameService.findCardsBySuit(game.getGameId());
        // Then
        assertNotNull(cardsBySuit);
        assertTrue(cardsBySuit.isEmpty());

    }

    @Test
    void givenGameAndCardsWhenFindRemaingCardsThanCardListIsReturned()
        throws GameAlreadyExistsException,
        GameNotFoundException {

        // Given
        final Game game = createGame();
        this.gameService.addDeck(game.getGameId(), new Deck());
        // When
        final List<CardCount> remaingCards = this.gameService.findRemaingCards(game.getGameId());
        // Then
        assertNotNull(remaingCards);
        assertFalse(remaingCards.isEmpty());
        assertEquals(TOTAL_CARDS_DECK, remaingCards.size());
        remaingCards.forEach(entry -> {
            assertEquals(1, entry.getCount().intValue());
        });

    }

    @Test
    void givenGameAndCardsWhenFindRemaingCardsAfterCallDealCardsThanCardListIsReturnedWithLessOneCount()
        throws GameAlreadyExistsException,
        GameNotFoundException,
        PlayerNotFoundException {

        // Given
        final Game game = createGame();
        this.gameService.addDeck(game.getGameId(), new Deck());
        final Player player = createPlayer();
        this.gameService.addPlayer(game.getGameId(), player);

        when(this.playerService.getPlayer(eq(player.getPlayerId()), anyList())).thenReturn(player);
        this.gameService.dealCards(game.getGameId(), player.getPlayerId());
        // When
        final List<CardCount> remaingCards = this.gameService.findRemaingCards(game.getGameId());
        // Then
        assertNotNull(remaingCards);
        assertFalse(remaingCards.isEmpty());
        assertEquals(TOTAL_CARDS_DECK - 1, remaingCards.size());

    }

    @Test
    void givenGameWithoutCardsWhenFindRemaingCardsThanCardListEmptyIsReturned()
        throws GameAlreadyExistsException,
        GameNotFoundException {

        // Given
        final Game game = createGame();
        // When
        final List<CardCount> remaingCards = this.gameService.findRemaingCards(game.getGameId());
        // Then
        assertNotNull(remaingCards);
        assertTrue(remaingCards.isEmpty());

    }

    @Test
    void givenGameAndCardsWhenShuffleThenListShuffleHelperIsCalled()
        throws GameAlreadyExistsException,
        GameNotFoundException {

        // Given
        final Game game = createGame();
        this.gameService.addDeck(game.getGameId(), new Deck());
        // When
        this.gameService.shuffle(game.getGameId());
        // Then
        verify(this.listShuffleHelper).shuffle(anyList());

    }

    @Test
    void givenGameWithouCardsWhenShuffleThenListShuffleHelperIsntCalled()
        throws GameAlreadyExistsException,
        GameNotFoundException {

        // Given
        final Game game = createGame();
        // When
        this.gameService.shuffle(game.getGameId());
        // Then
        verify(this.listShuffleHelper, never()).shuffle(anyList());

    }

    private Player createPlayer() {

        return new Player(1);
    }

    private Game createGame()
        throws GameAlreadyExistsException {

        final Game game = new Game();
        game.setGameId(1);
        this.gameService.createGame(game);
        return game;
    }

}
