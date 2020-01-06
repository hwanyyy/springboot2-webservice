package org.example.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
규모가 있는 프로젝트에서의 데이터 조회는 FK의 조인, 복잡한 조건 등으로 이런 Entity class만으로 처리하기 어려워 조회용 프레임워크를 추가로 사용(querydsl추천)
등록/수정/삭데 등은 SpringDataJpa를 통해 진행
 */

// JpaRepository<Entity class, PK type>를 상속하면 기본적인 CRUD 메소드가 자동으로 생성
public interface PostsRepository extends JpaRepository<Posts, Long> {
    // SpringDataJpa에서 제공하지 않는 메소드는 query로 작성
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

}
