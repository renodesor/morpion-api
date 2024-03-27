package com.renodesor.morpionapi.repository;

import com.renodesor.morpionapi.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {
}
