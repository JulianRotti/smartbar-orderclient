package org.lunskra.logins;

import io.smallrye.mutiny.Uni;
import org.lunskra.logins.mongopanache.Logins;

import java.util.UUID;

public interface LoginsService {

    Uni<UUID> createNewLogin(final Long tableId);
    Uni<Boolean> hasLogin(final long tableId);
    Uni<Logins> findLoginByToken(final UUID loginToken);
}
