package com.NearBuddies.backend.membership;

import com.NearBuddies.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "nearbuddies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Membership {
    @Id
    private String id;
    private LocalDateTime joinedAt;
    private LocalDateTime leftAt;
    private boolean isActive;
    private User user;

    public Membership(User user) {
        this.joinedAt = LocalDateTime.now();
        this.leftAt = null;
        this.user = user;
        this.isActive = leftAt == null;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public void setLeftAt(LocalDateTime leftAt) {
        this.leftAt = leftAt;
        updateIsActive();
    }

    public boolean isActive() {
        return isActive;
    }

    private void updateIsActive() {
        this.isActive = leftAt == null;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
