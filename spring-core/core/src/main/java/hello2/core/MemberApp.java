package hello2.core;

import hello2.core.member.Grade;
import hello2.core.member.Member;
import hello2.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {
        // AppConfig appConfig = new AppConfig();
        // MemberService memberService = appConfig.memberService();
        // MemberService memberService = new MemberServiceImpl();
        // 위에 코드는 스프링 안쓰고 자바 코드로만 진행할 때

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // AppConfig에 있는 설정을 가지고 스프링을 활용하는 것
        // ApplicationContext는 스프링 컨테이너를 사용하기 위해 만들어줘야 하는 것

        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        // AppConfig에서 꺼내는데 앞에 name은 메서드 이름, 두번째 MemberService.class는 반환타입이다.

        Member member = new Member(1L, "memberA", Grade.VIP);
        // ID가 1이 아닌 1L인 이유는 Long 타입이어서 그렇고 L 안 붙이면 컴파일 에러 발생
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());
    }
}
