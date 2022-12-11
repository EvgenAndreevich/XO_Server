package com.September15XO.repository;

import com.September15XO.domain.Games;
import com.September15XO.domain.Users;
import com.September15XO.enums.StatusGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
}
