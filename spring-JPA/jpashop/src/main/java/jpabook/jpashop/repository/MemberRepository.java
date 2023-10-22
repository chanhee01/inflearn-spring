package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Repository로 스프링 빈 등록(컴포넌트 스캔의 대상)
@RequiredArgsConstructor
public class MemberRepository {

    @Autowired
    private EntityManager em;

    /*
    public MemberRepository(EntityManager em) {
        this.em = em;
    }    ->        @RequiredArgsConstructor로 대체

     */

    public void save(Member member) {
        em.persist(member); // JPA가 회원을 저장
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); // 멤버를 찾아서 반환
    }

    public List<Member> findAll() {
        List<Member> result = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        // (JPQL, 반환타입).getResultList() 순으로 작성 (JPQL에서 from의 대상은 entity)
        return result;
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList(); // 이름을 가지고 회원들을 조회
    }
}
