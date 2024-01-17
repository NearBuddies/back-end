package com.NearBuddies.backend.community.model;

import com.NearBuddies.backend.membership.MembershipDTO;
import com.NearBuddies.backend.user.UserDTO;
import com.NearBuddies.backend.user.model.Visibility;
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
    private UserDTO creator;
    private UserDTO admin;
    private double latitude;
    private double longitude;
    private List<MembershipDTO> members;

    public Community(String name, String description, Visibility visibility,
                     UserDTO creator, UserDTO admin, double latitude, double longitude) {
        this.name = name;
        this.description = description;
        this.visibility = visibility;
        this.creator = creator;
        this.admin = admin;
        this.latitude = latitude;
        this.longitude = longitude;
        this.members = new ArrayList<MembershipDTO>();
    }
}
