package com.allegiancemd.interceptor;

import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Interceptor;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.BaseServerResponseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Interceptor
public class RequestExceptionInterceptor {

    @Hook(Pointcut.SERVER_HANDLE_EXCEPTION)
    public boolean handleException(RequestDetails theRequestDetails,
                                   BaseServerResponseException theException,
                                   HttpServletRequest theServletRequest,
                                   HttpServletResponse theServletResponse) throws IOException {

        // HAPI's server exceptions know what the appropriate HTTP status code is
        theServletResponse.setStatus(theException.getStatusCode());

        // Provide a response ourself
        theServletResponse.setContentType("text/plain");
        theServletResponse.getWriter().append("Failed to process!");
        theServletResponse.getWriter().close();

        // Since we handled this response in the interceptor, we must return false
        // to stop processing immediately
        return false;
    }
}
