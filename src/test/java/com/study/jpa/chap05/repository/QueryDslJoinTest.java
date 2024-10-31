package com.study.jpa.chap05.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.jpa.chap05.entity.Album;
import com.study.jpa.chap05.entity.Group;
import com.study.jpa.chap05.entity.Idol;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static com.study.jpa.chap05.entity.QAlbum.album;
import static com.study.jpa.chap05.entity.QGroup.group;
import static com.study.jpa.chap05.entity.QIdol.idol;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class QueryDslJoinTest {

    @Autowired
    IdolRepository idolRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    JPAQueryFactory factory;

    @Test
    void setUp () {
        /*
        Group leSserafim = groupRepository.findById(1L).orElseThrow();
        Group ive = groupRepository.findById(2L).orElseThrow();
        Group bts = groupRepository.findById(3L).orElseThrow();
        Group newjeans = groupRepository.findById(4L).orElseThrow();


        Album album1 = new Album("MAP OF THE SOUL 7", 2020, bts);
        Album album2 = new Album("FEARLESS", 2022, leSserafim);
        Album album3 = new Album("UNFORGIVEN", 2023, bts);
        Album album4 = new Album("ELEVEN", 2021, ive);
        Album album5 = new Album("LOVE DIVE", 2022, ive);
        Album album6 = new Album("OMG", 2023, newjeans);
        albumRepository.save(album1);
        albumRepository.save(album2);
        albumRepository.save(album3);
        albumRepository.save(album4);
        albumRepository.save(album5);
        albumRepository.save(album6);
        */

        Idol idol1 = new Idol("아이유", 31, "여", null);
        Idol idol2 = new Idol("임영웅", 33, "남", null);

        idolRepository.save(idol1);
        idolRepository.save(idol2);

    }

    @Test
    @DisplayName("내부 조인 예제")
    void innerJoinTest() {
        // given

        // when
        List<Tuple> idolList = factory
                .select(idol, group)
                .from(idol)
                // 첫번째 파라미터는 from절에 있는 엔터티의 연관 객체
                // 두번째 파라미터는 실제로 조인할 엔터티
                .innerJoin(idol.group, group)
                .fetch();

        // then
        System.out.println("\n\n");
        for (Tuple tuple : idolList) {
            Idol foundIdol = tuple.get(idol);
            Group foundGroup = tuple.get(group);
            System.out.println("foundIdol = " + foundIdol);
            System.out.println("foundGroup = " + foundGroup);
        }

    }

    @Test
    @DisplayName("외부 조인 예제")
    void outerJoinTest() {
        // given

        // when
        List<Tuple> idolList = factory
                .select(idol, group)
                .from(idol)
                // 첫번째 파라미터는 from절에 있는 엔터티의 연관 객체
                // 두번째 파라미터는 실제로 조인할 엔터티
                .leftJoin(idol.group, group)
                .fetch();

        // then
        System.out.println("\n\n");
        for (Tuple tuple : idolList) {
            Idol foundIdol = tuple.get(idol);
            Group foundGroup = tuple.get(group);
            System.out.println("foundIdol = " + foundIdol);
            System.out.println("foundGroup = " + foundGroup);
        }

    }

    @Test
    @DisplayName("특정 연도에 발매된 앨범의 아이돌 정보 조회")
    void practiceJoinTest() {
        // given
        int year = 2022;

        // when
        List<Tuple> result = factory
                .select(idol, album)
                .from(idol)
                .innerJoin(idol.group, group)
                .innerJoin(group.albums, album)
                .where(album.releaseYear.eq(year))
                .fetch();

        // then
        for (Tuple tuple : result) {
            Idol foundIdol = tuple.get(idol);
            Album foundAlbum = tuple.get(album);
            System.out.printf("\n아이돌명: %s, 그룹명: %s, 앨범명: %s, 발매연도: %d년\n\n"
                , foundIdol.getIdolName(), foundIdol.getGroup().getGroupName(),
                    foundAlbum.getAlbumName(), foundAlbum.getReleaseYear());
        }

    }

}


















