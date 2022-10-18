package com.example;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Nested
    @DisplayName("findByUsername()")
    class findByUsername {

        @Test
        @DisplayName("should return object with the same username, when called")
        void shouldReturnObjectWithSameUsernameWhenCalled() {
            String expectedUsername = "12345";
            Account expectedDbAccount = Account.builder().username(expectedUsername).build();

            Mockito
                    .when(accountRepository.findByUsername(expectedUsername))
                    .thenReturn(expectedDbAccount);

            Account result = accountService.findByUsername(expectedUsername);

            // expectedDbAccount == result
//        Assertions.assertSame(expectedDbAccount, result);
//        Assertions.assertTrue(expectedDbAccount == result);

            Assertions.assertEquals(expectedUsername, result.getUsername());

        }

        @Test
        @DisplayName("should throw NOT_FOUND, when no account with such username")
        void shouldThrowNotFoundWhenNoSuchAccount() {
            HttpStatus expectedErrorStatus = HttpStatus.NOT_FOUND;

            Mockito
                    .when(accountRepository.findByUsername(ArgumentMatchers.anyString()))
                    .thenReturn(null);

            var exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
                accountService.findByUsername("dummy_username");
            });

            Assertions.assertEquals(expectedErrorStatus, exception.getStatus());
        }

    }


    @Nested
    @DisplayName("save()")
    class Save {

        @Test
        void shouldThrowConflictWhenAccountExistsInDatabase() {
            HttpStatus expectedStatus = HttpStatus.CONFLICT;

            Mockito
                    .when(accountRepository.findByUsername(ArgumentMatchers.anyString()))
                    .thenReturn(Account.builder().build());

            var exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
                accountService.save(Account.builder().username("abc").build());
            });

            // CONFLICT
            Assertions.assertEquals(expectedStatus, exception.getStatus());

            Mockito
                    .verify(
                            accountRepository,
                            Mockito.times(0)
                    )
                    .save(ArgumentMatchers.any());

        }

        @Test
        void shouldCallDbSaveWhenNoSuchAccount() {

            Account expectedAccount = Account.builder()
                    .username("qwerty")
                    .build();

            Mockito
                    .when(accountRepository.findByUsername(expectedAccount.getUsername()))
                    .thenReturn(null);

            accountService.save(expectedAccount);

            Mockito.verify(
                            accountRepository,
                            Mockito.times(1)
                    )
                    .save(ArgumentMatchers.argThat((passedIn) -> {
                        return passedIn.getUsername().equals(expectedAccount.getUsername());
                    }));
        }
    }
}
