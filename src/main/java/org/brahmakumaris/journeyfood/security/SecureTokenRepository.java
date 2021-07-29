package org.brahmakumaris.journeyfood.security;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SecureTokenRepository extends JpaRepository<SecureToken, Long> {
	SecureToken findByToken(final String token);
    Long removeByToken(String token);
}
