package com.NearBuddies.backend.membership;

import com.NearBuddies.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Membership {
    @Id
    private String id;
    private LocalDateTime joinedAt;
    private LocalDateTime leftAt = null;
    private boolean isActive;
    private User user;

    public Membership(User user){
        this.joinedAt = LocalDateTime.now();
        this.isActive = leftAt==null;
        this.user = user;
    }
}
