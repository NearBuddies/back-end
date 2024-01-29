package com.NearBuddies.backend.registration;

import com.NearBuddies.backend.event.Type;
import com.NearBuddies.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Registration {
    @Id
    private String Id;
    @DocumentReference
    private User attendee;
    private Type type;
    private Status status;
    private LocalDateTime registredAt;
    private LocalDateTime canceledAt = null;
    private boolean isAttending;

    public Registration(User attendee){
        this.attendee = attendee;
        registredAt = LocalDateTime.now();
        canceledAt = null;
        isAttending = canceledAt==null;
    }
}
