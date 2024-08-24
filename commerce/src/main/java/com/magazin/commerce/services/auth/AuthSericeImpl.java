package com.magazin.commerce.services.auth;

import com.magazin.commerce.dto.SignupRequest;
import com.magazin.commerce.dto.UserDto;
import com.magazin.commerce.entity.User;
import com.magazin.commerce.enums.UserRole;
import com.magazin.commerce.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthSericeImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto createUser(SignupRequest signupRequest){
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));

        user.setName(signupRequest.getName());
        user.setRole(signupRequest.getRole());
        User createUser = userRepository.save(user);
        UserDto userDto= new UserDto();
        userDto.setId(createUser.getId());
        return userDto;

    }
    public Boolean hasUserWithEmail(String email){
        return userRepository.findFirstByEmail(email).isPresent();
    }
    @PostConstruct
    public void createAdminAccount(){
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if(null == adminAccount){
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }
    }
}
