package com.github.sfragata.gameapi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sfragata.gameapi.domain.Card;
import com.github.sfragata.gameapi.domain.CardCount;
import com.github.sfragata.gameapi.domain.Deck;
import com.github.sfragata.gameapi.domain.Game;
import com.github.sfragata.gameapi.domain.Player;
import com.github.sfragata.gameapi.domain.Shoe;
import com.github.sfragata.gameapi.domain.Suit;
import com.github.sfragata.gameapi.exception.GameAlreadyExistsException;
import com.github.sfragata.gameapi.exception.GameNotFoundException;
import com.github.sfragata.gameapi.exception.PlayerNotFoundException;
import com.github.sfragata.gameapi.helper.ListShuffleHelper;

/**
 * Class that do operations for games
 * @author Silvio Fragata
 */
@Service
public class GameServiceImpl
    implements GameService {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ListShuffleHelper listShuffleHelper;

    private final List<Game> games = new ArrayList<>();

    /**
     * Default constructor
     */
    public GameServiceImpl() {

        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createGame(
        final Game pGame)
        throws GameAlreadyExistsException {

        final var game = findById(pGame.getGameId());
        if (game.isPresent()) {
            throw new GameAlreadyExistsException(pGame.getGameId());
        } else {
            this.games.add(pGame);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteGame(
        final Integer gameId)
        throws GameNotFoundException {

        final var game = findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));

        this.games.remove(game);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDeck(
        final Integer gameId,
        final Deck deck)
        throws GameNotFoundException {

        final var game = findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        Shoe shoe = game.getShoe();
        if (shoe == null) {
            shoe = new Shoe();
            game.setShoe(shoe);
        }
        shoe.addCards(deck);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPlayer(
        final Integer gameId,
        final Player player)
        throws GameNotFoundException {

        final var game = findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        game.getPlayers().add(player);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePlayer(
        final Integer gameId,
        final Integer playerId)
        throws GameNotFoundException,
        PlayerNotFoundException {

        final var game = findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        final Player player = this.playerService.getPlayer(playerId, game.getPlayers());
        game.getPlayers().remove(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game getGame(
        final Integer gameId)
        throws GameNotFoundException {

        return findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game dealCards(
        final Integer gameId,
        final Integer playerId)
        throws GameNotFoundException,
        PlayerNotFoundException {

        final var game = findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        final Player player = this.playerService.getPlayer(playerId, game.getPlayers());

        if (game.getShoe() != null && !game.getShoe().getCards().isEmpty()) {
            final Card card = game.getShoe().getCards().remove(0);
            player.addCard(card);
        }
        return game;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> listPlayers(
        final Integer gameId)
        throws GameNotFoundException {

        final var game = findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        return game.getPlayers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Suit, Integer> findCardsBySuit(
        final Integer gameId)
        throws GameNotFoundException {

        final var game = findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));

        final EnumMap<Suit, Integer> map = new EnumMap<>(Suit.class);

        final Shoe shoe = game.getShoe();

        if (shoe != null && !shoe.getCards().isEmpty()) {

            shoe.getCards().forEach(
                card -> map.put(card.getSuit(), map.get(card.getSuit()) != null ? map.get(card.getSuit()) + 1 : 1));
        }

        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CardCount> findRemaingCards(
        final Integer gameId)
        throws GameNotFoundException {

        final var cardCountList = new ArrayList<CardCount>();

        final var game = findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        final Shoe shoe = game.getShoe();

        if (shoe != null && !shoe.getCards().isEmpty()) {
            final List<Card> sortedList = new ArrayList<>(shoe.getCards());
            Collections.sort(sortedList);
            for (final Card card : sortedList) {
                final Optional<CardCount> cardCountOptional =
                    cardCountList.stream().filter(filter -> filter.getCard().equals(card)).findAny();

                if (cardCountOptional.isPresent()) {
                    cardCountOptional.get().getCount().incrementAndGet();
                } else {
                    final CardCount cardCount = new CardCount();
                    cardCount.setCard(card);
                    cardCount.getCount().incrementAndGet();
                    cardCountList.add(cardCount);
                }

            }
        }
        return cardCountList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shuffle(
        final Integer gameId)
        throws GameNotFoundException {

        final var game = findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        final Shoe shoe = game.getShoe();
        if (shoe != null) {
            this.listShuffleHelper.shuffle(shoe.getCards());
        }
    }

    private Optional<Game> findById(
        final Integer gameId) {

        return this.games.stream().filter(game -> game.getGameId().equals(gameId)).findAny();
    }

}
