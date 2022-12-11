package com.September15XO.domain;

import com.September15XO.enums.StatusGame;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "games")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Games {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "player_x")
    private Users playerX;

    @ManyToOne
    @JoinColumn(name = "player_o")
    private Users playerO;

    private String field;

    @Enumerated(EnumType.STRING)
    private StatusGame status;


}

