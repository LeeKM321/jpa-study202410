package com.study.jpa.chap03;

import com.study.jpa.chap02.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentPageRepository extends JpaRepository<Student, String> {

//    findAll(Pageable pageable); => 기본 제공 - 안만들어도 됨.

    // 학생의 이름에 특정 단어가 포함된 걸 조회 + 페이징 정보
    Page<Student> findByNameContaining(String name, Pageable pageable);

}
