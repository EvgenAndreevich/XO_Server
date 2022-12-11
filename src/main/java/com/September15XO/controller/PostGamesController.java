package com.September15XO.controller;

import com.September15XO.domain.Games;
import com.September15XO.service.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;

@RestController
@RequestMapping("/game")
public class PostGamesController {
    @Autowired
    private GamesService gamesService;

    @GetMapping("start-game")//должен быть пост но сделали гет что бы написать контролер без проблем
    public ResponseEntity<Games> startGames(@RequestParam Integer usersId) {
        return ResponseEntity.ok(gamesService.startGame(usersId));
    }

    @GetMapping("check-status")
    public ResponseEntity<Games> checkStatus(@RequestParam Integer gameId) {
        return ResponseEntity.ok(gamesService.findGameById(gameId));
    }

    @GetMapping("do-move")
    public ResponseEntity<Games> doMove(@RequestParam Integer gameId,
                                        @RequestParam Integer userId,
                                        @RequestParam Integer coordX,
                                        @RequestParam Integer coordY){
        return ResponseEntity.ok(gamesService.doMove(gameId, userId, coordX, coordY));
    }
}

