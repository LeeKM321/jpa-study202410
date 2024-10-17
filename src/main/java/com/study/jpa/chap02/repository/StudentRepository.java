package com.study.jpa.chap02.repository;

import com.study.jpa.chap02.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {

    List<Student> findByName(String name);

    List<Student> findByCityAndMajor(String city, String major);

    // WHERE major LIKE '%major%'
    List<Student> findByMajorContaining(String major);

    // WHERE major LIKE 'major%'
    List<Student> findByMajorStartingWith(String major);

    // WHERE major LIKE '%major'
    List<Student> findByMajorEndingWith(String major);

    // WHERE age >= ?
//    List<Student> findNameAndMajorByAgeGreaterThanEqual(int age);

}















