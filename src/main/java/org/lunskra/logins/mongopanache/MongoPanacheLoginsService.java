package org.lunskra.logins.mongopanache;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.lunskra.logins.LoginsService;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

// @ApplicationScoped //comment out if other implementation of service is used
public class MongoPanacheLoginsService implements LoginsService {

    private final LoginsRepository loginsRepository;

    @Inject
    public MongoPanacheLoginsService(LoginsRepository loginsRepository) {
        this.loginsRepository = loginsRepository;
    }

    @Override
    public Uni<UUID> createNewLogin(Long tableId) {
        final UUID token = UUID.randomUUID();
        final Instant expiresAt = Instant.now().plusSeconds(20);
        final Logins login = new Logins(tableId, token.toString(), expiresAt);
        return loginsRepository.persist(login).map(ignored -> token);
    }

    @Override
    public Uni<Boolean> hasLogin(long tableId) {
        return loginsRepository.findByTableId(tableId).map(Objects::nonNull);
    }
}
