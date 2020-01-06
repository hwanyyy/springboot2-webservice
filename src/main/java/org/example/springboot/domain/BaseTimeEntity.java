package org.example.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass   // JPA Entity Class들이 BaseTimeEntity을 상속할 경우 필드들(createdDate,modifiedDate)도 column으로 인식하게 함, 앞으로 추가될 Entitiy들은 상속만 받으면 등록일/수정일 고민할 필요 X
@EntityListeners(AuditingEntityListener.class)  // class에 Auditing기능 포함시킴
public class BaseTimeEntity {

    @CreatedDate   // Entity가 생성되어 저장될 때 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate // 조회한 Entity의 값을 변경할 때 시간이 자동 저장
    private LocalDateTime modifiedDate;
}
