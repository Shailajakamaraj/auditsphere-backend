package com.auditsphere.auditspherebackend.controller;

import com.auditsphere.auditspherebackend.dto.DashboardSummaryDTO;
import com.auditsphere.auditspherebackend.service.DashboardService;
import com.auditsphere.auditspherebackend.dto.MonthlySummaryDTO;
import com.auditsphere.auditspherebackend.dto.RiskSummaryDTO;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5176"
})
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // ==========================
    // Dashboard Summary
    // ==========================

    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','AUDITOR')")
    public DashboardSummaryDTO getSummary() {

        return dashboardService.getDashboardSummary();

    }
// =====================================
// Monthly Chart
// =====================================

    @GetMapping("/monthly-summary")

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','AUDITOR')")
    public List<MonthlySummaryDTO> monthlySummary(){

        return dashboardService.getMonthlySummary();

    }



// =====================================
// Risk Chart
// =====================================

    @GetMapping("/risk-summary")

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','AUDITOR')")
    public List<RiskSummaryDTO> riskSummary(){

        return dashboardService.getRiskSummary();

    }
    // ==========================
    // AI Insights
    // ==========================

    @GetMapping("/insights")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','AUDITOR')")
    public String getInsights() {

        return dashboardService.generateInsight();

    }

    // ==========================
    // Refresh AI Report
    // ==========================

    @PostMapping("/refresh-ai")
    @PreAuthorize("hasRole('ADMIN')")
    public String refreshAI() {

        dashboardService.refreshAIReport();

        return "AI Report regenerated successfully.";

    }

}