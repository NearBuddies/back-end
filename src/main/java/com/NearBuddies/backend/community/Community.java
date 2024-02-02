package com.NearBuddies.backend.community;

import com.NearBuddies.backend.event.Event;
import com.NearBuddies.backend.membership.Membership;
import com.NearBuddies.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;

//@Document(collection = "nearbuddies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Community {
    @Id
    private String id;
    private String name;
    private String description;
    private Visibility visibility;
    // Image de la communauté
    byte[] profilPhoto;
    private List<Membership> members = new ArrayList<>();
    private List<Event> events = new ArrayList<>();
}
