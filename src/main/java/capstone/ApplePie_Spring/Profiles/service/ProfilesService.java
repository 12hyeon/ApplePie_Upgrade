package capstone.ApplePie_Spring.Profiles.service;

import capstone.ApplePie_Spring.Profiles.dto.*;

public interface ProfilesService {

    Object findUserProfiles(Long profileId);

    Object findProfiles(ProfilesFindDto profilesFindDto);

    Object findOneProfiles(OneProfilesFindDto oneProfilesFindDto);

    Object saveProfiles(Long pid, ProfilesDto profilesDto);

    Object updateProfiles(Long pid, ProfilesDto profilesDto);

    Object openProfiles(Long pid, OpenDto openDto);

    Object findAllProfiles(Long userId);
}
