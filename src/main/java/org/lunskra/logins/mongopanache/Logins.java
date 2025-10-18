package org.lunskra.logins.mongopanache;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import io.smallrye.mutiny.Uni;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.Instant;

@MongoEntity(database = "smartbar", collection = "logins-timed")
public class Logins extends ReactivePanacheMongoEntity {

    @BsonProperty("tableNumber")
    public Long tableId;
    public String token;
    public Instant expiresAt;

    public Logins(Long tableId, String token, Instant expiresAt) {
        this.tableId = tableId;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public Logins() {
    }

    public static Uni<Logins> findByTableId(Long tableId) {
        return find("tableNumber", tableId).firstResult();
    }
}
