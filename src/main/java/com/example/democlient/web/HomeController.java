package com.example.democlient.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        // Public home page
        return "index";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal Object principal,
                          @RegisteredOAuth2AuthorizedClient("my-client") OAuth2AuthorizedClient authorizedClient,
                          Model model) {

        model.addAttribute("principal", principal);
        model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
        model.addAttribute("accessToken", authorizedClient.getAccessToken());

        if (principal instanceof OidcUser oidcUser) {
            model.addAttribute("userName", oidcUser.getFullName());
            model.addAttribute("email", oidcUser.getEmail());
            model.addAttribute("claims", oidcUser.getClaims());
        } else if (principal instanceof OAuth2AuthenticationToken token) {
            model.addAttribute("userName", token.getName());
            model.addAttribute("authorities", token.getAuthorities());
        }

        return "profile";
    }
}