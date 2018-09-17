package com.camunda.configrest;


import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;


import org.camunda.bpm.engine.rest.security.auth.AuthenticationResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

@RestController
public class HomeController {

    @GetMapping("/listCurrentAuth")
    public String create(HttpServletRequest request){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        IdentityService identityService = processEngine.getIdentityService();

        identityService.clearAuthentication();
        return identityService.getCurrentAuthentication().getUserId()+" "+request.getSession().getId();
    }

    @GetMapping("/ola")
    public String ola(){

        return "ola";
    }




    @GetMapping("/session")
    public String removeCookie(HttpServletRequest request, HttpServletResponse response){

        if(request.getSession().getAttribute("userName")!= null){
            return request.getSession().getAttribute("userName").toString();
        }else{
            return "null";
        }

    }




}
