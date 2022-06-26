package business.exceptions.handler;


import business.exceptions.models.server.ServerException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ServerExceptionHandler implements ExceptionMapper<ServerException> {
    @Override
    public Response toResponse(ServerException e) {
        return Response.status(500).build();
    }
}
