package com.gametags.gametags.infrastructure;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.gametags.gametags.infrastructure.daos.UserDAO;
import com.gametags.gametags.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    UserDAO user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
  }

  private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(List<String> roles) {
    return roles.stream().map(SimpleGrantedAuthority::new).toList();
  }
}
