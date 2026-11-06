package org.example.services;

import org.example.entities.User;
import org.example.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + login));

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getMotDePasse(),
                user.isActif(),
                true,
                true,
                true,
                user.getProfils().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getLibelle()))
                        .collect(Collectors.toList())
        );
    }
}
