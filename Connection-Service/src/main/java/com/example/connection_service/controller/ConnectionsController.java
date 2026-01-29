package com.example.connection_service.controller;

import com.example.connection_service.entity.PersonEntity;
import com.example.connection_service.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
