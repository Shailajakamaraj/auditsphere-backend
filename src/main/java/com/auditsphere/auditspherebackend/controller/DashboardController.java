package com.auditsphere.auditspherebackend.controller;


import com.auditsphere.auditspherebackend.dto.DashboardResponseDTO;
import com.auditsphere.auditspherebackend.service.DashboardService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/dashboard")
public class DashboardController {


    private final DashboardService dashboardService;


    public DashboardController(
            DashboardService dashboardService){

        this.dashboardService = dashboardService;
    }



    @GetMapping
    public DashboardResponseDTO getDashboard(){

        return dashboardService.getDashboardData();

    }

}