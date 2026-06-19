package com.example.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserServiceAppTest {
    @Test
    void returnsKnownProfile() {
        assertEquals("{\"profile\":\"FOUNDER\"}", UserServiceApp.getProfile("42"));
    }

    @Test
    void handlesUnknownProfileSafely() {
        assertEquals("{\"error\":\"profile not found\"}", UserServiceApp.getProfile("missing"));
    }
}
