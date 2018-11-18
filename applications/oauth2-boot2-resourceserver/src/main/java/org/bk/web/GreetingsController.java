package org.bk.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {


    @RequestMapping(method = RequestMethod.GET, value = "/secured/read")
    @ResponseBody
    public String read(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Read Called: Hello %s", jwt.getClaims());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/secured/write")
    @ResponseBody
    public String write(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Write Called: Hello %s", jwt);
    }


}
