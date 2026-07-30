package com.auditsphere.auditspherebackend.repository;


import com.auditsphere.auditspherebackend.entity.AIReport;

import org.springframework.data.jpa.repository.JpaRepository;



public interface AIReportRepository
        extends JpaRepository<AIReport,Long>{



    AIReport findTopByOrderByCreatedAtDesc();



}