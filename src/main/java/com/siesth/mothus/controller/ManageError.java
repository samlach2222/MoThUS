package com.siesth.mothus.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ManageError implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        int statusCode = Integer.parseInt(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString());

        switch (statusCode) {
            // Not Found
            case 404:
                // Redirect to /login when a page doesn't exist
                // It will then redirect to /playZone if the user is already logged in
                System.err.println("Error code " + statusCode + ": Page " + request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI) + " not found");
                return "redirect:/login";
            case 403:
                System.err.println("Error code " + statusCode + ": Page " + request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI) + " Forbidden");
                return "redirect:/login";
            default:
                System.err.println("Error code " + statusCode);
                return "redirect:/login";
        }
    }
}