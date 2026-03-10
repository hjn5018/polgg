package com.polgg.polgg.controller.application;

import com.polgg.polgg.dto.application.ApplicationRequestDto;
import com.polgg.polgg.dto.application.ApplicationResponseDto;
import com.polgg.polgg.service.application.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<?> createApply(Authentication authentication, @RequestBody ApplicationRequestDto dto) {
        String studentId = (String) authentication.getPrincipal();
        Long id = applicationService.create(studentId, dto);
        return ResponseEntity.ok(Map.of("id", id, "message", "지원 기록이 등록되었습니다."));
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponseDto>> getMyApplications(Authentication authentication) {
        String studentId = (String) authentication.getPrincipal();
        return ResponseEntity.ok(applicationService.getMyApplications(studentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateApply(Authentication authentication, @PathVariable Long id, @RequestBody ApplicationRequestDto dto) {
        String studentId = (String) authentication.getPrincipal();
        applicationService.update(id, studentId, dto);
        return ResponseEntity.ok(Map.of("message", "지원 기록이 수정되었습니다."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApply(Authentication authentication, @PathVariable Long id) {
        String studentId = (String) authentication.getPrincipal();
        applicationService.delete(id, studentId);
        return ResponseEntity.ok(Map.of("message", "지원 기록이 삭제되었습니다."));
    }
}
