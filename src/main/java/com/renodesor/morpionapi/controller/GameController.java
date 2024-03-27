package com.renodesor.morpionapi.controller;

import com.renodesor.morpionapi.entity.Game;
import com.renodesor.morpionapi.repository.GameRepository;
import com.renodesor.morpionapi.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {

    private final GameRepository gameRepository;

    @Autowired
    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @PostMapping(value="/api/games/add")
    public ResponseEntity<Game> addGame(@RequestBody String gameStr) throws Exception {

        Game game = (Game) Converter.convertJsonToObject(Converter.formatStrJson(gameStr), "Game");

        Game createdGame  = gameRepository.save(game);
        if (createdGame != null) {
            return new ResponseEntity<>(createdGame, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value="/api/games/update")
    public ResponseEntity<Game> updateGame(@RequestBody String gameStr) throws Exception {

        Game game = (Game) Converter.convertJsonToObject(Converter.formatStrJson(gameStr), "Game");

        Game createdGame  = gameRepository.save(game);

        if (createdGame != null) {
            return new ResponseEntity<>(createdGame, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/api/games/delete")
    public ResponseEntity<Integer> deleteGameById(@RequestParam Integer id) {
        Game game = gameRepository.getReferenceById(id);

        if (game != null) {
            gameRepository.delete(game);
            return new ResponseEntity<>(gameRepository.findAll().size(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/api/games")
    public ResponseEntity<Game> getGameById(@RequestParam Integer id) {
        Game game = gameRepository.getReferenceById(id);

        if (game != null) {
            return new ResponseEntity<>(game, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/api/games/all")
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameRepository.findAll();

        if (games != null) {
            return new ResponseEntity<>(games, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
