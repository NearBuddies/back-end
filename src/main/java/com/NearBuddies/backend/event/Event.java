package com.NearBuddies.backend.event;

import com.NearBuddies.backend.address.Address;
import com.NearBuddies.backend.comment.Comment;
import com.NearBuddies.backend.rating.Rating;
import com.NearBuddies.backend.registration.Registration;
import com.NearBuddies.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "nearbuddies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    private String id;
    private String name;
    private String description;
    private Type type;
    private Date date;
    private Address address;
    byte[] poster;
    private int credits;
    @DocumentReference
    private User organizer;
    @DocumentReference
    private List<Registration> registrations = new ArrayList<>();  // List of registered memebers
    @DocumentReference
    private List<Rating> ratings = new ArrayList<>();
    @DocumentReference
    private List<Comment> comments = new ArrayList<>();
}