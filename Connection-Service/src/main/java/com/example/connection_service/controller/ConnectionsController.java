package com.example.connection_service.controller;

import com.example.connection_service.entity.PersonEntity;
import com.example.connection_service.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class ConnectionsController {
    private final ConnectionService connectionService;

    @GetMapping("/deg-1")
    public ResponseEntity<List<PersonEntity>> get1DegreeConnections() {
        return ResponseEntity.ok(connectionService.get1DegreeConnections());
    }

    @GetMapping("/deg-2")
    public ResponseEntity<List<PersonEntity>> get2DegreeConnections() {
        return ResponseEntity.ok(connectionService.get2DegreeConnections());
    }

    @GetMapping("/deg-3")
    public ResponseEntity<List<PersonEntity>> get3DegreeConnections() {
        return ResponseEntity.ok(connectionService.get3DegreeConnections());
    }

    @GetMapping("/deg-4")
    public ResponseEntity<List<PersonEntity>> get4DegreeConnections() {
        return ResponseEntity.ok(connectionService.get4DegreeConnections());
    }

    @PostMapping("/request/{userId}")
    public ResponseEntity<?> sendConnectionRequest(@PathVariable Long userId) {
        try {
            boolean success = connectionService.sendConnectionRequest(userId);
            return ResponseEntity.ok(success);
        } catch (RuntimeException e) {
            String message = e.getMessage();

            if (message.contains("Unauthorized")) {
                return ResponseEntity.status(401).body("Unauthorized - please login");
            }
            if (message.contains("Cannot send connection request to yourself")) {
                return ResponseEntity.badRequest().body("You cannot send a request to yourself");
            }
            if (message.contains("Connection Request already exists")) {
                return ResponseEntity.badRequest().body("Connection request already sent");
            }
            if (message.contains("Already Connected")) {
                return ResponseEntity.badRequest().body("You are already connected to this user");
            }

            // fallback for unexpected errors
            return ResponseEntity.internalServerError().body("Failed to send connection request: " + message);
        }
    }

    @PostMapping("/accept/{userId}")
    public ResponseEntity<?> acceptConnectionRequest(@PathVariable Long userId) {
        try {
            boolean success = connectionService.acceptConnectionRequest(userId);
            return ResponseEntity.ok(success);
        } catch (RuntimeException e) {
            String message = e.getMessage();

            if (message.contains("Unauthorized")) {
                return ResponseEntity.status(401).body("Unauthorized - please login");
            }
            if (message.contains("No Connection Request found")) {
                return ResponseEntity.badRequest().body("No pending connection request from this user");
            }
            if (message.contains("Already Connected")) {
                return ResponseEntity.badRequest().body("You are already connected to this user");
            }

            return ResponseEntity.internalServerError().body("Failed to accept connection: " + message);
        }
    }

    @PostMapping("/reject/{userId}")
    public ResponseEntity<?> rejectConnectionRequest(@PathVariable Long userId) {
        try {
            boolean success = connectionService.rejectConnectionRequest(userId);
            return ResponseEntity.ok(success);
        } catch (RuntimeException e) {
            String message = e.getMessage();

            if (message.contains("Unauthorized")) {
                return ResponseEntity.status(401).body("Unauthorized - please login");
            }
            if (message.contains("No Connection Request found")) {
                return ResponseEntity.badRequest().body("No pending connection request from this user");
            }

            return ResponseEntity.internalServerError().body("Failed to reject connection: " + message);
        }
    }
}