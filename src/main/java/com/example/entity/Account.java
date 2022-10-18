package com.example.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Account {

    @Id
    private Long id;

    private String username;

    private AccountStatus accountStatus;
}
