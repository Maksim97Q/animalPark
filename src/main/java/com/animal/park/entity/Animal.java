package com.animal.park.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String view;
    @Column
    private String dateOfBirth;
    @Column
    private String gender;
    @Column
    private String nickname;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
}
