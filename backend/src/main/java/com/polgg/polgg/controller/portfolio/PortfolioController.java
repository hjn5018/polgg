package com.polgg.polgg.controller.portfolio;

import com.polgg.polgg.dto.portfolio.PortfolioRequestDto;
import com.polgg.polgg.dto.portfolio.PortfolioResponseDto;
import com.polgg.polgg.service.portfolio.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;
    
    @GetMapping("/search")
    public ResponseEntity<List<PortfolioResponseDto>> searchPortfolios(@RequestParam String keyword) {
        return ResponseEntity.ok(portfolioService.searchPublicPortfolios(keyword));
    }

    @PostMapping
    public ResponseEntity<?> createPortfolio(Authentication authentication, @RequestBody PortfolioRequestDto dto) {
        String studentId = (String) authentication.getPrincipal();
        Long id = portfolioService.createPortfolio(studentId, dto);
        return ResponseEntity.ok(Map.of("id", id, "message", "포트폴리오가 등록되었습니다."));
    }

    @GetMapping
    public ResponseEntity<List<PortfolioResponseDto>> getMyPortfolios(Authentication authentication) {
        String studentId = (String) authentication.getPrincipal();
        return ResponseEntity.ok(portfolioService.getPortfoliosByMember(studentId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioResponseDto> getPortfolio(@PathVariable Long id) {
        return ResponseEntity.ok(portfolioService.getPortfolio(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePortfolio(Authentication authentication, @PathVariable Long id,
            @RequestBody PortfolioRequestDto dto) {
        String studentId = (String) authentication.getPrincipal();
        portfolioService.updatePortfolio(id, studentId, dto);
        return ResponseEntity.ok(Map.of("message", "포트폴리오가 수정되었습니다."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePortfolio(Authentication authentication, @PathVariable Long id) {
        String studentId = (String) authentication.getPrincipal();
        portfolioService.deletePortfolio(id, studentId);
        return ResponseEntity.ok(Map.of("message", "포트폴리오가 삭제되었습니다."));
    }
}
