package capstone.ApplePie_Spring.Team.service;

import capstone.ApplePie_Spring.Team.dto.FindMemberDto;
import capstone.ApplePie_Spring.Team.dto.MemberDto;

public interface MemberService {
    Object save(MemberDto memberDto);
    Object getMembers(FindMemberDto findMemberDto);
}
