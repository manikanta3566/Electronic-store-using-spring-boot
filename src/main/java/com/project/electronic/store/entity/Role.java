package com.project.electronic.store.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
public class Role {

    @Id
    private String id;

    private String name;

    public Role(){
      this.id= UUID.randomUUID().toString();
    }
}
