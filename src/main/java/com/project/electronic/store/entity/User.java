package com.project.electronic.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@AllArgsConstructor
@Data
public class User {

    @Id
    private String id;

    private boolean active;

    private String username;

    @Column(unique = true,nullable = false)
    private String email;

    private String password;

    @CreationTimestamp
    private LocalDateTime createdDate;

    private String userImagePath;

    public User(){
        id= UUID.randomUUID().toString();
        active=true;
    }

}
