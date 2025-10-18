package org.lunskra.logins.mongopanache;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.Instant;

@MongoEntity(database = "smartbar", collection = "logins-timed")
public class Logins {

    @BsonProperty("tableNumber")
    private Long tableId;
    private String token;
    private Instant expiresAt;

    public Logins(Long tableId, String token, Instant expiresAt) {
        this.tableId = tableId;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public Logins() {
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
}
