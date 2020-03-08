package com.github.sfragata.gameapi.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sfragata.gameapi.domain.Deck;
import com.github.sfragata.gameapi.domain.Game;
import com.github.sfragata.gameapi.domain.Player;
import com.github.sfragata.gameapi.exception.GameAlreadyExistsException;
import com.github.sfragata.gameapi.exception.GameNotFoundException;
import com.github.sfragata.gameapi.exception.PlayerNotFoundException;
import com.github.sfragata.gameapi.service.GameService;
import com.github.sfragata.gameapi.service.PlayerService;

/**
 * Controller class serving all endpoints for the application
 * @author Silvio Fragata
 *
 */
@RestController
@RequestMapping("/gameapi")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    private final AtomicInteger atomicInteger = new AtomicInteger(1);

    public GameController() {

        super();
    }

    /**
     * POST method to create a Game
     * @param gameId the game id
     * @return HTTP status 201 if OK or HTTP status 400 with a message with the error
     */
    @PostMapping("/{gameId}")
    public ResponseEntity<String> createGame(
        @PathVariable final Integer gameId) {

        try {
            final var game = new Game();
            game.setGameId(gameId);
            this.gameService.createGame(game);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (final GameAlreadyExistsException gameAlreadyExistsException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gameAlreadyExistsException.getMessage());
        }

    }

    /**
     * DELETE method to delete a Game
     * @param gameId the game id
     * @return HTTP status 204 if OK or HTTP status 404 if the game passed is not found (with message error)
     */
    @DeleteMapping("/{gameId}")
    public ResponseEntity<String> deleteGame(
        @PathVariable final Integer gameId) {

        try {
            this.gameService.deleteGame(gameId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (final GameNotFoundException gameNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gameNotFoundException.getMessage());
        }

    }

    /**
     * POST method to create a Deck and add it to the Game
     * @param gameId the game id
     * @return HTTP status 201 if OK or HTTP status 404 if the game passed is not found (with message error)
     */
    @PostMapping("/{gameId}/deck")
    public ResponseEntity<String> addDeck(
        @PathVariable final Integer gameId) {

        try {
            this.gameService.addDeck(gameId, new Deck());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (final GameNotFoundException gameNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gameNotFoundException.getMessage());
        }

    }

    /**
     * POST method to create a Player and add it to the Game
     * @param gameId the game id
     * @return HTTP status 200 and the object Player if OK or HTTP status 404 if the game passed is not found (with message error)
     */
    @PostMapping("/{gameId}/player")
    public ResponseEntity<?> addPlayer(
        @PathVariable final Integer gameId) {

        try {
            final var player = new Player(this.atomicInteger.getAndIncrement());
            this.gameService.addPlayer(gameId, player);
            return ResponseEntity.status(HttpStatus.OK).body(player);
        } catch (final GameNotFoundException gameNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gameNotFoundException.getMessage());
        }

    }

    /**
     * DELETE method to delete a Player from the Game
     * @param gameId the game id
     * @param playerId the player id
     * @return HTTP status 204 if OK or HTTP status 404 if the game or the player passed is not found (with message error)
     */
    @DeleteMapping("/{gameId}/player/{playerId}")
    public ResponseEntity<String> deletePlayer(
        @PathVariable final Integer gameId,
        @PathVariable final Integer playerId) {

        try {
            this.gameService.removePlayer(gameId, playerId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (final GameNotFoundException | PlayerNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    /**
     * POST method to deal a card to a Player from the Game
     * @param gameId the game id
     * @param playerId the player id
     * @return HTTP status 200 with the Game object if OK or HTTP status 404 if the game or the player passed is not found (with message error)
     */
    @PostMapping("/{gameId}/player/{playerId}/deal")
    public ResponseEntity<?> dealCards(
        @PathVariable final Integer gameId,
        @PathVariable final Integer playerId) {

        try {
            final Game game = this.gameService.dealCards(gameId, playerId);
            return ResponseEntity.status(HttpStatus.OK).body(game);
        } catch (final GameNotFoundException | PlayerNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    /**
     * GET method to get a Player from the Game
     * @param gameId the game id
     * @param playerId the player id
     * @return HTTP status 200 with the Player object if OK or HTTP status 404 if the game or the player passed is not found (with message error)
     */
    @GetMapping("/{gameId}/player/{playerId}")
    public ResponseEntity<?> getPlayer(
        @PathVariable final Integer gameId,
        @PathVariable final Integer playerId) {

        try {
            final Game game = this.gameService.getGame(gameId);
            final Player player = this.playerService.getPlayer(playerId, game.getPlayers());
            return ResponseEntity.status(HttpStatus.OK).body(player);
        } catch (final GameNotFoundException | PlayerNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    /**
     * GET method to get all Players from the Game
     * @param gameId the game id
     * @return HTTP status 200 with the list of Players if OK or HTTP status 404 if the game or the player passed is not found (with message error)
     */
    @GetMapping("/{gameId}/players")
    public ResponseEntity<?> listPlayer(
        @PathVariable final Integer gameId) {

        try {
            final var listPlayers = this.gameService.listPlayers(gameId);
            Collections.sort(listPlayers, Comparator.reverseOrder());
            return ResponseEntity.status(HttpStatus.OK).body(listPlayers);
        } catch (final GameNotFoundException gameNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gameNotFoundException.getMessage());
        }

    }

    /**
     * GET method to list the cards undealt grouped by Suit in the game
     * @param gameId the game id
     * @return HTTP status 200 with the list of cards if OK or HTTP status 404 if the game passed is not found (with message error)
     */
    @GetMapping("/{gameId}/deck")
    public ResponseEntity<?> listCardsUndealtBySuit(
        @PathVariable final Integer gameId) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.gameService.findCardsBySuit(gameId));
        } catch (final GameNotFoundException gameNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gameNotFoundException.getMessage());
        }

    }

    /**
     * GET method to list remaining cards in the game
     * @param gameId the game id
     * @return HTTP status 200 with the list of remaining cards if OK or HTTP status 404 if the game passed is not found (with message error)
     */
    @GetMapping("/{gameId}/deck/cards")
    public ResponseEntity<?> listRemaingCards(
        @PathVariable final Integer gameId) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.gameService.findRemaingCards(gameId));
        } catch (final GameNotFoundException gameNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gameNotFoundException.getMessage());
        }

    }

    /**
     * POST method to shuffle all cards in the game
     * @param gameId the game id
     * @return HTTP status 204 if OK or HTTP status 404 if the game passed is not found (with message error)
     */
    @PostMapping("/{gameId}/shuffle")
    public ResponseEntity<?> shuffle(
        @PathVariable final Integer gameId) {

        try {
            this.gameService.shuffle(gameId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (final GameNotFoundException gameNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gameNotFoundException.getMessage());
        }

    }

}
