package com.camunda.configrest.config;

import org.apache.commons.io.IOUtils;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;

import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.impl.digest._apacheCommonsCodec.Base64;

import org.camunda.bpm.engine.impl.identity.Authentication;
import org.camunda.bpm.engine.rest.security.auth.AuthenticationProvider;
import org.camunda.bpm.engine.rest.security.auth.AuthenticationResult;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.midi.SysexMessage;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Collectors;
//import org.camunda.bpm.engine.rest.security.auth.impl.HttpBasicAuthenticationProvider

public class CoreAuthProvider implements AuthenticationProvider {
    IdentityService identityService;

    @Override
    public AuthenticationResult extractAuthenticatedUser(HttpServletRequest request, ProcessEngine engine) {
        String userName = request.getHeader("userName");
        String password = request.getHeader("password");

        identityService = engine.getIdentityService();


         System.out.println("getRequestedSessionId: "+request.getRequestedSessionId()   );



        if (request.getSession().getAttribute("userName") != null) {
            System.out.println("tem sessao");

            return AuthenticationResult.successful(request.getSession().getAttribute("userName").toString());

        } else {
            System.out.println("nao tem sessao");

            if(isAuthenticated(engine, userName, password)){
                System.out.println("is checked");
                // request.getSession(true);
                request.getSession().setMaxInactiveInterval(30);
                request.getSession().setAttribute("userName", userName);




                return AuthenticationResult.successful(userName);
            }else {
                return AuthenticationResult.unsuccessful(userName);
            }
        }

    }

    protected boolean isAuthenticated(ProcessEngine engine, String userName, String password) {
        return engine.getIdentityService().checkPassword(userName, password);
    }

    @Override
    public void augmentResponseByAuthenticationChallenge(
            HttpServletResponse response, ProcessEngine engine) {
        response.setHeader(HttpHeaders.LAST_MODIFIED, engine.getName() );
        System.out.println("RESPONSESEEES");

    }


    static String extractPostRequestBody(HttpServletRequest request) throws IOException {

        Scanner s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";

    }
}
