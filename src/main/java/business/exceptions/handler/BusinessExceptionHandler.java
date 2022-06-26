package business.exceptions.handler;

import business.exceptions.models.business.BusinessException;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.logging.LogRecord;

public class BusinessExceptionHandler implements ExceptionMapper<BusinessException> {

    private static final Logger LOG = Logger.getLogger(BusinessExceptionHandler.class);

    @Override
    public Response toResponse(BusinessException e) {
        LOG.debug(e.getError());
        // check on logging service
        return Response.status(400).entity(e).build();
    }
}
