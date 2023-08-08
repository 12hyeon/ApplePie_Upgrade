package capstone.ApplePie_Spring.Team.controller;


import capstone.ApplePie_Spring.Team.dto.*;
import capstone.ApplePie_Spring.Team.service.MemberService;
import capstone.ApplePie_Spring.Team.service.TeamService;
import capstone.ApplePie_Spring.Team.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/teams")
@RestController
@RequiredArgsConstructor
public class TeamController {
    
    private final TeamService teamService;
    private final VolunteerService volunteerService;
    private final MemberService memberService;

    // 팀 생성
    @PostMapping("/{uid}")
    public ResponseEntity<Object> createTeam(@PathVariable Long uid, @RequestBody TeamDto teamDto){
        return new ResponseEntity<>(teamService.save(uid, teamDto), HttpStatus.OK);
    }

    // 팀 수정
    @PutMapping("/{uid}")
    public ResponseEntity<Object> updateTeam(@PathVariable Long uid, @RequestBody TeamDto teamDto){
        return new ResponseEntity<>(teamService.update(uid, teamDto), HttpStatus.OK);
    }

    // 팀 모집 취소
    @DeleteMapping("/{uid}")
    public ResponseEntity<Object> cancelTeam(@PathVariable Long uid, @RequestBody TeamUpdateDto teamUpdateDto) {
        return new ResponseEntity<>(teamService.cancel(uid, teamUpdateDto), HttpStatus.OK);
    }

    // 팀에 지원
    @PostMapping("/volunteer")
    public ResponseEntity<Object> createVolunteer(@RequestBody VolunteerDto volunteerDto) {
        return new ResponseEntity<>(volunteerService.save(volunteerDto), HttpStatus.OK);
    }

    // 지원 취소
    @DeleteMapping("/volunteer")
    public ResponseEntity<Object> cancelVolunteer(@RequestBody CancelVolunteerDto volunteerDto) {
        return new ResponseEntity<>(volunteerService.cancel(volunteerDto), HttpStatus.OK);
    }

    // 관련된 모든 팀 uid -> teamId
    @GetMapping("/{uid}")
    public ResponseEntity<Object> getMyTeam(@PathVariable Long uid) {
        return new ResponseEntity<>(teamService.findTeam(uid), HttpStatus.OK);
    }

    // 지원자 리스트 조회
    @PostMapping("/volunteer/{uid}") //get
    public ResponseEntity<Object> findVolunteers(@PathVariable Long uid, @RequestBody FindVolunteerDto volunteerDto) {
        return new ResponseEntity<>(volunteerService.getVolunteers(uid, volunteerDto), HttpStatus.OK);
    }

    // 팀원 처리
    @PostMapping("/member")
    public ResponseEntity<Object> updateVolunteerToMember(@RequestBody MemberDto memberDto) {
        return new ResponseEntity<>(memberService.save(memberDto), HttpStatus.OK);
    }

    // 팀의 팀원들 조회
    @PostMapping("/member/all") // get
    public ResponseEntity<Object> getMember(@RequestBody FindMemberDto findMemberDto) {
        return new ResponseEntity<>(memberService.getMembers(findMemberDto), HttpStatus.OK);
    }

}
