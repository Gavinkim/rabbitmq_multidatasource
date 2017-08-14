package com.example.queuedemo.repository.mysql;

import com.example.queuedemo.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by gs on 2017. 8. 11..
 */
public interface MysqlMemberRepository extends JpaRepository<Member,Long> {
}
