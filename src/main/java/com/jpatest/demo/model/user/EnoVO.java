package com.jpatest.demo.model.user;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="TADM00100")
public class EnoVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false, unique = true, length = 200)
    private String eno;

    @Column(nullable = false, length = 512)
    private String enoPw;

    @Column(nullable = true, length = 11)
    private String celph;

    @Column(nullable = true, length = 200)
    private String email;

    @Column(nullable = true, length = 8)
    private String cretDt;

    @Column(nullable = true, length = 6)
    private String cretTm;

    @Column(nullable = true, length = 8)
    private String chgDt;

    @Column(nullable = true, length = 6)
    private String chgTm;

    @Builder
    public EnoVO(String eno, String enoPw, String celph, String email
    , String cretDt, String cretTm, String chgDt, String chgTm){
        this.eno = eno;
        this.enoPw = enoPw;
        this.celph = celph;
        this.email = email;
        this.cretDt = cretDt;
        this.cretTm = cretTm;
        this.chgDt = chgDt;
        this.chgTm = chgTm;
    }
}
