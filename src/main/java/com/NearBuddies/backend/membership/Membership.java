package com.NearBuddies.backend.membership.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "nearbuddies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Membership {
    @Id
    private String id;
    private Date joinedAt;
    private Date leftAt;
    private boolean isActive;
    private UserDTO user;

    public Membership(Date joinedAt, Date leftAt, UserDTO user) {
        this.joinedAt = joinedAt;
        this.leftAt = leftAt;
        this.user = user;
        this.isActive = leftAt == null || leftAt.after(new Date());
    }

    public void setJoinedAt(Date joinedAt) {
        this.joinedAt = joinedAt;
    }

    public void setLeftAt(Date leftAt) {
        this.leftAt = leftAt;
        updateIsActive();
    }

    public boolean isActive() {
        return isActive;
    }

    private void updateIsActive() {
        this.isActive = leftAt == null || leftAt.after(new Date());
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
