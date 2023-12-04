package com.jpatest.demo.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpatest.demo.model.user.TADM00100VO;

import jakarta.transaction.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<TADM00100VO, Long> {

    Page<TADM00100VO> findBySeqOrEnoContaining(@Param("seq") Long Seq, @Param("eno") String eno, PageRequest pageable);
    Page<TADM00100VO> findByEnoContaining(@Param("eno") String eno, PageRequest pageable);
    Page<TADM00100VO> findBySeq(@Param("seq") Long Seq, PageRequest pageable);
    // 띄워쓰기 주의 할 것
    @Query(value = "SELECT * FROM tadm00100 u "
                    + "WHERE (:seq IS NULL OR u.seq = :seq) "
                    + "AND (:eno IS NULL OR u.eno LIKE %:eno%)"       
            , nativeQuery = true
            )
    Page<TADM00100VO> findBySeqOrEnoTest(@Param("seq") Long Seq, @Param("eno") String eno, PageRequest pageable);

}
