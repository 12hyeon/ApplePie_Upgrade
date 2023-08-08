package capstone.ApplePie_Spring.User.service;

import capstone.ApplePie_Spring.User.dto.LoginDto;
import capstone.ApplePie_Spring.User.dto.ProfileDto;
import capstone.ApplePie_Spring.User.dto.SignupDto;

public interface UserService {
    Object signup(SignupDto signupDto) throws Exception;
    Object login(LoginDto loginDto);
    Object delete(Long userId);
    Object findProfile(Long userId);
    Object updateProfile(Long userId, ProfileDto profileDto);
}
