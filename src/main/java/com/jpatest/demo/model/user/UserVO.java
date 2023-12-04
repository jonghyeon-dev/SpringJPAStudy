package com.jpatest.demo.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="TUSR00100")
public class UserVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false, unique = true, length = 200)
    private String userId;

    @Column(nullable = false, length = 512)
    private String userPw;

    private String celph;

    private String email;

    private String cretDate;

    private String cretTime;

    private String chgDate;

    private String chgTime;

    @Builder
    public UserVO(String userId, String userPw, String celph, String email, String cretDate, String cretTime
    , String chgDate, String chgTime){
        this.userId = userId;
        this.userPw = userPw;
        this.celph = celph;
        this.email = email;
        this.cretDate = cretDate;
        this.cretTime = cretTime;
        this.chgDate = chgDate;
        this.chgTime = chgTime;
    }
    
}
