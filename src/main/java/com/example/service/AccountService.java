package com.example.service;

import com.example.entity.Account;

public interface AccountService {

    void save(Account account);

    void toggleAccountStatus(String username);

    Account findByUsername(String username);

    void deleteAccount(String username);
}
