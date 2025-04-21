package com.example.demo.Userfile;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByUseremail(String useremail) {
        return userRepository.findByUseremail(useremail);
    }

    @Override
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
        User user = userRepository.findByUseremail(useremail)
            .orElseThrow(() -> new UsernameNotFoundException("用戶名不存在"));
        return new org.springframework.security.core.userdetails.User(user.getUseremail(), user.getPassword(), new ArrayList<>());
    }

    public void registerNewUser(User user) throws Exception {
        if (userRepository.findByUseremail(user.getUseremail()).isPresent()) {
            throw new Exception("用戶名已存在");
        }
        // 需要在这里对密码进行编码
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }  
}