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

@SpringBootTest
@Transactional
@Rollback
class UserServiceImplTest {
    @Autowired private UserRepository userRepository;


    @Test
    public void saveUser() {
        SignupDto signupDto = SignupDto.builder()
                .email("test")
                .password("test")
                .name("test")
                .nickname("test")
                .corp(true)
                .build();

        User user = signupDto.toUser();
        User findUser = userRepository.save(user);
        Assertions.assertThat(findUser.getNickname().equals("test"));

    }
}