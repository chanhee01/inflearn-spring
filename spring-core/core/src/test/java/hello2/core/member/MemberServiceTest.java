package hello2.core.member;

import hello2.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    MemberService memberService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void join() {
        //given (~~환경에서)
        Member member = new Member(1L, "memberA", Grade.VIP);

        //when (~~란 상황이 주어지면)
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        //then (이렇게 실행한다.)
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
