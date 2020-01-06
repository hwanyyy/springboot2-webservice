package org.example.springboot.domain.posts;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springboot.domain.BaseTimeEntity;

import javax.persistence.*;

/*  domain : 게시글, 댓글, 회원, 정산, 결제 등 소프트웨어에 대한 요구사항 혹은 문제 영역(비지니스 처리 담당)
JAP annotation: @Entity, @Id, @GeneratedValue, @Column
Lombok annotation: @Getter, @NoArgsConstructor, @Builder
 */

// annotation의 순서를 주요 annotation을 class에 가깝게
@Getter
@NoArgsConstructor      // 기본 생성자 자동 추가 == public Posts(){}
@Entity     // Jpa의 annotation, table과 링크 될 class, 기본값으로 class의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭 ex) SalesManager.java -> sales_manager table
public class Posts extends BaseTimeEntity {    // 실제 DB의 table과 매칭(접근) 될 class, 보통 Entity class라고 함, Jpa를 사용하면 DB데이터에 작업할 경우 실제 query를 날리기보다 이 class를 수정

    @Id // 해당 테이블의 PK field
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // PK의 생성 규칙, springboot 2.0에서는 IDENTITY옵션을 줘야만 auto_increment 됨
    private Long id;

    @Column(length = 500, nullable = false)     // table의 column을 나타내며 굳이 선언하지 않아도, 해당 클래스의 필드는 모두 column이 됨, 그러나 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용 (VARCHAR(255) default -> 500)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder    // 해당 class의 빌더 패턴 클래스를 생성, 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    //기본적인 구조는 생성자(빌더)를 통해 최종값을 채운 후 db에 삽입하는 것이며, 값 변경 필요한 경우 메소드 호출
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}