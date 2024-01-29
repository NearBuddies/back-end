package com.NearBuddies.backend.community;

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

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Community {
    @Id
    private String id;
    private String name;
    private String description;
    private Visibility visibility;
    @DocumentReference
    private User creator;
    @DocumentReference
    private User admin;
    // Image de la communauté
    byte[] profilPhoto;
    @DocumentReference
    private List<Membership> members = new ArrayList<>();
}
