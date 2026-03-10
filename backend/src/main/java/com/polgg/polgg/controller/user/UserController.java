package com.polgg.polgg.controller.user;

import com.polgg.polgg.dto.user.ProfileResponseDto;
import com.polgg.polgg.dto.user.ProfileUpdateRequestDto;
import com.polgg.polgg.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> getProfile(Authentication authentication) {
        String studentId = (String) authentication.getPrincipal();
        return ResponseEntity.ok(userService.getProfile(studentId));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(Authentication authentication,
            @Valid @RequestBody ProfileUpdateRequestDto request) {
        String studentId = (String) authentication.getPrincipal();
        userService.updateProfile(studentId, request);
        return ResponseEntity.ok(Map.of("message", "프로필이 성공적으로 수정되었습니다."));
    }
}
