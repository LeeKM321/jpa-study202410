package com.study.jpa.chap05.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.jpa.chap05.entity.Idol;
import com.study.jpa.chap05.entity.QIdol;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.study.jpa.chap05.entity.QIdol.*;

// 쿼리DSL 전용 구현체 (정해진 틀 없음!)
@Repository
@RequiredArgsConstructor
public class IdolRepositoryCustomImpl implements IdolCustomRepository {

    private final JdbcTemplate template;

    private final JPAQueryFactory factory;

    @Override
    public List<Idol> findAllSortedByName() {
        return factory
                .select(idol)
                .from(idol)
                .orderBy(idol.idolName.asc())
                .fetch();
    }

    @Override
    public Optional<List<Idol>> findByGroupName(String groupName) {
        return Optional.ofNullable(factory
                .select(idol)
                .from(idol)
                .where(idol.group.groupName.eq("아이브"))
                .fetch());
    }


}












