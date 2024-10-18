package com.study.jpa.chap05.repository;

import com.study.jpa.chap05.entity.Idol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class IdolCustomRepositoryTest {

    @Autowired
    IdolCustomRepository customRepository;

    @Test
    @DisplayName("커스텀 레포지토리에 선언한 메서드 호출")
    void testCustomCall() {
        // given

        // when
        List<Idol> sorted = customRepository.findAllSortedByName();
        List<Idol> ive = customRepository.findByGroupName("아이브").orElseThrow();

        // then
        sorted.forEach(System.out::println);
        System.out.println("\n\n\n");
        ive.forEach(System.out::println);
    }

}















