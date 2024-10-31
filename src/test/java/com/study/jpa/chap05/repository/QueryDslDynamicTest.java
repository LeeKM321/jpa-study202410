package com.study.jpa.chap05.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.jpa.chap05.entity.Idol;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.study.jpa.chap05.entity.QIdol.idol;

@SpringBootTest
@Transactional
public class QueryDslDynamicTest {

    @Autowired
    JPAQueryFactory factory;


    @Test
    @DisplayName("동적 쿼리를 사용한 간단한 아이돌 조회")
    void dynamicTest1() {
        // given
        String name = null; // -> 값이 전달되지 않았다고 가정하자.
//        String name = "사쿠라";

        String gender = "남";

        // 동적 쿼리를 위한 BooleanBuilder
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (name != null) {
            booleanBuilder.and(idol.idolName.eq(name));
        }
        if (gender != null) {
            booleanBuilder.and(idol.gender.eq(gender));
        }


        // when
        List<Idol> result = factory
                .selectFrom(idol)
                .where(booleanBuilder)
                .fetch();

        // then
        result.forEach(System.out::println);
    }


    @Test
    @DisplayName("동적 쿼리를 사용한 간단한 아이돌 조회2")
    void dynamicTest2() {
        // given
        String name = null; // -> 값이 전달되지 않았다고 가정하자.
//        String name = "사쿠라";
        String gender = "남";

        // when
        List<Idol> result = factory
                .selectFrom(idol)
                // BooleanExpression을 리턴하는 메서드를 직접 호출
                .where(nameEq(name), genderEq(gender))
                .fetch();

        // then
        result.forEach(System.out::println);
    }

    // 전달받은 값이 없다면 null을 리턴하고, 그렇지 않은 경우 논리 표현식 결과를 리턴합니다.
    // WHERE절에서는 null값인 조건을 건너 뜁니다. (쿼리를 완성하지 않음)
    private BooleanExpression nameEq(String nameParam) {
        return nameParam != null ? idol.idolName.eq(nameParam) : null;
    }

    private BooleanExpression genderEq(String genderParam) {
        if (genderParam == null) return null;
        return idol.gender.eq(genderParam);
    }


    @Test
    @DisplayName("동적 정렬을 사용한 아이돌 조회")
    void dynamicTest3() {
        // given
        String sortBy = "idolName"; // 나이, 이름, 그룹명
        boolean asc = true; // 오름차 true, 내림차 false

        OrderSpecifier<?> specifier = null;
        // 동적 정렬 조건 생성
        switch (sortBy) {
            case "idolName":
                specifier = asc ? idol.idolName.asc() : idol.idolName.desc();
                break;
            case "groupName":
                specifier = asc ? idol.group.groupName.asc() : idol.group.groupName.desc();
                break;
            case "age":
                specifier = asc ? idol.age.asc() : idol.age.desc();
        }


        // when
        List<Idol> result = factory
                .selectFrom(idol)
                .orderBy(specifier)
                .fetch();

        // then
        System.out.println("\n\n");
        result.forEach(System.out::println);
    }


}


















