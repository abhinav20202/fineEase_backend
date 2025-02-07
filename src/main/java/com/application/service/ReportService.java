package com.application.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.application.dto.ReportProjection;
import com.application.repository.TransactionsRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ReportService {
	private static final Logger log = LoggerFactory.getLogger(ReportService.class);

	
	@Autowired TransactionsRepository transactionsRepository;
	
	 public List<ReportProjection> generateReport(Long userId, LocalDate startDate, LocalDate endDate, String search) {
	        return transactionsRepository.generateReport(userId, startDate, endDate, search);
	    }
	 
  public void exportToExcel(HttpServletResponse httpServletResponse, Long userId, LocalDate startDate, LocalDate endDate, String search) throws IOException{
	  List<ReportProjection> reportProjections = transactionsRepository.generateReport(userId, startDate, endDate, search);
	  Workbook workbook = new XSSFWorkbook();
	  Sheet sheet = workbook.createSheet("Expense Report");
	  
	  Row headerRow = sheet.createRow(0);
	  String[] columns = {"Category", "Total Spent", "Highest Spent", "Lowest Spent", "Average Spent"};
	  
	  CellStyle headerStyle = workbook.createCellStyle();
	  Font font = workbook.createFont();
	  font.setBold(true);
	  font.setFontHeightInPoints((short) 12);
	  headerStyle.setFont(font);
	  
	  for(int i=0; i< columns.length; i++) {
		  Cell cell = headerRow.createCell(i);
		  cell.setCellValue(columns[i]);
		  cell.setCellStyle(headerStyle);
	  }
	  
	  int rowNum =1;
	  
	  for(ReportProjection report : reportProjections) {
		  Row row = sheet.createRow(rowNum++);
		  row.createCell(0).setCellValue(report.getCategory());
		  row.createCell(1).setCellValue(report.getTotalSpent());
		  row.createCell(2).setCellValue(report.getHighestSpent());
		  row.createCell(3).setCellValue(report.getLowestSpent());
		  row.createCell(4).setCellValue(report.getAverageSpent());
	  }
	  
	  for(int i =0; i<columns.length; i++) {
		  sheet.autoSizeColumn(i);
	  }
	  
	  httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheet.sheet");
	  httpServletResponse.setHeader("Content-Disposition", "attachment; filename=report.xlsx");
	  
	  try(ServletOutputStream outputStream = httpServletResponse.getOutputStream()){
		  workbook.write(outputStream);
		  workbook.close();
	  }
  }
}
