package com.cdweb.peakyblinders.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Users extends BaseEntity {
    private String username;

    private String password;

    private String fullName;

    private String phoneNumber;

    private String role;

    private boolean isActive;
//
//    @OneToMany(mappedBy = "users")
//    private List<BoardsUsers> boardsUsers;
//
//    @ManyToMany(mappedBy = "users")
//    private List<Tasks> tasks;
//
//    @OneToMany(mappedBy = "user")
//    private List<Comment> comments;
}
