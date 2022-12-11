package com.September15XO.service;

import com.September15XO.domain.Games;
import com.September15XO.domain.Users;
import com.September15XO.enums.StatusGame;
import com.September15XO.repository.GamesRepository;
import com.September15XO.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.September15XO.enums.StatusGame.*;

@Service
public class GamesService {
    @Autowired
    private GamesRepository gamesRepository;
    @Autowired
    private UsersRepository usersRepository;

    private final String COORD_X = "X";
    private final String COORD_O = "O";

    @Transactional
    public Games startGame(Integer usersId) {
        Users user = usersRepository.findById(usersId)
                .orElseThrow(() -> new RuntimeException("пользователь для этого id не найден!!!"));
        Optional<Games> game = gamesRepository.getByStatus(WAITING);
        if (game.isPresent()) {
            game.get().setPlayerO(user);
            game.get().setStatus(PLAYER_X);
            return game.get();
        }
        Games newGame = new Games();
        newGame.setPlayerX(user);
        newGame.setStatus(WAITING);
        newGame.setField("*;*;*;*;*;*;*;*;*");
        gamesRepository.save(newGame);
        return newGame;
    }

    public Games findGameById(Integer gameId) {
        return gamesRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Игра с таким id не найдена!!!"));
    }

    @Transactional
    public Games doMove(Integer gameId, Integer userId, int x, int y) {
        Games games = gamesRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Игра с таким id не найдена!!!"));
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("пользователь для этого id не найден!!!"));
        String symbol = "";
        if (games.getPlayerX().getNameId().equals(users.getNameId())) {
            symbol = COORD_X;
        } else {
            symbol = COORD_O;
        }
        String field = games.getField();
        String[] array = field.split(";");
        String[][] fieldArray = new String[3][3];
        for (int i = 0; i < 3; i++) {
            fieldArray[0][i] = array[i];
        }
        for (int i = 3; i < 6; i++) {
            fieldArray[1][i - 3] = array[i];
        }
        for (int i = 6; i < 9; i++) {
            fieldArray[2][i - 6] = array[i];
        }

        StatusGame statusGame;
        if (fieldArray[x][y].equals("*")) {
            statusGame = travelMark(symbol, x, y, fieldArray);
        } else {
            throw new RuntimeException("неверные координаты");
        }

        field = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field += fieldArray[i][j] + ";";
            }
        }

        games.setStatus(statusGame);
        games.setField(field);
        return games;
    }

    public StatusGame travelMark(String symbole, int x, int y, String[][] array) {
        array[x][y] = symbole;
        if (checkDraw(array)) {
            return DRAW;
        }

        if (symbole.equals(COORD_X)) {
            return checkWinningCombination(COORD_X + COORD_X + COORD_X, symbole, array);
        } else {
            return checkWinningCombination(COORD_O + COORD_O + COORD_O, symbole, array);
        }
    }

    public boolean checkDraw(String[][] array) {
        boolean arraysContainsStar = false;
        for (int i = 2; i > -1; i--) {
            for (int j = 0; j < 3; j++) {
                if (array[j][i].equals("*")) {
                    arraysContainsStar = true;
                    break;
                }

            }
        }
        if (!arraysContainsStar) {
            return true;
        }
        return false;
    }


    public StatusGame checkWinningCombination(String combination, String symbol, String[][] array) {
        if ((array[0][0] + array[0][1] + array[0][2]).equals(combination)
                || (array[1][0] + array[1][1] + array[1][2]).equals(combination)
                || (array[2][0] + array[2][1] + array[2][2]).equals(combination)
                || (array[0][0] + array[1][0] + array[2][0]).equals(combination)
                || (array[0][1] + array[1][1] + array[2][1]).equals(combination)
                || (array[0][2] + array[1][2] + array[2][2]).equals(combination)
                || (array[0][0] + array[1][1] + array[2][2]).equals(combination)
                || (array[0][2] + array[1][1] + array[2][0]).equals(combination)) {
            if (symbol.equals(COORD_X)) {
                return WON_X;
            } else {
                return WON_O;
            }
        }
        if (symbol.equals(COORD_X)) {
            return PLAYER_O;
        } else {
            return PLAYER_X;
        }
    }
}

