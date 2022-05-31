package com.project.userservice.security;

import com.project.userservice.entities.UserEntity;
import com.project.userservice.exceptions.BadRequestException;
import com.project.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetails implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    final UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("User doesn't exist by email : " + email));

    if (user == null) {
      throw new UsernameNotFoundException("User '" + email + "' not found");
    }

    return org.springframework.security.core.userdetails.User//
        .withUsername(email)//
        .password(user.getPassword())//
        .authorities(user.getRoles())//
        .accountExpired(false)//
        .accountLocked(false)//
        .credentialsExpired(false)//
        .disabled(false)//
        .build();
  }

}
