package capstone.ApplePie_Spring.User.service;

import capstone.ApplePie_Spring.User.dto.ProfileDto;
import capstone.ApplePie_Spring.User.response.ResponseProfile;
import capstone.ApplePie_Spring.User.response.ResponseLogin;
import capstone.ApplePie_Spring.User.response.ResponseSignup;
import capstone.ApplePie_Spring.User.response.ResponseUser;
import capstone.ApplePie_Spring.User.domain.Profile;
import capstone.ApplePie_Spring.User.domain.User;
import capstone.ApplePie_Spring.User.dto.LoginDto;
import capstone.ApplePie_Spring.User.dto.SignupDto;
import capstone.ApplePie_Spring.User.repository.ProfileRepository;
import capstone.ApplePie_Spring.User.repository.UserRepository;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;

    private static final Integer STATUS = 1;

    @Override
    public Object signup(SignupDto signupDto) {
        if (validateDuplicateEmail(signupDto.getEmail())) { // email 중복
            return new ResponseUser(ExceptionCode.SIGNUP_DUPLICATED_EMAIL);
        } else if (validateDuplicateNickname(signupDto.getNickname())) { // nickname 중복
            return new ResponseUser(ExceptionCode.SIGNUP_DUPLICATED_NICKNAME);
        }

        String encodedPassword = passwordEncoder.encode(signupDto.getPassword());
        signupDto.setPassword(encodedPassword);

        User user = signupDto.toUser();
        userRepository.save(user);

        Profile profile = Profile.builder()
                .signupDto(signupDto)
                .user(user)
                .build();
        profileRepository.save(profile);
        return new ResponseSignup(ExceptionCode.SIGNUP_CREATED_OK, user.getId(), profile.getId());
    }

    @Override
    public Object login(LoginDto loginDto) {
        Optional<User> findUser = userRepository.findByEmailAndStatus(loginDto.getEmail(), STATUS);
        if (findUser.isEmpty()) {
            return new ResponseUser(ExceptionCode.LOGIN_NOT_FOUND_EMAIL);
        }

        User user = findUser.get();
        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) { // 비밀번호 일치
            Profile profile = profileRepository.findByUserIdAndStatus(user.getId(), STATUS).get();
            return new ResponseLogin(ExceptionCode.LOGIN_OK, user.getId(), profile.getId(), user.isCorp());
        }
        return new ResponseUser(ExceptionCode.LOGIN_NOT_FOUND_PW);
    }

    @Override
    public Object delete(Long userId) {
        Optional<User> findUser = userRepository.findByIdAndStatus(userId, STATUS);
        Optional<Profile> findProfile = profileRepository.findByUserIdAndStatus(userId, STATUS);
        if (findUser.isEmpty() || findProfile.isEmpty()) {
            return new ResponseUser(ExceptionCode.USER_ERROR);
        }
        findUser.get().delete();
        findProfile.get().delete();
        return new ResponseUser(ExceptionCode.WITHDRAW_USER_OK);
    }

    private boolean validateDuplicateEmail(String email) {
        return userRepository.existsByEmailAndStatus(email, STATUS);
    }

    private boolean validateDuplicateNickname(String nickname) {
        return userRepository.existsByNicknameAndStatus(nickname, STATUS);
    }


    @Override
    public Object findProfile(Long userId) {
        Optional<Profile> findProfile = profileRepository.findByUserIdAndStatus(userId, STATUS);
        if (findProfile.isEmpty()) {
            Optional<User> findUser = userRepository.findByIdAndStatus(userId, STATUS);
            return new ResponseUser(ExceptionCode.USER_PROFILE_FIND_NOT);
        }
        Profile profile = findProfile.get();
        User user = profile.getUser();
        return new ResponseProfile(ExceptionCode.USER_PROFILE_FIND_OK, user, profile);
    }

    @Override
    public Object updateProfile(Long userId, ProfileDto profileDto) {
        Optional<Profile> findProfile = profileRepository.findByUserIdAndStatus(userId, STATUS);
        if (findProfile.isEmpty()) {
            return new ResponseUser(ExceptionCode.USER_ERROR);
        }
        try {
            Profile profile = findProfile.get();
            profile.update(profileDto); // 변경 감지
            return new ResponseUser(ExceptionCode.USER_PROFILE_UPDATE_OK);
        } catch (Exception exception) {
            return new ResponseUser(ExceptionCode.USER_ERROR);
        }
    }
}
