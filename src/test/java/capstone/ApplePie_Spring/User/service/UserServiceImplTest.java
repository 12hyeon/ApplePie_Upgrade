package capstone.ApplePie_Spring.User.service;

import capstone.ApplePie_Spring.User.domain.User;
import capstone.ApplePie_Spring.User.dto.SignupDto;
import capstone.ApplePie_Spring.User.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

// @SpringBootTest // 정의된 모든 빈을 생성 -> 통합 테스트에 이용
@RunWith(MockitoJUnitRunner.class) // Mock 사용할 수 있게
@TestPropertySource(locations = "/application-test.properties") // 다른 db와 연결
class UserServiceImplTest {
    
    @MockBean // 테스트 분리 -> 단위 테스트 : 실제 동작에 관여하지 않는 빈은 필요 없음
    private UserRepository userRepository;

    @MockBean // Spring context에 올라감
    private ProfileRepository profileRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl(userRepository, profileRepository, passwordEncoder);

    @DisplayName("존재하는 유저는 프로필 조회가 가능하다.")
    @Test
    public void saveUser() {

        // given
        SignupDto signupDto = SignupDto.builder()
                .email("test")
                .password("test")
                .name("test")
                .nickname("test")
                .corp(true)
                .build();

        User user = signupDto.toUser();
        User saveUser = userService.save(user);

        // when
        User findUser = userService.findProfile(saveUser.getId());

        // then
        Assertions.assertThat(findUser.getNickname().equals("test"));

    }
}