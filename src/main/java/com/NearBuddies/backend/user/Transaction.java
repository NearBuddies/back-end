package com.NearBuddies.backend.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    private String id;
    private String userId;
    private double amount;
    private int credits;
    private LocalDateTime madeAt;
    private String motif;

    public Transaction(String userId, double amount, int credits, String motif) {
        this.userId = userId;
        this.amount = amount;
        this.credits = credits;
        this.madeAt = LocalDateTime.now();
        this.motif = motif;
    }
}
