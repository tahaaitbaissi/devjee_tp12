package org.example.repositories;

import org.example.entities.Role;
import org.example.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void persisteEtRelitUnUtilisateurAvecSonRole() {
        Role role = roleRepository.save(new Role(null, "ROLE_TEST_REPO"));
        userRepository.saveAndFlush(new User(null, "test_repo", passwordEncoder.encode("repo2026"), true, List.of(role)));

        Optional<User> trouve = userRepository.findByLogin("test_repo");

        assertTrue(trouve.isPresent());
        assertEquals("test_repo", trouve.get().getLogin());
        assertTrue(trouve.get().isActif());
        assertTrue(passwordEncoder.matches("repo2026", trouve.get().getMotDePasse()));
        assertEquals(1, trouve.get().getProfils().size());
    }

    @Test
    public void rechercheUnRoleParLibelle() {
        roleRepository.saveAndFlush(new Role(null, "ROLE_TEST_LIBELLE"));

        Optional<Role> role = roleRepository.findByLibelle("ROLE_TEST_LIBELLE");

        assertTrue(role.isPresent());
        assertEquals("ROLE_TEST_LIBELLE", role.get().getLibelle());
    }

    @Test
    public void loginInconnuRetourneVide() {
        assertFalse(userRepository.findByLogin("absent_tp12").isPresent());
    }

    @Test
    public void libelleInconnuRetourneVide() {
        assertFalse(roleRepository.findByLibelle("ROLE_INEXISTANT").isPresent());
    }
}
