package com.auditsphere.auditspherebackend.repository;


import com.auditsphere.auditspherebackend.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuditLogRepository
        extends JpaRepository<AuditLog,Long> {


}