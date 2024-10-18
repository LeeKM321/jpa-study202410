package com.study.jpa.chap05.repository;

import com.study.jpa.chap05.entity.Idol;
import org.springframework.data.jpa.repository.JpaRepository;

// 이 인터페이스는 JPA용 인터페이스
public interface IdolRepository
        extends JpaRepository<Idol, Long> {

    // 쿼리 메서드와 JPQL 등등을 작성할 것이다.


}
