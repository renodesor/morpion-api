package com.renodesor.morpionapi.repository;

import com.renodesor.morpionapi.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    List<Player> findByUsernameAndPassword(String username, String password);
}
