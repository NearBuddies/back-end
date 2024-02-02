package com.NearBuddies.backend.event;

import com.NearBuddies.backend.comment.Comment;
import com.NearBuddies.backend.rating.Rating;
import com.NearBuddies.backend.registration.Registration;
import com.NearBuddies.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Document(collection = "nearbuddies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    private String id;
    private String communityId;
    private String name;
    private String description;
    private Type type;
    private Date date;
    private String address;
    byte[] poster;
    private int credits;
    private User organizer;
    private List<Registration> registrations = new ArrayList<>();  // List of registered memebers
    private List<Rating> ratings = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
}