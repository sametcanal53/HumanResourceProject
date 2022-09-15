package com.sametcanal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "human_resources")
public class HumanResource extends BaseEntity{

    @Column(name = "human_resource_name")
    private String humanResourceName;

    @OneToMany(mappedBy = "humanResource",cascade = CascadeType.ALL)
    private List<Employee> employees;
}
