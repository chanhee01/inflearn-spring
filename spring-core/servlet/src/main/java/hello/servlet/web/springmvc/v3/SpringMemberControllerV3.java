package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    // @RequestMapping(value = "/new-form", method = RequestMethod.GET) // method를 통해 GET으로만 호출 가능
    @GetMapping("new-form")
    public String newForm() {
        return "new-form"; // modelAndView도 되지만 스프링 MVC는 유연하기에 그냥 return 해도 됨
    }

    @PostMapping("/save")
    // @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestParam("username") String username,
                             @RequestParam("age") int age,
                             Model model) {
        /*String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
         */ // 애노테이션을 이욯해서 생략 가능

        Member member = new Member(username, age);
        memberRepository.save(member);

        model.addAttribute("member", member); // 모델에 담기
        return "save-result"; // view 이름 반환
    }

    @GetMapping
    // @RequestMapping(method = RequestMethod.GET)
    public String members(Model model) {

        List<Member> members = memberRepository.findAll();

        model.addAttribute("members", members);
        return "members";
    }

    // new-form하고 list는 단순 조회니 GET을 하고
    // save는 저장이기때문에 POST를 사용
}
