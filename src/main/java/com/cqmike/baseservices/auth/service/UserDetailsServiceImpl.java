//package com.cqmike.baseservices.auth.service;
//
//import com.cqmike.baseservices.auth.dto.JwtUser;
//import com.cqmike.baseservices.auth.entity.User;
//import com.cqmike.baseservices.auth.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
///**
// * @program: baseServices
// * @description: security接口
// * @author: chen qi
// * @create: 2020-11-25 22:55
// **/
//
//@Service
//@RequiredArgsConstructor
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        User user = userRepository.findUserByUsername(s).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
//        return new JwtUser(user);
//    }
//
//}
