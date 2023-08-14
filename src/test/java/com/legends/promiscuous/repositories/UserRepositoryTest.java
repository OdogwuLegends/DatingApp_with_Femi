package com.legends.promiscuous.repositories;

import com.legends.promiscuous.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void readByEmail(){
        User firstUser = new User();
        firstUser.setEmail("odogwulegends@gmail.com");
        firstUser.setPassword("12345");
        userRepository.save(firstUser);

        User secondUser = new User();
        secondUser.setEmail("legjnr@gmail.com");
        secondUser.setPassword("34567");
        userRepository.save(secondUser);

        User thirdUser = new User();
        thirdUser.setEmail("leg@gmail.com");
        thirdUser.setPassword("09876");
        userRepository.save(thirdUser);


        Optional<User> foundUser = userRepository.findByEmail("legjnr@gmail.com");
        assertThat(foundUser).isPresent();
        assertThat(foundUser).isNotNull();
    }
}