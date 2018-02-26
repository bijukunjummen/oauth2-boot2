package samples.oauth2.web;

import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OAuth2AccessDeniedException.class)
    public String handleCustomBindException(OAuth2AccessDeniedException ex, Model model) {
        model.addAttribute("exception_message",
                String.format("Http Error Code: %s, OAuth2Error Code: %s, Exception Message: %s",
                        ex.getHttpErrorCode(), ex.getOAuth2ErrorCode(), ex.getMessage()));
        return "exception_page";
    }

}