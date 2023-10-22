package jpabook.jpashop.service;

import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 중요!! 까먹지 않기
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /*
    @Autowired // 스프링이 빈에 등록되어 있는 리포지토리를 인젝션해줌
    원래는 @PersistenceContext인데 스프링 데이터 JPA에서 @Autowired를 지원해주는 것
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository; // 생성자 주입
    }
    */  // ->   @RequiredArgsConstructor로 대체

    /**
     *  회원 가입
     */
    @Transactional // 쓰기에는 무조건 readOnly false(default)로 해야함
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    // @Transactional(readOnly = true) // 단순한 읽기는 readOnly = true
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
