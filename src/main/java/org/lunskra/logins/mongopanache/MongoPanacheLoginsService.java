package org.lunskra.logins.mongopanache;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.lunskra.logins.LoginsService;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class MongoPanacheLoginsService implements LoginsService {

    @Override
    public Uni<UUID> createNewLogin(Long tableId) {
        final UUID token = UUID.randomUUID();
        final Instant expiresAt = Instant.now().plusSeconds(20);
        final Logins login = new Logins(tableId, token.toString(), expiresAt);
        return login.persist().map(ignored -> token);
    }

    @Override
    public Uni<Boolean> hasLogin(long tableId) {
        return Logins.findByTableId(tableId).map(Objects::nonNull);
    }
}
