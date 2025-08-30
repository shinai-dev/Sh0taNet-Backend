package com.sh0tanet.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_users_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_users_username", columnNames = "username")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String firstName;

    @Column(nullable = false, length = 80)
    private String lastName;

    @Column(nullable = false, length = 160)
    private String email;

    @Column(nullable = false, length = 80)
    private String username; // ✅ Nuevo campo para evitar el error

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Role> roles;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "avatar_url")
    private String avatarUrl;

    public static User create(String firstName, String lastName, String email, String encodedPassword,
            Set<Role> roles) {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email.toLowerCase().trim())
                .username(email.toLowerCase().trim()) // ✅ Usamos el email como username por defecto
                .password(encodedPassword)
                .roles(roles)
                .enabled(true)
                .createdAt(Instant.now())
                .build();
    }
}
