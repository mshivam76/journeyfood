package org.brahmakumaris.journeyfood.security;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VerificationToken {
	@Id
	private Long id;
}
