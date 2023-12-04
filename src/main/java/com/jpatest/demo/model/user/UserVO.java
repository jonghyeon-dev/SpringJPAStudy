package com.jpatest.demo.model.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVO {
    private String userId;
    private String userPw;
    private String cretDate;
    private String cretTime;
    private String chgDate;
    private String chgTime;

    @Builder
    public UserVO(String userId, String userPw, String cretDate, String cretTime
    , String chgDate, String chgTime){
        this.userId = userId;
        this.userPw = userPw;
        this.cretDate = cretDate;
        this.cretTime = cretTime;
        this.chgDate = chgDate;
        this.chgTime = chgTime;
    }
    
}
