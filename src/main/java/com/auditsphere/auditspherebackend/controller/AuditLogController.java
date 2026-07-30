package com.auditsphere.auditspherebackend.controller;


import com.auditsphere.auditspherebackend.entity.AuditLog;
import com.auditsphere.auditspherebackend.service.AuditLogService;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/audit-logs")
@CrossOrigin
public class AuditLogController {



    private final AuditLogService auditLogService;



    public AuditLogController(
            AuditLogService auditLogService
    ){

        this.auditLogService = auditLogService;

    }



    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<AuditLog> getLogs(){

        return auditLogService.getAllLogs();

    }


}