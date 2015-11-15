package com.example.campaign.domain;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@MappedSuperclass
abstract class DatabaseObject {
    private Long id;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}


