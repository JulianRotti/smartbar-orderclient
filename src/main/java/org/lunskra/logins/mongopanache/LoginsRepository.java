package org.lunskra.logins.mongopanache;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoginsRepository implements ReactivePanacheMongoRepository<Logins> {
    public Uni<Logins> findByTableId(Long tableId) {
        return find("tableNumber", tableId).firstResult();
    }
}
