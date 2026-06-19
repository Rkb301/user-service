package com.example.user;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UserServiceApp {
    private static final Map<String, String> USERS = new HashMap<>();
    private static final Map<String, String> PROFILES = new HashMap<>();

    static {
        USERS.put("42", "Ada");
        USERS.put("404", "");
        PROFILES.put("42", "founder");
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/users", UserServiceApp::handleUsers);
        server.start();
        System.out.println("user-service listening on 8081");
    }

    static void handleUsers(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if ("POST".equals(exchange.getRequestMethod()) && "/users".equals(path)) {
            write(exchange, 201, "{\"id\":\"100\"}");
            return;
        }
        String[] parts = path.split("/");
        if (parts.length >= 3 && "GET".equals(exchange.getRequestMethod())) {
            if (parts.length == 4 && "profile".equals(parts[3])) {
                int status = PROFILES.containsKey(parts[2]) ? 200 : 404;
                write(exchange, status, getProfile(parts[2]));
                return;
            }
            if (USERS.containsKey(parts[2])) {
                write(exchange, 200, "{\"id\":\"" + parts[2] + "\"}");
            } else {
                write(exchange, 404, "{\"error\":\"user not found\"}");
            }
            return;
        }
        write(exchange, 404, "{\"error\":\"not found\"}");
    }

    public static String getProfile(String userId) {
        String profile = PROFILES.get(userId);
        if (profile == null) {
            return "{\"error\":\"profile not found\"}";
        }
        return "{\"profile\":\"" + profile.toUpperCase() + "\"}";
    }

    private static void write(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(bytes);
        }
    }
}
