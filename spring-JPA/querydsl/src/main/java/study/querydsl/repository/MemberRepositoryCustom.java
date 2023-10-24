package study.querydsl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.querydsl.dto.MemberCond;
import study.querydsl.dto.MemberTeamDto;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberTeamDto> search(MemberCond condition);
    Page<MemberTeamDto> searchPageSimple(MemberCond condition, Pageable pageable);
    Page<MemberTeamDto> searchPageComplex(MemberCond condition, Pageable pageable);
}
