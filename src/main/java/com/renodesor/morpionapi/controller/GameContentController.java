package com.renodesor.morpionapi.controller;

import com.renodesor.morpionapi.entity.GameContent;
import com.renodesor.morpionapi.repository.GameContentRepository;
import com.renodesor.morpionapi.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameContentController {

    private final GameContentRepository gameContentRepository;

    @Autowired
    public GameContentController(GameContentRepository gameContentRepository) {
        this.gameContentRepository = gameContentRepository;
    }

    @PostMapping(value="/api/game-contents/add")
    public ResponseEntity<GameContent> addGameContent(@RequestBody String gameContentStr) throws Exception {

        GameContent gameContent = (GameContent) Converter.convertJsonToObject(Converter.formatStrJson(gameContentStr), "GameContent");

        GameContent createdGameContent  = gameContentRepository.save(gameContent);
        if (createdGameContent != null) {
            return new ResponseEntity<>(createdGameContent, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value="/api/game-contents/update")
    public ResponseEntity<GameContent> updateGameContent(@RequestBody String gameContentStr) throws Exception {

        GameContent gameContent = (GameContent) Converter.convertJsonToObject(Converter.formatStrJson(gameContentStr), "GameContent");

        GameContent createdGameContent  = gameContentRepository.save(gameContent);

        if (createdGameContent != null) {
            return new ResponseEntity<>(createdGameContent, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/api/game-contents/delete")
    public ResponseEntity<Integer> deleteGameContentById(@RequestParam Integer id) {
        GameContent gameContent = gameContentRepository.getReferenceById(id);

        if (gameContent != null) {
            gameContentRepository.delete(gameContent);
            return new ResponseEntity<>(gameContentRepository.findAll().size(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/api/game-contents")
    public ResponseEntity<GameContent> getGameContentById(@RequestParam Integer id) {
        GameContent gameContent = gameContentRepository.getReferenceById(id);

        if (gameContent != null) {
            return new ResponseEntity<>(gameContent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/api/game-contents/all")
    public ResponseEntity<List<GameContent>> getAllGameContents() {
        List<GameContent> gameContents = gameContentRepository.findAll();

        if (gameContents != null) {
            return new ResponseEntity<>(gameContents, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
