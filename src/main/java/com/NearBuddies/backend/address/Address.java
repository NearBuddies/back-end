package com.NearBuddies.backend.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@Document(collection = "nearbuddies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    private  String Id;
    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
