package com.hexin.demo;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * Created by hexin on 2017/12/30.
 */
@Service
public class SecurityService {
    @Secured("ROLE_USER")
    public String secure() {
        return "Hello Security";
    }

    @PreAuthorize("hasRole('ROLE_USER1')")
    public String authorized() {
        return "Hello World";
    }

    @PreAuthorize("false")
    public String denied() {
        return "Goodbye World";
    }
}
