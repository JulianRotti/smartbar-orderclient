package org.lunskra.logins;

import io.smallrye.mutiny.Uni;

import java.util.UUID;

public interface LoginsService {

    Uni<UUID> createNewLogin(final Long tableId);
    Uni<Boolean> hasLogin(final long tableId);
}
