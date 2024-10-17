package com.study.jpa.chap01.repository;

import com.study.jpa.chap01.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.study.jpa.chap01.entity.Product.Category.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class ProductRepositoryTest2 {

    @Autowired
    ProductRepository productRepository;

//    @BeforeEach
    @Test
    void insertBeforeTest() {
        Product p1 = Product.builder()
                .name("아이폰")
                .category(ELECTRONIC)
                .price(2000000)
                .build();
        Product p2 = Product.builder()
                .name("탕수육")
                .category(FOOD)
                .price(20000)
                .build();
        Product p3 = Product.builder()
                .name("구두")
                .category(FASHION)
                .price(300000)
                .build();
        Product p4 = Product.builder()
                .name("주먹밥")
                .category(FOOD)
                .price(1500)
                .build();
        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
        productRepository.save(p4);
    }

    @Test
    @DisplayName("상품을 데이터베이스에 저장한다.")
    void saveTest() {
        // given
        Product p = Product.builder()
                .name("떡볶이")
                .build();
        // when
        // insert 후 저장된 데이터의 객체를 반환.
        Product saved = productRepository.save(p);

        // then
        assertNotNull(saved);
    }

    @Test
    @DisplayName("1번 상품을 삭제한다.")
    void deleteTest() {
        // given
        Long id = 1L;

        // when
        productRepository.deleteById(id);

        Optional<Product> optional = productRepository.findById(id);
        boolean present = optional.isPresent();
        System.out.println("present = " + present);
        optional.ifPresent(p -> {
            System.out.println("p = " + p);
        });

        // then
        assertFalse(present);
    }

    @Test
    @DisplayName("상품 전체조회를 하면 개수는 3개여야 한다.")
    void selectAllTest() {
        // given

        // when
        List<Product> products = productRepository.findAll();

        // then
        assertEquals(3, products.size());
    }

    @Test
    @DisplayName("2번 상품의 이름과 가격을 변경해야 한다.")
    void updateTest() {
        // given
        Long id = 2L;
        String newName = "마라탕";
        int newPrice = 10000;

        // when
        productRepository.findById(id).ifPresent(p -> {
            p.setName(newName);
            p.setPrice(newPrice);

            // jpa는 따로 update 메서드를 제공하지 않습니다.
            // 조회한 객체의 필드를 setter로 변경하면 자동으로 update가 나갑니다.

            productRepository.save(p);
        });

        // then
    }






}








