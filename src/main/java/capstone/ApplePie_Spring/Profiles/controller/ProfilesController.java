package capstone.ApplePie_Spring.Profiles.controller;

import capstone.ApplePie_Spring.Profiles.dto.*;
import capstone.ApplePie_Spring.Profiles.service.ProfilesService;
import capstone.ApplePie_Spring.User.dto.ProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/profiles")
@RestController
public class ProfilesController {

    private final ProfilesService profilesService;

    @PostMapping // get
    public ResponseEntity<Object> findProject(@RequestBody ProfilesFindDto profilesFindDto) {
        return new ResponseEntity<>(profilesService.findProfiles(profilesFindDto), HttpStatus.OK);
    }

    @PostMapping("/one") // get
    public ResponseEntity<Object> findProject(@RequestBody OneProfilesFindDto oneProfilesFindDto) {
        return new ResponseEntity<>(profilesService.findOneProfiles(oneProfilesFindDto), HttpStatus.OK);
    }


    @PostMapping("/{pid}")
    public ResponseEntity<Object> saveProject(@PathVariable Long pid, @RequestBody ProfilesDto profilesDto) {
        return new ResponseEntity<>(profilesService.saveProfiles(pid, profilesDto), HttpStatus.OK);
    }

    @PutMapping("/{pid}")
    public ResponseEntity<Object> updateProject(@PathVariable Long pid, @RequestBody ProfilesDto profilesDto) {
        return new ResponseEntity<>(profilesService.updateProfiles(pid, profilesDto), HttpStatus.OK);
    }

    // open 상태 변경
    @PutMapping("/open/{pid}")
    public ResponseEntity<Object> updateOpenLesson(@PathVariable Long pid, @RequestBody OpenDto openDto) {
        return new ResponseEntity<>(profilesService.openProfiles(pid, openDto), HttpStatus.OK);
    }
}
