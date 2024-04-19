package com.renodesor.morpionapi.controller;

import com.renodesor.morpionapi.entity.Player;
import com.renodesor.morpionapi.repository.PlayerRepository;
import com.renodesor.morpionapi.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.2.12:3000"})
@RestController
public class PlayerController {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @PostMapping(value="/api/players/add")
    public ResponseEntity<Player> addPlayer(@RequestBody String playerStr) throws Exception {

        Player player = (Player) Converter.convertJsonToObject(Converter.formatStrJson(playerStr), "Player");

        Player createdPlayer  = playerRepository.save(player);
        if (createdPlayer != null) {
            return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value="/api/players/update")
    public ResponseEntity<Player> updatePlayer(@RequestBody String playerStr) throws Exception {

        Player player = (Player) Converter.convertJsonToObject(Converter.formatStrJson(playerStr), "Player");

        Player createdPlayer  = playerRepository.save(player);

        if (createdPlayer != null) {
            return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/api/players/delete")
    public ResponseEntity<Integer> deletePlayerById(@RequestParam Integer id) {
        Player player = playerRepository.getReferenceById(id);

        if (player != null) {
            playerRepository.delete(player);
            return new ResponseEntity<>(playerRepository.findAll().size(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/api/players")
    public ResponseEntity<Player> getPlayerById(@RequestParam Integer id) {
        Player player = playerRepository.getReferenceById(id);

        if (player != null) {
            return new ResponseEntity<>(player, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/api/players/usr-psw")
    public ResponseEntity<Player> getPlayerByUsernameAndPassword(@RequestParam String username, @RequestParam String password) {
        List<Player> players = playerRepository.findByUsernameAndPassword(username, password);
        Player player = players == null || players.isEmpty() ? null : playerRepository.findByUsernameAndPassword(username, password).get(0);
        if (player != null) {
            return new ResponseEntity<>(player, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/api/players/all")
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = playerRepository.findAll();

        if (players != null) {
            return new ResponseEntity<>(players, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
