package org.lunskra.logins.redis;

import io.quarkus.redis.client.RedisClientName;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands;
import io.quarkus.redis.datasource.value.ReactiveValueCommands;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.lunskra.logins.LoginsService;

import java.util.UUID;


// @ApplicationScoped //comment out if other implementation of service is used
public class RedisLoginsService implements LoginsService {

    private final ReactiveValueCommands<Long, UUID> tokens;
    private final ReactiveKeyCommands<Long> keys;


    @Inject
    public RedisLoginsService(@RedisClientName("logins") ReactiveRedisDataSource reactiveRedisDataSource) {
        this.tokens = reactiveRedisDataSource.value(Long.class, UUID.class);
        this.keys = reactiveRedisDataSource.key(Long.class);
    }


    @Override
    public Uni<UUID> createNewLogin(Long tableId) {
        UUID token = UUID.randomUUID();
        return tokens.set(tableId, token)
                .chain(v -> keys.expire(tableId, 20))
                .map(v -> token);
    }

    @Override
    public Uni<Boolean> hasLogin(long tableId) {
        return keys.exists(tableId);
    }
}
