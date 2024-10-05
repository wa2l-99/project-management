package com.pmt.project_management.project;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Project")
public class ProjectController {

    private final ProjectService service;

    @PostMapping
    public ResponseEntity<Integer> saveProject(@Valid @RequestBody ProjectRequest request,
                                               Authentication connectedUser) {
        return ResponseEntity.ok(service.save(request, connectedUser));
    }
}
