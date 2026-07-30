package com.auditsphere.auditspherebackend.service;


import com.auditsphere.auditspherebackend.entity.AuditLog;
import com.auditsphere.auditspherebackend.repository.AuditLogRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;



@Service
public class AuditLogService {


    private final AuditLogRepository auditLogRepository;


    public AuditLogService(AuditLogRepository auditLogRepository){

        this.auditLogRepository = auditLogRepository;
    }



    public void logAction(
            String action,
            String entityName,
            Long entityId
    ){


        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();



        String email =
                authentication.getName();



        AuditLog log = AuditLog.builder()

                .userEmail(email)

                .action(action)

                .entityName(entityName)

                .entityId(entityId)

                .timestamp(LocalDateTime.now())

                .build();



        auditLogRepository.save(log);

    }



    public List<AuditLog> getAllLogs(){

        return auditLogRepository.findAll();

    }


}