package com.spintech.testtask.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class Actor {

    @Id
    private String id;

    private String name;
}
