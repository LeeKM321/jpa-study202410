package com.study.jpa.chap02.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Setter @Getter @ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_student")
public class Student {

    @Id
    @Column(name = "stu_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "stu_name", nullable = false)
    private String name;

    private String city;

    private String major;

}











