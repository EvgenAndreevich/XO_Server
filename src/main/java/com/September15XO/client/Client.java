package com.September15XO.client;

import com.September15XO.domain.Games;
import com.September15XO.domain.Users;
import com.September15XO.enums.StatusGame;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.September15XO.enums.StatusGame.*;
import static java.lang.Thread.sleep;

public class Client {

    public static String url = "https://xo-server-logics.amvera.io/";
    public static RestTemplate restTemplate = new RestTemplate();


    public static void main(String[] args) throws InterruptedException {
        System.out.println("создаем пользователя, введите имя пользователя:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        Map<String, String> map = new HashMap<>();
        map.put("key", name);
        ResponseEntity<Users> user = restTemplate
                .getForEntity(url + "update-users/add-new-users?name={key}", Users.class, map);

        Map<String, Integer> map2 = new HashMap<>();
        Integer usersId = user.getBody().getNameId();
        map2.put("key2", usersId);
        ResponseEntity<Games> game = restTemplate
                .getForEntity(url + "/game/start-game?usersId={key2}", Games.class, map2);

        StatusGame player = PLAYER_O;
        if (game.getBody().getPlayerX().getNameId().equals(user.getBody().getNameId())) {
            player = PLAYER_X;
        }

        Boolean flag = true;
        while (flag) {
            Map<String, Integer> map3 = new HashMap<>();
            Integer gameId = game.getBody().getId();
            map3.put("key3", gameId);
            StatusGame statusGame = restTemplate
                    .getForEntity(url + "/game/check-status?gameId={key3}", Games.class, map3)
                    .getBody().getStatus();

            if (statusGame.equals(WAITING)) {
                System.out.println("ждем второго игрока...");
                sleep(1000);
            } else {
                System.out.println("игра началась!!!");
                flag = false;
            }
        }

        while (true) {
            try {
                Map<String, Integer> map3 = new HashMap<>();
                Integer gameId = game.getBody().getId();
                map3.put("key3", gameId);
                ResponseEntity<Games> gameWithStatus = restTemplate
                        .getForEntity(url + "/game/check-status?gameId={key3}", Games.class, map3);
                StatusGame statusGame = gameWithStatus.getBody().getStatus();
                if (statusGame.equals(player)) {
                    printArray(getField(gameWithStatus));
                    System.out.println("ваш ход, введите x (от 1 до 3) и y (от 1 до 3):");
                    int x = scanner.nextInt() - 1;
                    int y = scanner.nextInt() - 1;

                    Map<String, Integer> map4 = new HashMap<>();
                    map4.put("key1", game.getBody().getId());
                    map4.put("key2", user.getBody().getNameId());
                    map4.put("key3", x);
                    map4.put("key4", y);


                    ResponseEntity<Games> gameMove = restTemplate
                            .getForEntity(url + "/game/do-move?gameId={key1}&userId={key2}&coordX={key3}&coordY={key4}"
                                    , Games.class, map4);
                } else if (statusGame.equals(WON_X)) {
                    if (player.equals(PLAYER_X)) {
                        System.out.println("вы выиграли!!!");
                    } else {
                        System.out.println("вы проиграли!!!");
                    }
                    return;
                } else if (statusGame.equals(WON_O)) {
                    if (player.equals(PLAYER_O)) {
                        System.out.println("вы выиграли!!!");
                    } else {
                        System.out.println("вы проиграли!!!");
                    }
                    return;
                } else if (statusGame.equals(DRAW)) {
                    System.out.println("ничья!!!");
                    return;
                } else {
                    System.out.println("ожидаем ход другого игрока...");
                    sleep(1000);
                }
            } catch (Exception e) {
                System.out.println("повторите ход, вы ввели неверные координаты!!!");
            }
        }
    }

    private static String[][] getField(ResponseEntity<Games> game) {
        String field = game.getBody().getField();
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
        return fieldArray;
    }

    private static void printArray(String[][] array) {
        for (int i = 2; i > -1; i--) {
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + array[j][i] + " ");
            }
            System.out.println();
        }
    }
}
