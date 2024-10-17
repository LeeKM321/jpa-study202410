package com.study.jpa.chap03;

import com.study.jpa.chap02.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StudentPageRepositoryTest {

    @Autowired
    StudentPageRepository repository;

    @BeforeEach
    void bulkInsert() {
        for (int i = 1; i <= 147; i++) {
            Student s = Student.builder()
                    .name("김시골" + i)
                    .city("도시" + i)
                    .major("숨쉬기" + i)
                    .build();
            repository.save(s);
        }
    }

    @Test
    @DisplayName("기본적인 페이지 조회 테스트")
    void basicPageTest() {
        // given
        int pageNo = 6;
        int amount = 10;

        // 페이지 정보 객체 생성 (Pageable)
        // 여기서는 페이지 번호가 zero-based임: 1페이지를 0으로 취급
        Pageable pageInfo = PageRequest.of(pageNo - 1, amount);

        // when
        Page<Student> students = repository.findAll(pageInfo);

        // 실질적 데이터를 꺼내기
        List<Student> studentList = students.getContent();

        // 총 페이지 수
        int totalPages = students.getTotalPages();

        // 총 학생 수
        long totalElements = students.getTotalElements();

        boolean next = students.hasNext();
        boolean prev = students.hasPrevious();


        // then
        System.out.println("\n\n\n");
        System.out.println("totalPages = " + totalPages);
        System.out.println("totalElements = " + totalElements);
        System.out.println("prev = " + prev);
        System.out.println("next = " + next);

        studentList.forEach(System.out::println);
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("이름검색 + 페이징")
    void testSearchAndPaging() {
        // given
        int pageNo = 3;
        int amount = 6;

        Pageable pageable = PageRequest.of(pageNo-1, amount);


        // when
        Page<Student> students
                = repository.findByNameContaining("3", pageable);

        int totalPages = students.getTotalPages();
        long totalElements = students.getTotalElements();
        boolean prev = students.hasPrevious();
        List<Student> studentList = students.getContent();

        /*
         페이징 처리 시에 버튼 알고리즘은 jpa에서 따로 제공하지 않기 때문에
         버튼 배치 알고리즘을 수행할 클래스는 여전히 필요합니다.
         제공되는 정보는 이전보다 많기 때문에, 좀 더 수월하게 처리가 가능합니다.
         */


        // then
        System.out.println("\n\n\n");
        System.out.println("totalPages = " + totalPages);
        System.out.println("totalElements = " + totalElements);
        System.out.println("prev = " + prev);

        studentList.forEach(System.out::println);
        System.out.println("\n\n\n");
    }

}












