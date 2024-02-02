package com.NearBuddies.backend.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

//@Document(collection = "nearbuddies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int credits;

    public void addCredits(int credits){
        this.credits+=credits;
    }

    public void subtractCredits(int credits){
        this.credits-=credits;
    }
}
