package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController // api 설계할 때에는 RestController 사용
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members") // 조회는 GetMapping
    public List<Member> membersV1() {
        return memberService.findMembers();
        // 엔티티를 직접 사용하면 엔티티에 있는 정보들이 다 외부로 노출
        // 사진처럼 order 내역까지 다 노출됨 (지금은 주문이 없긴 하지만)
        // 그냥 api에서는 엔티티 사용 절대 금지
    }

    @GetMapping("/api/v2/members")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        // Member 리스트를 MemberDto 리스트로 바꾼 것

        return new Result(collect);

    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
    // 리스트는 바로 내면 안되고 이렇게 한번 감싸주고 보내야한다.
    // data가 배열로 들어감
    // 아까는 배열에 값들이 들어갔는데 지금은 {} 안에 data가 배열로 들어간다.

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @PostMapping("api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        // api는 별도의 Dto를 만들어야함. 그렇지 않고 Entity에 @NotEmpty 등 제약을 걸면 큰 장애 발생
        // 로그인을 할 때에도 네이버로 가입, 구글로 가입 ~~ 등등 많은데 엔티티 하나로 감당 안됨
        // api를 할 때에는 무조건 별도의 Dto를 만들어 사용하자
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
    // 별도의 Dto를 만들어서 바인딩 해줌
    // 별도로 검증 가능, 엔티티가 변경되어도 api 스펙이 변하지 않음
    // 무조건 별도의 Dto 클래스를 만들어서 설계 해야함
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("api/v2/members/{id}") // 수정은 PutMapping을 사용
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id, request.getName()); // 서비스로직에 update 로직 있음
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
