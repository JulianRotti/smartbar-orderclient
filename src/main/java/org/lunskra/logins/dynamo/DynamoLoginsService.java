package org.lunskra.logins.dynamo;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.lunskra.logins.LoginsService;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import javax.management.Attribute;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class DynamoLoginsService implements LoginsService {

    private final DynamoDbAsyncClient dynamoDbAsyncClient;

    @Inject
    public DynamoLoginsService(DynamoDbAsyncClient dynamoDbAsyncClient) {
        this.dynamoDbAsyncClient = dynamoDbAsyncClient;
    }


    @Override
    public Uni<UUID> createNewLogin(Long tableId) {
        final UUID token = UUID.randomUUID();
        final Long expiresAt = Instant.now().plusSeconds(60).toEpochMilli();

        Map<String, AttributeValue> login = new HashMap<>();
        login.put("tableId", AttributeValue.builder().n(tableId.toString()).build());
        login.put("token", AttributeValue.builder().s(token.toString()).build());
        login.put("expiresAt", AttributeValue.builder().n(expiresAt.toString()).build());
        return Uni.createFrom()
                .completionStage(
                    dynamoDbAsyncClient.putItem(PutItemRequest.builder().tableName("Logins").item(login).build()))
                .map(ignored -> token);
    }

    @Override
    public Uni<Boolean> hasLogin(long tableId) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("tableId", AttributeValue.builder().n(Long.toString(tableId)).build());
        final var existingLogin = dynamoDbAsyncClient.getItem(GetItemRequest.builder().tableName("Logins").key(key).build());
        return Uni.createFrom().completionStage(existingLogin).map(response -> response.hasItem());
    }
}
