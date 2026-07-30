package com.auditsphere.auditspherebackend.controller;


import com.auditsphere.auditspherebackend.dto.DashboardSummaryDTO;
import com.auditsphere.auditspherebackend.service.DashboardService;


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



    public DashboardController(
            DashboardService dashboardService
    ){

        this.dashboardService = dashboardService;

    }






    // KPI Summary

    @GetMapping("/summary")
    @PreAuthorize(
            "hasAnyRole('ADMIN','MANAGER','AUDITOR')"
    )
    public DashboardSummaryDTO getSummary(){


        return dashboardService.getDashboardSummary();

    }








    // AI Insights

    @GetMapping("/insights")
    @PreAuthorize(
            "hasAnyRole('ADMIN','MANAGER','AUDITOR')"
    )
    public String getInsights(){


        return dashboardService.generateInsight();

    }







    // ADMIN ONLY

    @PostMapping("/refresh-ai")
    @PreAuthorize(
            "hasRole('ADMIN')"
    )
    public String refreshAI(){


        dashboardService.refreshAIReport();


        return "AI report regenerated successfully";

    }


}