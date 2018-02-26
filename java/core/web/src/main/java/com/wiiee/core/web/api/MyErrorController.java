package com.wiiee.core.web.api;

import com.wiiee.core.domain.service.ServiceResult;
import com.wiiee.core.platform.exception.CoreException;
import com.wiiee.core.platform.property.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class MyErrorController implements ErrorController {
    private static final String PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @Autowired
    private AppProperties appProperties;

    @Override
    public String getErrorPath() {
        return PATH;
    }

    @RequestMapping(value = PATH)
    public ServiceResult error(HttpServletRequest request, HttpServletResponse response) {
        if(response.getStatus() == 401 && appProperties.getAuthenticationUrl().equals(request.getAttribute("javax.servlet.error.request_uri"))){
            String errorMsg = (String)request.getAttribute("javax.servlet.error.message");

            if(errorMsg != null && errorMsg.equals("Authentication Failed: Bad credentials")){
                return ServiceResult.getByException(CoreException.INVALID_PWD);
            }

            if(errorMsg != null && errorMsg.startsWith("Authentication Failed: ")){
                return ServiceResult.getByException(CoreException.INVALID_USERNAME);
            }
        }

        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return new ServiceResult(false, response.getStatus(), (String)errorAttributes.getErrorAttributes(requestAttributes, false).get("error"), null, null);
    }
}
