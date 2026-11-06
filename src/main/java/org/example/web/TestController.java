package org.example.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/user/profile")
    public Map<String, Object> profilUtilisateur(Authentication authentication) {
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Map.of(
                "login", authentication.getName(),
                "roles", roles,
                "message", "Accès utilisateur autorisé"
        );
    }

    @GetMapping("/admin/dashboard")
    public Map<String, Object> tableauAdmin(Authentication authentication) {
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Map.of(
                "login", authentication.getName(),
                "roles", roles,
                "message", "Accès administrateur autorisé"
        );
    }
}
