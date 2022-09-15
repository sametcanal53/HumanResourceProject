package com.sametcanal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employees")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","humanResource","humanResourceId"})
public class Employee extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "salary")
    private double salary;

    @JoinColumn(name = "day_off",insertable = false,updatable = false)
    private DayOff dayOff;

    @Column(name = "human_resource")
    private Long humanResourceId;

    @ManyToOne()
    @JoinColumn(name = "human_resource",insertable = false,updatable = false)
    private HumanResource humanResource;
}
