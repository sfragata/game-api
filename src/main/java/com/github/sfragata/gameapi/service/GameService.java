package com.github.sfragata.gameapi.service;

import java.util.List;
import java.util.Map;

import com.github.sfragata.gameapi.domain.CardCount;
import com.github.sfragata.gameapi.domain.Deck;
import com.github.sfragata.gameapi.domain.Game;
import com.github.sfragata.gameapi.domain.Player;
import com.github.sfragata.gameapi.domain.Suit;
import com.github.sfragata.gameapi.exception.GameAlreadyExistsException;
import com.github.sfragata.gameapi.exception.GameNotFoundException;
import com.github.sfragata.gameapi.exception.PlayerNotFoundException;

/**
 * Interface for the game service
 * @author Silvio Fragata
 */
public interface GameService {

    /**
     * method to create a game
     * @param game the game object
     * @throws GameAlreadyExistsException if the game id already exists
     */
    void createGame(
        Game game)
        throws GameAlreadyExistsException;

    /**
     * method to delete a game
     * @param gameId the game id
     * @throws GameNotFoundException if the game doesn't exist
     */
    void deleteGame(
        Integer gameId)
        throws GameNotFoundException;

    /**
     * method to add a deck to the game
     * @param gameId the game id
     * @param deck the deck object
     * @throws GameNotFoundException if the game doesn't exist
     */
    void addDeck(
        Integer gameId,
        Deck deck)
        throws GameNotFoundException;

    /**
     * method to add a player to the game
     * @param gameId the game id
     * @param player the player object
     * @throws GameNotFoundException if the game doesn't exist
     */
    void addPlayer(
        Integer gameId,
        Player player)
        throws GameNotFoundException;

    /**
     * method to remove a player to the game
     * @param gameId the game id
     * @param playerId the player id
     * @throws GameNotFoundException if the game doesn't exist
     * @throws PlayerNotFoundException if the player doesn't exist
     */
    void removePlayer(
        Integer gameId,
        Integer playerId)
        throws GameNotFoundException,
        PlayerNotFoundException;

    /**
     * method to return the game
     * @param gameId the game id
     * @return the game object
     * @throws GameNotFoundException if the game doesn't exist
     */
    Game getGame(
        Integer gameId)
        throws GameNotFoundException;

    /**
     * method to deal a card to a player
     * @param gameId the game id
     * @param playerId the player id
     * @return the game object
     * @throws GameNotFoundException if the game doesn't exist
     * @throws PlayerNotFoundException if the player doesn't exist
     */
    Game dealCards(
        Integer gameId,
        Integer playerId)
        throws GameNotFoundException,
        PlayerNotFoundException;

    /**
     * method to return the players of the game
     * @param gameId the game id
     * @return the list of players
     * @throws GameNotFoundException if the game doesn't exist
     */
    List<Player> listPlayers(
        Integer gameId)
        throws GameNotFoundException;

    /**
     * method to return quantity of cards grouped by suit
     * @param gameId the game id
     * @return a Map with key the Suit and the quantity of cards for this Suit
     * @throws GameNotFoundException if the game doesn't exist
     */
    Map<Suit, Integer> findCardsBySuit(
        Integer gameId)
        throws GameNotFoundException;

    /**
     * method to return the remaining cards to the game
     * @param gameId the game id
     * @return a List of cards and it quantities
     * @throws GameNotFoundException if the game doesn't exist
     */
    List<CardCount> findRemaingCards(
        Integer gameId)
        throws GameNotFoundException;

    /**
     * method to shuffle all cards of the game
     * @param gameId the game id
     * @throws GameNotFoundException if the game doesn't exist
     */
    void shuffle(
        Integer gameId)
        throws GameNotFoundException;

}
