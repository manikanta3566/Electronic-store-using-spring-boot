package com.project.electronic.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
public class Category {

    @Id
    private String id;

    private String title;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdDate;

    private String coverImagePath;

    public Category(){
        this.id= UUID.randomUUID().toString();
    }
}
