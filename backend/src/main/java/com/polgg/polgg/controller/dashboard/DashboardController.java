package com.polgg.polgg.controller.dashboard;

import com.polgg.polgg.dto.dashboard.DashboardItemDto;
import com.polgg.polgg.service.dashboard.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<List<DashboardItemDto>> getDashboard() {
        return ResponseEntity.ok(dashboardService.getPublicDashboard());
    }
}
