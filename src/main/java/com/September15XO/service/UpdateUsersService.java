package com.September15XO.service;

import com.September15XO.domain.Users;
import com.September15XO.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateUsersService {
    @Autowired
    private UsersRepository usersRepository;

    public Users addNewUsers(Users users) {
        return usersRepository.save(users);
    }
}



