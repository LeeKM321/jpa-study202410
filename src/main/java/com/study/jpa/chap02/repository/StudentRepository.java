package com.study.jpa.chap02.repository;

import com.study.jpa.chap02.entity.Student;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {

    List<Student> findByNameOrderByMajorDesc(String name);

    List<Student> findByCityAndMajor(String city, String major);

    // WHERE major LIKE '%major%'
    List<Student> findByMajorContaining(String major, Sort sort);

    // WHERE major LIKE 'major%'
    List<Student> findByMajorStartingWith(String major);

    // WHERE major LIKE '%major'
    List<Student> findByMajorEndingWith(String major);

    // WHERE age >= ?
//    List<Student> findNameAndMajorByAgeGreaterThanEqual(int age);

    // native SQL 사용법
    @Query(value = "SELECT * FROM tbl_student WHERE stu_name = :nm OR city = :city", nativeQuery = true)
    List<Student> getStudentByNameOrCity(@Param("nm") String name, @Param("city") String city);

    // native-sql
    // SELECT 컬럼명 FROM 테이블명
    // WHERE 컬럼 = ?

    // JPQL
    // SELECT 별칭 FROM 엔터티클래스명 AS 별칭
    // WHERE 별칭.필드명 = ?


    @Query(value = "SELECT st FROM Student st WHERE st.name = ?1 OR st.city = ?2")
    List<Student> getStudentByNameOrCity2(String name, String city);

    // 특정 이름이 포함된 학생 리스트 조회하기
    @Query("SELECT stu FROM Student stu WHERE stu.name LIKE %?1%")
    List<Student> searchByNameWithJPQL(String name);

    // 도시명으로 학생 1명을 단일 조회
    @Query("SELECT s FROM Student s WHERE s.city = ?1")
    Optional<Student> getByCityWithJPQL(String city);

    @Modifying // SELECT 아니면 무조건 붙이기
    @Query("DELETE FROM Student s WHERE s.name = ?1 AND s.city = ?2")
    void deleteByNameAndCityWithJPQL(String name, String city);

}















