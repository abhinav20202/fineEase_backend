package com.application.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.application.dto.ReportProjection;
import com.application.repository.TransactionsRepository;

@Service
public class ReportService {
	private static final Logger log = LoggerFactory.getLogger(ReportService.class);

	
	@Autowired TransactionsRepository transactionsRepository;
	
	 public List<ReportProjection> generateReport(Long userId, LocalDate startDate, LocalDate endDate, String search) {
	        return transactionsRepository.generateReport(userId, startDate, endDate, search);
	    }
}
