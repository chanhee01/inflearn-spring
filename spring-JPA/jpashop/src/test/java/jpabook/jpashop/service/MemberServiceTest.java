package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class) // 스프링이랑 같이 엮어서 junit을 실행하는 것
@SpringBootTest // 스프링부트를 띄운 상태로 테스트(이게 없으면 Autowired가 다 실패)
@Transactional // 트랜잭션
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = memberService.join(member);

        // then
        // em.flush(); -> 롤백을 하지만 db에 쿼리를 날려서 insert문 보는 것
        Assert.assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        try {
            memberService.join(member2); // 예외가 발생해야 한다
        } catch (IllegalStateException e) {
            return;
        }

        // then
        Assert.fail("예외가 발생해야 한다.");
    }
}