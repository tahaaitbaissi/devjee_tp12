package org.example.services;

import org.example.entities.Role;
import org.example.entities.User;
import org.example.repositories.RoleRepository;
import org.example.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CustomUserDetailsServiceTest {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void chargeLeCompteAdministrateur() {
        UserDetails details = userDetailsService.loadUserByUsername("hamza");

        assertTrue(details.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertTrue(details.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(passwordEncoder.matches("admin2026", details.getPassword()));
    }

    @Test
    public void chargeLeCompteUtilisateur() {
        UserDetails details = userDetailsService.loadUserByUsername("salma");

        assertTrue(details.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertFalse(details.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertTrue(passwordEncoder.matches("user2026", details.getPassword()));
    }

    @Test
    public void chargeUnUtilisateurPersiste() {
        Role role = roleRepository.save(new Role(null, "ROLE_TEST_JWT"));
        userRepository.saveAndFlush(new User(null, "test_jwt", passwordEncoder.encode("secret2026"), true, List.of(role)));

        UserDetails details = userDetailsService.loadUserByUsername("test_jwt");

        assertEquals("test_jwt", details.getUsername());
        assertTrue(details.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TEST_JWT")));
        assertTrue(passwordEncoder.matches("secret2026", details.getPassword()));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void utilisateurInconnuEstRefuse() {
        userDetailsService.loadUserByUsername("inconnu_tp12");
    }
}
