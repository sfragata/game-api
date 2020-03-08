package com.github.sfragata.gameapi.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents the game, storing its game id, the list of players and the game deck (shoe)
 * @author Silvio Fragata
 *
 */
public class Game {

    private Integer gameId;

    private final List<Player> players = new ArrayList<>();

    private Shoe shoe;

    /**
     * Default constructor
     */
    public Game() {

        super();
    }

    public Integer getGameId() {

        return this.gameId;
    }

    public void setGameId(
        final Integer gameId) {

        this.gameId = gameId;
    }

    public Shoe getShoe() {

        return this.shoe;
    }

    public void setShoe(
        final Shoe shoe) {

        this.shoe = shoe;
    }

    public List<Player> getPlayers() {

        return this.players;
    }

}
