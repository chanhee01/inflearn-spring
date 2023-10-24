package study.querydsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.querydsl.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    // 스프링 데이터 JPA에서 메서드 이름으로 자동 쿼리 만들어줌
    List<Member> findByUsername(String username);
}
