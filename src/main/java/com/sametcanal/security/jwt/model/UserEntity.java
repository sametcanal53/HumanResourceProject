package com.sametcanal.security.jwt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sametcanal.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column
    private String username;

    @Column
    @JsonIgnore
    private String password;

    @Column(name="system_auto_data")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date date;
}

