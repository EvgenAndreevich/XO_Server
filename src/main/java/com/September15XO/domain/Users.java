package com.September15XO.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer nameId;

    @Column(name = "name_player")
    private String namePlayer;
}

//    @Transient
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modelCar")
//    private List<ShoppingList> shoppingLists;
