package com.renodesor.morpionapi.repository;

import com.renodesor.morpionapi.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
}
