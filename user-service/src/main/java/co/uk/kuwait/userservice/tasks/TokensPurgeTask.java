package co.uk.kuwait.userservice.tasks;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.uk.kuwait.userservice.repository.PasswordResetTokenRepository;
import co.uk.kuwait.userservice.repository.VerificationTokenRepository;

@Service
@Transactional
public class TokensPurgeTask {

	@Autowired
	VerificationTokenRepository tokenRepository;

	@Autowired
	PasswordResetTokenRepository passwordTokenRepository;

	@Scheduled(cron = "${purge.cron.expression}")
	public void purgeExpired() {

		Date now = Date.from(Instant.now());

		this.passwordTokenRepository.deleteAllExpiredSince(now);
		this.tokenRepository.deleteAllExpiredSince(now);
	}
}
