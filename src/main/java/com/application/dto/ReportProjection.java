package com.application.dto;

public interface ReportProjection {
   String getCategory();
   Double getTotalSpent();
   Double getHighestSpent();
   Double getLowestSpent();
   Double getAverageSpent();
}