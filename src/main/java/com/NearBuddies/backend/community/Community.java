package com.NearBuddies.backend.community;

import com.NearBuddies.backend.membership.Membership;
import com.NearBuddies.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "nearbuddies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Community {
    @Id
    private String id;
    private String name;
    private String description;
    private Visibility visibility;
    private User creator;
    private User admin;
    private String imgUrl;
    private List<Membership> members;

    public Community(String name, String description, Visibility visibility,
                     User creator, User admin, String imgUrl) {
        this.name = name;
        this.description = description;
        this.visibility = visibility;
        this.creator = creator;
        this.admin = admin;
        this.imgUrl = imgUrl;
        this.members = new ArrayList<Membership>();
    }

    public void addMember(User user){
        members.add(new Membership(user));
    }
}
