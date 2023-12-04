package com.jpatest.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpatest.demo.model.user.UserVO;

import jakarta.transaction.Transactional;

@Transactional
public interface UserLoginRepository extends JpaRepository<UserVO, Long> {
    // 띄워쓰기 주의 할 것
    @Query(value = "SELECT * FROM tusr00100 u "
                    + "WHERE u.USER_ID = :userId "
                    + "AND u.USER_PW = :userPw"       
            , nativeQuery = true
            )
    UserVO CheckUserLogin(@Param("userId") String userId, @Param("userPw") String userPw);

}
