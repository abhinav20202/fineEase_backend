package com.application.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.dto.ReportProjection;
import com.application.repository.TransactionsRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ReportService {
    private static final Logger log = LoggerFactory.getLogger(ReportService.class);

    @Autowired 
    private TransactionsRepository transactionsRepository;

    public List<ReportProjection> generateReport(Long userId, LocalDate startDate, LocalDate endDate, String search) {
        log.info("Generating report for User ID: {}, Start Date: {}, End Date: {}, Search: {}", userId, startDate, endDate, search);

        try {
            List<ReportProjection> reports = transactionsRepository.generateReport(userId, startDate, endDate, search);
            log.info("Report generation successful, {} records fetched.", reports.size());
            return reports;
        } catch (Exception e) {
            log.error("Error generating report for User ID: {}", userId, e);
            return null;
        }
    }

    public void exportToExcel(HttpServletResponse httpServletResponse, Long userId, LocalDate startDate, LocalDate endDate, String search) {
        log.info("Exporting report to Excel for User ID: {}, Start Date: {}, End Date: {}, Search: {}", userId, startDate, endDate, search);

        try {
            List<ReportProjection> reportProjections = transactionsRepository.generateReport(userId, startDate, endDate, search);
            
            if (reportProjections.isEmpty()) {
                log.warn("No data available for the given filters, Excel report will be empty.");
            }

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Expense Report");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Category", "Total Spent", "Highest Spent", "Lowest Spent", "Average Spent"};

            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 12);
            headerStyle.setFont(font);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // Fill data rows
            int rowNum = 1;
            for (ReportProjection report : reportProjections) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(report.getCategory());
                row.createCell(1).setCellValue(report.getTotalSpent());
                row.createCell(2).setCellValue(report.getHighestSpent());
                row.createCell(3).setCellValue(report.getLowestSpent());
                row.createCell(4).setCellValue(report.getAverageSpent());
            }

            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=report.xlsx");

            try (ServletOutputStream outputStream = httpServletResponse.getOutputStream()) {
                workbook.write(outputStream);
                log.info("Excel report successfully generated and written to output stream.");
            } finally {
                workbook.close();
            }

        } catch (IOException e) {
            log.error("Error writing Excel report to output stream", e);
        } catch (Exception e) {
            log.error("Unexpected error occurred while exporting report to Excel", e);
        }
    }
}