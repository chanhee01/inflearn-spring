package study.querydsl.dto;

import lombok.Data;

@Data
public class MemberCond {
    // 회원명, 팀명, 나이(ageGoe, ageLoe)

    private String username;
    private String teamName;
    private Integer ageGoe; // 크거나 같거나
    private Integer ageLoe; // 작거나 같거나
}
