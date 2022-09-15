package com.kwan.springboot.domain.user;

import com.kwan.springboot.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public User update(String name,String picture) {
        this.name=name;
        this.picture=picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    @Getter
    @RequiredArgsConstructor
    public enum Role{
        GUEST("ROLE_GUEST","손님"),
        USER("ROLE_USER","일반 사용자");

        private final String key;
        private final String title;
    }
}
