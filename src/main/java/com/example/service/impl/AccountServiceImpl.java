package com.example.service.impl;

import com.example.entity.Account;
import com.example.entity.AccountStatus;
import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

//    public AccountServiceImpl(AccountRepository accountRepository) {
//        this.accountRepository = accountRepository;
//    }

    @Override
    public void save(Account account) {
        Account dbAccount = accountRepository.findByUsername(account.getUsername());
        if (dbAccount != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        accountRepository.save(account);
    }

    @Override
    public void toggleAccountStatus(String username) {
        Account dbAccount = findByUsername(username);
        if (dbAccount.getAccountStatus() == AccountStatus.ACTIVE) {
            dbAccount.setAccountStatus(AccountStatus.INACTIVE);
        } else {
            dbAccount.setAccountStatus(AccountStatus.ACTIVE);
        }

        accountRepository.save(dbAccount);
    }

    @Override
    public Account findByUsername(String username) {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return account;
    }

    @Override
    public void deleteAccount(String username) {
        accountRepository.deleteByUsername(username);
    }
}
