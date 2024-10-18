package com.study.jpa.chap04.repository;

import com.study.jpa.chap04.entity.Department;
import com.study.jpa.chap04.entity.Employee;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class DepartmentRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("부서 정보 조회하면 해당 부서원들도 함께 조회되어야 한다.")
    void testFindDept() {
        // given
        Long id = 2L;

        // when
        Department department = departmentRepository.findById(id).orElseThrow();
            System.out.println("조회 결과 없음!");

        // then
        System.out.println("\n\n\n");
        System.out.println("department = " + department);
        System.out.println("department.getEmployees() = " + department.getEmployees());
        System.out.println("\n\n\n");

    }


    @Test
    @DisplayName("Lazy 로딩과 Eager 로딩의 차이")
    void testLazyAndEager() {
        // 3번 사원을 조회하고 싶은데, 굳이 부서정보까지는 필요없긴 함.

        // given
        Long id = 3L;

        // when
        Employee employee = employeeRepository.findById(id).orElseThrow();

        // then
        System.out.println("\n\n\n");
        System.out.println("employee = " + employee);
        System.out.println(employee.getDepartment().getName());
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("양방향 연관관계에서 연관 데이터의 수정")
    void testChangeDept() {
        // 1번 사원의 부서를 1 -> 2번 부서로 변경해야 한다.

        // given
        Employee foundEmp = employeeRepository.findById(1L).orElseThrow();

        Department newDept = departmentRepository.findById(2L).orElseThrow();

        // 연관관계 편의 메서드 호출 -> 데이터 수정시에는 반대편 엔터티도 꼭 수정을 해 주자!
        foundEmp.changeDepartment(newDept);

        /*
        em.flush(); // DB로 밀어내기
        em.clear(); // 영속성 컨텍스트 비우기 (비우지 않으면 컨텍스트 내의 정보를 참조하려 하니까)
        */

        // when

        // then
        System.out.println("\n\n\n");
        newDept.getEmployees().forEach(emp -> System.out.println(emp));
        System.out.println("\n\n\n");

    }

    @Test
    @DisplayName("N+1 문제 발생 예시")
    void testNPlusOneEx() {
        // given
        List<Department> departments = departmentRepository.findAll();

        // when
        departments.forEach(dept -> {
            System.out.println("========== 사원 리스트 ==========");
            List<Employee> empList = dept.getEmployees();
            System.out.println(empList);

            System.out.println("\n\n");
        });

        // then
    }

    @Test
    @DisplayName("N+1 문제 해결 예시")
    void testNPlusOneSolution() {
        // given
        List<Department> departments
                = departmentRepository.findAllIncludesEmployees();

        // when
        departments.forEach(dept -> {
            System.out.println("========== 사원 리스트 ==========");
            List<Employee> empList = dept.getEmployees();
            System.out.println(empList);

            System.out.println("\n\n");
        });

        // then
    }
    
    @Test
    @DisplayName("부서가 사라지면 해당 사원도 함께 사라진다.")
    void cascadeRemoveTest() {
        // given
        Department department = departmentRepository.findById(1L).orElseThrow();

        // when
        departmentRepository.delete(department);
    
        // then
    }

    @Test
    @DisplayName("고아 객체 삭제")
    void orphanRemovalTest() {
        // given

        // 2번 부서 조회
        Department department
                = departmentRepository.findById(2L).orElseThrow();

        // 2번 부서 사원 목록 가져오기
        List<Employee> employeeList = department.getEmployees();

        // when

        Employee employee = employeeList.get(1);
        employeeList.remove(employee);

        // then
    }

    @Test
    @DisplayName("양방향 관계에서 CascadeType을 PERSIST를 주면 부모가 데이터 변경의 주체가 된다.")
    void cascadePersistTest() {
        // given
        Department department = departmentRepository.findById(2L).orElseThrow();

        Employee pororo = Employee.builder()
                .name("뽀로로")
                .department(department) // 속할 부서를 전달해 줘야 합니다.
                .build();

        // when
        department.getEmployees().add(pororo);

        // then
    }


}

















