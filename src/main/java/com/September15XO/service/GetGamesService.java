package com.September15XO.service;

import com.September15XO.domain.Games;
import com.September15XO.repository.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetGamesService {
    @Autowired
    private GamesRepository gamesRepository;
    public List<Games> getAllGames() {
        return gamesRepository.findAll();
    }
}
