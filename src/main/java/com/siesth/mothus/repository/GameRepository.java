package com.siesth.mothus.repository;

import com.siesth.mothus.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    // You can add custom query methods if needed

    //findTopByOrderByDateOfTheGameDesc
    Game findTopByOrderByDateOfTheGameDesc();
}
