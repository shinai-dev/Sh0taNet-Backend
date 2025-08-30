package com.sh0tanet.backend.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.sh0tanet.backend.repository.UserRepository;
import com.sh0tanet.backend.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repo;

    public UserDetailsServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findByEmail(email.toLowerCase().trim())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new UserPrincipal(user);
    }
}