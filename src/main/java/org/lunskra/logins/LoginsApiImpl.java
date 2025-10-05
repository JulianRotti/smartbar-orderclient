package org.lunskra.logins;

import jakarta.ws.rs.core.Response;
import org.lunskra.smartbar.orderclient.api.LoginsApi;

public class LoginsApiImpl implements LoginsApi {
    @Override
    public Response postLoginFromTable(Long tableId) {
        return Response.ok().build();
    }
}
