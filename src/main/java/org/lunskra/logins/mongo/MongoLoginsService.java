package org.lunskra.logins.mongo;

import io.quarkus.mongodb.MongoClientName;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.bson.Document;
import org.lunskra.logins.LoginsService;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

// @ApplicationScoped //comment out if other implementation of service is used
public class MongoLoginsService implements LoginsService {

    private final ReactiveMongoClient reactiveMongoClient;

    @Inject
    public MongoLoginsService(@MongoClientName("logins") ReactiveMongoClient reactiveMongoClient) {
        this.reactiveMongoClient = reactiveMongoClient;
    }

    @Override
    public Uni<UUID> createNewLogin(Long tableId) {
        final UUID token = UUID.randomUUID();
        final Instant expiresAtInstant = Instant.now().plusSeconds(20);
        final Date expiresAt = Date.from(expiresAtInstant);
        Document loginDocument = new Document()
                .append("tableId", tableId)
                .append("token", token.toString())
                .append("expiresAt", expiresAt);

        return getLoginsCollection().insertOne(loginDocument).map(result -> token);
    }

    @Override
    public Uni<Boolean> hasLogin(long tableId) {
        return getLoginsCollection().find(new Document().append("tableId", tableId))
                .collect().asList().map(documents -> !documents.isEmpty());
    }

    private ReactiveMongoCollection<Document> getLoginsCollection() {
        return reactiveMongoClient.getDatabase("smartbar").getCollection("logins-timed");
    }
}
