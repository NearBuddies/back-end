package com.NearBuddies.backend.registration;

import com.NearBuddies.backend.registration.Type;
import com.NearBuddies.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

//@Document(collection = "nearbuddies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Registration {
    @Id
    private String id;
    private String eventId;
    private User attendee;
    private Type type;
    private Status status;
    private LocalDateTime registredAt;
    private LocalDateTime canceledAt = null;
    private boolean isAttending;

    public Registration(String eventId, User attendee, Type type, Status status, LocalDateTime registredAt, LocalDateTime canceledAt, boolean isAttending){
        this.eventId = eventId;
        this.attendee = attendee;
        this.type = type;
        this.status = status;
        this.registredAt = registredAt;
        this.canceledAt = canceledAt;
        this.isAttending = isAttending;
    }
}
