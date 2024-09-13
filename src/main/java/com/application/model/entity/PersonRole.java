package com.application.model.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "person_role")
public class PersonRole {
    @Id
    @Column(name = "person_role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personRoleId;

    @Column(name = "person_role_name")
    private String personRoleName;
    @OneToMany(mappedBy = "personRole")
    private List<Person> persons;

    public int getPersonRoleId() {
        return personRoleId;
    }

    public void setPersonRoleId(int personRoleId) {
        this.personRoleId = personRoleId;
    }

    public String getPersonRoleName() {
        return personRoleName;
    }

    public void setPersonRoleName(String personRoleName) {
        this.personRoleName = personRoleName;
    }
}
