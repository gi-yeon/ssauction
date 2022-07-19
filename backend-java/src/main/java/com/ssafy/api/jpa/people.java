package com.ssafy.api.jpa;


import com.ssafy.db.entity.BaseEntity;
import lombok.Data;
import org.checkerframework.checker.interning.qual.InternedDistinct;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class people extends BaseEntity {
    // id -> pk
    @Column(name = "people_name",nullable = false,unique = false )
    String name;
}
