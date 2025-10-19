package org.lunskra.logins.mongopanache;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class LoginsRepository implements ReactivePanacheMongoRepository<Logins> {

    //returns Login if still active (i.e. expiresAt is in the future)
    public Uni<Logins> findByTableId(Long tableId) {
        return find("tableNumber = ?1 and expiresAt > ?2", tableId, Instant.now()).firstResult();
    }

    //returns Login if still active (i.e. expiresAt is in the future)
    public Uni<Logins> findByLoginToken(UUID loginToken) {
        return find("token", loginToken.toString()).firstResult().map(this::filterAfterExpiry);
    }

    public Logins filterAfterExpiry(Logins logins) {
        return (logins == null || Instant.now().isAfter(logins.getExpiresAt())) ?
                null :
                logins;
    }
}
