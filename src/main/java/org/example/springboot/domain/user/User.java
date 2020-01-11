package org.example.springboot.domain.user;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springboot.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)    //2.0방식에서 url주소들을 enum으로 대체, JPA로 DV로 저장할 때 Enum 값을 어떤 형태로 저장할지 결정(기본적으로 int로 된 숫자가 저장) 숫자로 저장되면 db로 확인할 때 그 값이 무슨 코드를 의미하는지 알 수 없음 -> String으로 저장
    @Column(nullable = false)
    private Role role;  // 각 사용자의 권한을 관리할 Enum class

    @Builder
    public User(String name, String email, String picture, Role role){
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture){
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
