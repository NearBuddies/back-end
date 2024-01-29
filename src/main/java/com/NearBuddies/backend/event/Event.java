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

import java.util.Date;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    private String Id;
    private String name;
    private String description;
    private Type type;
    private Date date;
    private Address address;
    byte[] poster;
    @DocumentReference
    private User organizer;
    @DocumentReference
    private List<Registration> registrations;  // List of registered memebers
    @DocumentReference
    private List<Rating> ratings;
    @DocumentReference
    private List<Comment> comments;
}