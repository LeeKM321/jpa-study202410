package com.study.jpa.chap01.repository;

import com.study.jpa.chap01.entity.Product;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class ProductRepositoryTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("상품을 데이터베이스에 저장한다.")
    void saveTest() {
        // given
        Product p = Product.builder()
                .name("신발")
                .price(90000)
                .category(Product.Category.FASHION)
                .build();
        // when
        em.persist(p);

        // then
    }
    
    @Test
    @DisplayName("저장된 상품을 조회한다.")
    void selectTest() {
        // given
        Long id = 1L;
        // when
        Product product = em.find(Product.class, id);

        // then
        assertEquals("신발", product.getName());
    }

    @Test
    @DisplayName("영속성 컨텍스트의 1차 캐시 사용")
    void persistTest() {
        // given
        Product p = Product.builder()
                .name("짜장면")
                .price(8000)
                .category(Product.Category.FOOD)
                .build();

        // when
        em.persist(p);

        Product findProd = em.find(Product.class, 2L);

        // then
        assertEquals(8000, findProd.getPrice());
    }

    @Test
    @DisplayName("특정 상품의 가격을 수정한다.")
    void updateTest() {
        // given
        Product shoes = em.find(Product.class, 1L);

        // when
        shoes.setPrice(50000);
        shoes.setName("할인하는 신발");

        em.persist(shoes);

        int price = em.find(Product.class, 1L).getPrice();

        // then
        assertEquals(50000, price);
    }



}


















