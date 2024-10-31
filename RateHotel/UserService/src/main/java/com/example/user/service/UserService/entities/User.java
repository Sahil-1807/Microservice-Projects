package com.example.user.service.UserService.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "ID")
    private String userId;

    @Column(name = "NAME", length = 25)
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ABOUT")
    private String about;

    @Transient   // FOR LETTING USER KNOW TO NOT SAVE IT IN DATABASE
    private List<Rating> ratings = new ArrayList<>();
}
